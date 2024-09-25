import common.XmlSanitization;
import org.junit.jupiter.api.Test;

import static common.XmlSanitization.getName;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class XmlSanitizationTests {
    @Test
    public void testCombinations() throws Exception {
        check("<root>", "<child>", "<child>");
        check("<root xmlns:ns1=\"http://ns1\">", "<ns1:child>", "<ns1:child xmlns:ns1=\"http://ns1\">");
    }

    public void check(String sbd, String sed, String expected) throws Exception {
        String actual = XmlSanitization.sanitize(sbd, sed);
        assertEquals(expected, actual);
    }

    @Test
    public void tagNameTests() {
        assertEquals("abc", getName("<abc>"));
        assertEquals("abc", getName("<abc >"));
        assertEquals("abc", getName("<abc\r\n>"));
    }
}
