package common;

import java.util.Stack;

public class StackCheck {
    public static boolean check(Stack<Integer> stack, String pattern) {
        if (stack.size() < pattern.length()) {
            return false;
        }
        for (int i = 0; i < pattern.length(); i++) {
            if (stack.get(stack.size() - i - 1) != pattern.charAt(pattern.length() - i - 1)) {
                return false;
            }
        }
        return true;
    }
}