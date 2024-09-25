import common.XmlSanitization;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlSanitizationTests {
    @Test
    public void testCombinations() {
        check("<root>", "<child>", "<child>");
        //check("<root xmlns:ns1=\"http://ns1\">", "<ns1:child>", "<ns1:child xmlns:ns1=\"http://ns1\">");
    }

    public void check(String sbd, String sed, String expected) {
        String actual = XmlSanitization.sanitize(sbd, sed);
        assertEquals(expected, actual);
    }
}
