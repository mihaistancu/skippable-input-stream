import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FirstVersionTests {

    @Test
    public void test() throws  Exception{

        var string = """
                <sbd>
                    <sbdh:y><alex></alex><cristi></cristi></sbdh:y>
                    <sed><b></b><c></c></sed>
                </sbd>
                """;
        var bis = new ByteArrayInputStream(string.getBytes());

        var skipped = new SkippableInputStream(bis);
        var result = new String(skipped.readAllBytes());

        assertEquals("<sed><b></b><c></c></sed>", result);
    }


    public class SkippableInputStream extends InputStream {

        private final InputStream bis;
        private Stack<Integer> stack = new Stack<>();

        public SkippableInputStream(InputStream bis) throws Exception {
            this.bis = bis;
            skipToPosition();
        }

        private void skipToPosition() throws Exception {
            int c = -1;
            do {
                c = bis.read();
            } while (c != '>');

            do {
                c = bis.read();
            } while (c != '<');

            Stack<Integer> s = new Stack<>();
            s.push(c);

            do {
                c = bis.read();
                if (c == '<') {
                    s.push(c);
                } else if (c == '/') {
                    s.pop();
                    s.pop();
                }
            } while (!s.empty());

            do  {
                c = bis.read();
            } while (c != '>');

        }

        boolean finished = false;

        @Override
        public int read() throws IOException {
            if (finished) {
                return -1;
            }
            int c = bis.read();
            if (c == '<') {
                stack.push(c);
            }
            else if (c == '/') {
                stack.pop();
                stack.pop();
            }
            finished = c == '>' && stack.empty();
            return c;
        }


        private boolean hasFinished() {
            return false;
        }

        public static long skipUntil(InputStream content, String expected) throws Exception {
            long bytesSkipped = 0L;
            int index = 0;

            while(index < expected.length()) {
                int ch = content.read();
                if (ch == expected.charAt(index)) {
                    ++index;
                } else if (ch == expected.charAt(0)) {
                    bytesSkipped += (long)index;
                    index = 1;
                } else {
                    if (ch == -1) {
                        throw new RuntimeException("Expected [" + expected + "]");
                    }

                    if (index == 0) {
                        ++bytesSkipped;
                    } else {
                        bytesSkipped += (long)index;
                        ++bytesSkipped;
                        index = 0;
                    }
                }
            }

            return bytesSkipped;
        }
    }
}
