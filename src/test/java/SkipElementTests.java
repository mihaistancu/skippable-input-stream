import common.StringSerializer;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static common.StringSerializer.getInputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SkipElementTests {
    @Test
    public void testCombinations() throws Exception {
        check("<a></a><next>", "<next>");
        check("<a> \r\n text </a><next>","<next>");
        check("   <a> \r\n text </a><next>","<next>");
    }

    public void check(String input, String expected) throws Exception {
        InputStream is = getInputStream(input);

        XmlSkip.skipCurrentElement(is);
        assertEquals(expected, StringSerializer.toString(is));
    }

}
