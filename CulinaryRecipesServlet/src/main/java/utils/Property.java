package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Property {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    public static String get(final String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (InputStream inputStream = Property.class.getClassLoader()
                .getResourceAsStream("appsettings")) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}