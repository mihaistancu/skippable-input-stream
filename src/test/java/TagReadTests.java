import exceptions.NoEndTagException;
import exceptions.NoStartTagException;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static common.StringSerializer.getInputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TagReadTests {
    @Test
    public void whitespacesAreIgnored() throws Exception {
        assertEquals("<x>", read("<x>"));
        assertEquals("<x>", read("   <x>"));
        assertEquals("<x>", read("\r\n<x>"));
        assertEquals("<x>", read("\r\n<x>\r\n"));
        assertEquals("</x>", read("</x>"));
    }

    @Test
    public void failsWhenNoEndTag() {
        assertThrows(NoEndTagException.class, () -> {
            read("<x");
        });
    }

    @Test
    public void failsWhenNoStartTag() {
        assertThrows(NoStartTagException.class, () -> {
            read("x");
        });
    }

    private static String read(String input) throws Exception {
        try (InputStream is = getInputStream(input)) {
            return XmlSkip.readTag(is);
        }
    }
}
