import common.StackPop;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import static common.StackCheck.check;

public class XmlElementStream extends InputStream{

    private final Stack<Integer> stack = new Stack<>();
    private final Stack<Integer> commentStack = new Stack<>();
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

        if (!commentStack.empty()) {
            if (c == '-') {
                commentStack.push(c);
            } else if (c == '>') {
                if (check(commentStack, "<!----")) {
                    commentStack.clear();
                }
            } else {
                while (stack.size() > 4) {
                    stack.pop();
                }
            }
            return c;
        }

        if (c == '<') {
            stack.push(c);
        } else if (c == '/') {
            if (stack.peek() != '"' && stack.peek() != '>') {
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

            if (check(stack, "<t/>")) {
                StackPop.pop(stack, 4);
            } else if (check(stack, "<t></t>")) {
                StackPop.pop(stack, 7);
            }
        }
        else if (c == '!') {
            if (stack.peek() == '<') {
                commentStack.push((int)'<');
                commentStack.push((int)'!');
                stack.pop();
            }
        }
        else {
            if (stack.empty()) {
                return c;
            }
            int prev = stack.peek();
            if (prev != 't' && prev != '>' && prev != '"') {
                stack.push((int) 't');
            }
        }
        finished = commentStack.empty() && stack.empty();
        return c;
    }
}