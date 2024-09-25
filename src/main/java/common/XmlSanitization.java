package common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlSanitization {
    public static String sanitize(String sbdRoot, String sedRoot) {
        return sedRoot;
//        Map<String, String> sbdNamespaces = getNamespaces(sbdRoot);
//        Map<String, String> sedNamespaces = getNamespaces(sbdRoot);
//        Map<String, String> mergedNamespaces = merge(sbdNamespaces, sedNamespaces);
//        String name = getTagName(sedRoot);
//        List<String> attributes = getAttributes(sedRoot);
//        return createTag(name, mergedNamespaces, attributes);
    }

    private static List<String> getAttributes(String sedRoot) {
        return null;
    }

    private static String getTagName(String sedRoot) {
        return "";
    }

    private static Map<String, String> merge(Map<String, String> sbdNamespaces, Map<String, String> sedNamespaces) {
        var result = new HashMap<String, String>();
        return result;
    }

    private static Map<String, String> getNamespaces(String tag) {
        var result = new HashMap<String, String>();
        return result;
    }

    public static String createTag(String tagName, Map<String, String> namespaces, List<String> attributes){
        return "";
    }
}
