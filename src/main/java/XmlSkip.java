import common.StackCheck;
import common.StackPop;
import exceptions.NoEndTagException;
import exceptions.NoStartTagException;

import java.io.InputStream;
import java.util.Stack;

import static common.StackCheck.*;
import static common.StackPop.*;

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

    public static void skipCurrentElement(InputStream is) throws Exception {
        Stack<Integer> stack = new Stack<>();

        do {
            int c = is.read();

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

}
