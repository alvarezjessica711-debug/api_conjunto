package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String DEFAULT_URL = "jdbc:mysql://mysql-150b858-alvarezjessica711-9f82.f.aivencloud.com:22020/valle_san_remo?ssl-mode=REQUIRED&useSSL=true&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DEFAULT_USER = "avnadmin";

    public static Connection getConnection() throws SQLException {
        String url = firstNonBlank(
                System.getenv("DB_URL"),
                System.getProperty("DB_URL"),
                DEFAULT_URL
        );
        String user = firstNonBlank(
                System.getenv("DB_USER"),
                System.getProperty("DB_USER"),
                DEFAULT_USER
        );
        String password = firstNonBlank(
                System.getenv("DB_PASSWORD"),
                System.getProperty("DB_PASSWORD")
        );

        if (password == null || password.isBlank()) {
            throw new SQLException("Falta la contraseña de la base de datos. Define DB_PASSWORD o -DDB_PASSWORD=...");
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver JDBC de MySQL", e);
        }

        return DriverManager.getConnection(url, user, password);
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }
}
