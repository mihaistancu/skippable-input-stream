import exceptions.NoEndTagException;
import exceptions.NoStartTagException;

import java.io.InputStream;

public class XmlSkip {
    public static String readTag(InputStream is) throws Exception {
        StringBuilder builder = new StringBuilder();

        int c;
        do {
            c = is.read();
            if (c== -1) {
                throw new NoStartTagException();
            }
        } while (c != '<');

        builder.append((char)c);

        do {
            c = is.read();
            if (c == -1) {
                throw new NoEndTagException();
            }
            builder.append((char)c);
        } while (c != '>');

        return builder.toString();
    }
}
