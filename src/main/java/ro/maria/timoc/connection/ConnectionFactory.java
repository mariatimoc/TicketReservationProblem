package ro.maria.timoc.connection;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionFactory {

    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());

    private static final Properties props = new Properties();

    static {
        try (InputStream in =ConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (in != null) {
                props.load(in);
            }

        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Could not load properties file.");
        }
    }

    private static final String DB_URL= props.getProperty("db.url", "jdbc:mysql://localhost:3306/train_ticket");

    private static final String USER =props.getProperty("db.user", "root");

    private static final String PASSWORD= props.getProperty("db.pass", "");

    private static final ConnectionFactory instance = new ConnectionFactory();

    public static Connection getConnection() {
        return instance.createConnection();
    }

    private Connection createConnection() {

        try {
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);

        } catch (SQLException e) {

            LOGGER.log(Level.WARNING, "Database connection error: " + e.getMessage());

            return null;
        }
    }

    public static void close(Connection connection) {

        try {

            if (connection != null) {
                connection.close();
            }

        } catch (SQLException e) {

            LOGGER.log(Level.WARNING, "Error while closing connection: " + e.getMessage());
        }
    }

    public static void close(Statement statement) {

        try {

            if (statement != null) {
                statement.close();
            }

        } catch (SQLException e) {

            LOGGER.log(Level.WARNING, "Error while closing statement: " + e.getMessage());
        }
    }

    public static void close(ResultSet resultSet) {

        try {

            if (resultSet != null) {
                resultSet.close();
            }

        } catch (SQLException e) {

            LOGGER.log(Level.WARNING, "Error while closing result set: " + e.getMessage());
        }
    }
}