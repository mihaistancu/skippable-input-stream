import common.StringSerializer;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static common.StringSerializer.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SedStreamTests {
    @Test
    public void test() throws Exception {
        String xml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <sbd>
                    <sbdh></sbdh>
                    <sed>content</sed>
                </sbd>
                """;

        InputStream is = getInputStream(xml);

        SedStream sed = new SedStream(is);

        String expected = "<sed>content</sed>";

        assertEquals(expected, StringSerializer.toString(sed));
    }
}
