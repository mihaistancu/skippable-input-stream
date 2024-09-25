package common;

import java.io.IOException;
import java.io.InputStream;

public class JoinedStream extends InputStream {
    private final InputStream s1;
    private final InputStream s2;

    public JoinedStream(InputStream s1, InputStream s2) {
        this.s1 = s1;
        this.s2 = s2;
    }

    boolean firstFinished;

    @Override
    public int read() throws IOException {
        if (firstFinished) {
            return s2.read();
        }

        int c = s1.read();
        if (c == -1) {
            firstFinished = true;
        }
        return c;
    }
}
