package common;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class StringSerializer {
    public static InputStream getInputStream(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }
}