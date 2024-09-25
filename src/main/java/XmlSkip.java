import common.StackCheck;
import common.StackPop;
import exceptions.InvalidXmlDeclaration;
import exceptions.InvalidXmlStartCharacter;
import exceptions.NoEndTagException;
import exceptions.NoStartTagException;

import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.util.Stack;

import static common.StackCheck.*;
import static common.StackPop.*;

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

    public static void skipCurrentElement(InputStream is) throws Exception {
        Stack<Integer> stack = new Stack<>();
        int c = skipToFirstNonWhitespace(is);
        stack.push(c);

        do {
            c = is.read();

            if (c == '<') {
                stack.push(c);
            } else if (c == '/') {
                stack.push(c);
            } else if (c == '>') {
                stack.push(c);

                if (check(stack, "<t/>")) {
                    pop(stack, 4);
                } else if (check(stack, "<t></t>")) {
                    pop(stack, 7);
                }
            } else {
                int prev = stack.peek();
                if (prev != 't' && prev != '>') {
                    stack.push((int) 't');
                }
            }
        } while (!stack.empty());
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
