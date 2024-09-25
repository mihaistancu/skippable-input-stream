import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class StringSerializer {
    public static InputStream toByteArray(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }
}