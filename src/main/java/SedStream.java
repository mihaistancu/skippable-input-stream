import common.JoinedStream;

import java.io.IOException;
import java.io.InputStream;

import static common.StringSerializer.getInputStream;

public class SedStream extends InputStream {
    private final InputStream is;
    private final String sbdRoot;
    private final String sedRoot;
    private final String sanitizedSedRoot;

    public SedStream(InputStream is) throws Exception {
        sbdRoot = XmlSkip.readFirstTag(is);

        var sbdh = new XmlElementStream(is);
        sbdh.skipToEnd();

        sedRoot = XmlSkip.readTag(is);
        sanitizedSedRoot = sanitize(sbdRoot, sedRoot);

        this.is = new XmlElementStream(new JoinedStream(getInputStream(sanitizedSedRoot), is));
    }

    private String sanitize(String sbdRoot, String sedRoot) {
        return sedRoot;
    }

    @Override
    public int read() throws IOException {
        return is.read();
    }
}
