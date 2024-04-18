package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static final String URL_KEY = "db.url";

    private static final String USER_KEY = "db.user";

    private static final String PASSWORD_KEY = "db.password";

    public static final String DRIVER_KEY = "db.driver";


    static {
        try {
            Class.forName(Property.get(DRIVER_KEY));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(Property.get(URL_KEY),
                    Property.get(USER_KEY), Property.get(PASSWORD_KEY));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
