import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static common.StringSerializer.getInputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FirstTagReadTests {

    @Test
    public void xmlDeclarationIsIgnored() throws Exception {
        String xmlDeclaration = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><x>";
        assertEquals("<x>", read(xmlDeclaration + "<x>"));
        assertEquals("<x>", read(xmlDeclaration + "\r\n<x>"));
    }

    @Test
    public void xmlDeclarationCanBeMissing() throws Exception {
        assertEquals("<x>", read("<x>"));
    }

    private static String read(String input) throws Exception {
        try (InputStream is = getInputStream(input)) {
            return XmlSkip.readFirstTag(is);
        }
    }
}
