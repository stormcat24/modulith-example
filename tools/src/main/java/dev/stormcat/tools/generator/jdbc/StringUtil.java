package dev.stormcat.tools.generator.jdbc;

public final class StringUtil {

    public static String toCamelCase(String value) {
        String[] tokens = value.split("_");
        StringBuilder sb = new StringBuilder();
        for (String s : tokens) {
            sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1).toLowerCase());
        }
        return sb.toString();
    }
}
