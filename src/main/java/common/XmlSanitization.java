package common;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.stream.IntStream;

import static common.StringSerializer.getInputStream;

public class XmlSanitization {
    public static String sanitize(String sbdRoot, String sedRoot) throws Exception {
        String sbdRootName = getName(sbdRoot);
        String sedRootName = getName(sedRoot);

        String xml = sbdRoot + sedRoot + endTag(sedRootName) + endTag(sbdRootName);
        String child = extractFirstChild(getInputStream(xml));
        return child.substring(0, child.length() - 2) + ">";
    }

    private static String endTag(String tagName) {
        return "</" + tagName + ">";
    }

    public static String getName(String tag) {
        int index = IntStream.range(0, tag.length())
                .filter(i -> Character.isWhitespace(tag.charAt(i)) || tag.charAt(i) == '>' || tag.charAt(i) == '/')
                .findFirst()
                .orElse(-1);
        return tag.substring(1, index);
    }

    private static String extractFirstChild(InputStream is) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse(is);
        Node node = doc.getFirstChild().getFirstChild();
        Document newDoc = builder.newDocument();
        Node importedNode = newDoc.importNode(node, true);
        newDoc.appendChild(importedNode);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

        DOMSource source = new DOMSource(newDoc);
        StreamResult result = new StreamResult(new StringWriter());
        transformer.transform(source, result);
        return result.getWriter().toString();
    }
}
