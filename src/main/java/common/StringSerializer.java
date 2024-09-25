package common;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class StringSerializer {
    public static InputStream getInputStream(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }

    public static String toString(InputStream inputStream) throws Exception {
        return new String(inputStream.readAllBytes());
    }
}