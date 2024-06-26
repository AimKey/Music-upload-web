package Model;

// @author phamm
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseInformation {

    private String serverName, dbName, dbUser, dbPassword;

    public DatabaseInformation(String serverName, String dbName, String dbUser, String dbPassword) {
        this.serverName = serverName;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public DatabaseInformation(String serverName, String dbName) {
        this.serverName = serverName;
        this.dbName = dbName;
        dbUser = "sa";
        dbPassword = "123456";
    }

    // Setup connection
    public Connection getConnection()
            throws ServletException, IOException {
        java.sql.Connection con = null;
//        String port = "1433";
//        String IP = "192.168.1.88";
//        String ServerName = "KUUL";
//        String DBName = "TestingCRUD";
        String driverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        // Use String.format() for easy manipulation
        String test = String.format("%s%s", dbUser, dbPassword);
        String dbURL = String.format(
                "jdbc:sqlserver://%s;databaseName=%s;encrypt=false;trustServerCertificate=false;loginTimeout=30",
                 serverName, dbName);
        try {
            Class.forName(driverClass);
            //DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return con;
    }
}
