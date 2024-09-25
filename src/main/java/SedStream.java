import java.io.IOException;
import java.io.InputStream;

public class SedStream extends InputStream {
    private final InputStream is;
    private final String sbdRoot;
    private final String sedRoot;
    private final String sanitizedSedRoot;

    public SedStream(InputStream is) throws Exception {
        this.is = is;
        sbdRoot = XmlSkip.readFirstTag(is);
        XmlSkip.skipCurrentElement(is);
        sedRoot = XmlSkip.readTag(is);
        sanitizedSedRoot = sanitize(sbdRoot, sedRoot);
    }

    private String sanitize(String sbdRoot, String sedRoot) {
        return sedRoot;
    }

    private int pos = 0;
    @Override
    public int read() throws IOException {
        if (pos < sanitizedSedRoot.length()) {
            int c = sanitizedSedRoot.charAt(pos);
            pos++;
            return c;
        }
        return is.read();
    }
}
