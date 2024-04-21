package utils;

public class JspHelper {
    private static final String FORMAT = "/%s.jsp";

    public static String get(String path) {
        return String.format(FORMAT, path);
    }
}