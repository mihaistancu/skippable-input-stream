import exceptions.InvalidXmlDeclaration;
import exceptions.InvalidXmlStartCharacter;
import exceptions.NoEndTagException;
import exceptions.NoStartTagException;

import java.io.IOException;
import java.io.InputStream;

public class XmlSkip {
    public static String readFirstTag(InputStream is) throws Exception {
        StringBuilder builder = new StringBuilder();

        int c = is.read();
        if (c != '<') {
            throw new InvalidXmlStartCharacter();
        }

        c = is.read();
        if (c == '?') {
            do {
                c = is.read();

            } while (c != '?');

            c = is.read();
            if (c != '>') {
                throw new InvalidXmlDeclaration();
            }

            do {
                c = is.read();
                if (c== -1) {
                    throw new NoStartTagException();
                }
            } while (c != '<');
        }
        else {
            builder.append('<');
        }

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

    public static int skipToFirstNonWhitespace(InputStream is) throws IOException {
        int c;
        do {
            c = is.read();
            if (c == -1) {
                throw new RuntimeException("Only whitespace");
            }
        } while (Character.isWhitespace(c));
        return c;
    }
}
