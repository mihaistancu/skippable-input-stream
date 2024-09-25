import common.StackCheck;
import common.StackPop;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

public class XmlElementStream extends InputStream{

    private final Stack<Integer> stack = new Stack<Integer>();
    private final InputStream is;
    boolean finished = false;

    public XmlElementStream(InputStream is) {
        this.is = is;
    }

    public void skipToEnd() throws IOException {
        int c;
        do {
            c = read();
        } while (c != -1);
    }

    @Override
    public int read() throws IOException {
        if (finished) {
            return -1;
        }

        int c = is.read();

        if (c == '<') {
            stack.push(c);
        } else if (c == '/') {
            if (stack.peek() != '"') {
                stack.push(c);
            }
        } else if (c == '"') {
            if (stack.peek() == '"') {
                stack.pop();
            } else {
                stack.push(c);
            }
        }
        else if (c == '>') {
            stack.push(c);

            if (StackCheck.check(stack, "<t/>")) {
                StackPop.pop(stack, 4);
            } else if (StackCheck.check(stack, "<t></t>")) {
                StackPop.pop(stack, 7);
            }
        } else {
            if (stack.empty()) {
                return c;
            }
            int prev = stack.peek();
            if (prev != 't' && prev != '>' && prev != '"') {
                stack.push((int) 't');
            }
        }
        finished = stack.empty();
        return c;
    }
}