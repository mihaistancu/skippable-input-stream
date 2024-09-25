import common.StackCheck;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StackPatternCheckTests {

    @Test
    public void testCombinations() {
        check("<t/>", "<t/>");
        check("<t></t><t/>", "<t/>");
    }

    public void check(String items, String pattern) {
        Stack<Integer> stack = new Stack<>();
        push(stack, items);
        assertTrue(StackCheck.check(stack, pattern));
    }

    public void push(Stack<Integer> stack, String items) {
        for (int i = 0; i <items.length(); i++) {
            stack.push((int)items.charAt(i));
        }
    }

}
