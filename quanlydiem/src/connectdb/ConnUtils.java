package connectdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnUtils {
    public static Connection getMySqlConnection(String hostName, String dbName, String userName, String password) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName+"?serverTimezone=UTC";
        Connection conn = DriverManager.getConnection(connectionURL, userName, password);
        return conn;
    }


    public static Connection getMySqlConnection() throws SQLException, ClassNotFoundException {
        String hostName = "localhost";
        String dbName = "quanlydiem";
        String userName = "root";
        String password = "Nam08021998@";
        return getMySqlConnection(hostName,dbName,userName,password);
    }
}
