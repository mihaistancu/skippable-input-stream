package common;

import java.util.Stack;

public class StackPop {
    public static void pop(Stack<Integer> stack, int count) {
        for (int i = 0; i < count; i++) {
            stack.pop();
        }
    }
}
