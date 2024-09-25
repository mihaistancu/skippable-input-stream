import common.StringSerializer;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import common.JoinedStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JoinedStreamTests {
    @Test
    public void test() throws Exception {
        InputStream s1 = StringSerializer.getInputStream("123");
        InputStream s2 = StringSerializer.getInputStream("456");

        var joined = new JoinedStream(s1, s2);

        assertEquals("123456", StringSerializer.toString(joined));
    }
}
