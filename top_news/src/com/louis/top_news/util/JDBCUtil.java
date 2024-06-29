package com.louis.top_news.util;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtil {
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();
    private static DataSource dataSource;

    private static String url;
    private static String user;
    private static String password;

    static {
        Properties properties = new Properties();
        InputStream resourceAsStream = JDBCUtil.class.getClassLoader()
                                                     .getResourceAsStream("jdbc.properties");
        try {
            properties.load(resourceAsStream);
            url      = properties.getProperty("jdbc.url");
            user     = properties.getProperty("jdbc.username");
            password = properties.getProperty("jdbc.password");

            Class.forName(properties.getProperty("jdbc.driver"));
        }
        catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection () {
        Connection connection = threadLocal.get();
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url , user , password);
                threadLocal.set(connection);
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void closeConnection () {
        Connection connection = threadLocal.get();
        if (connection != null) {
            try {
                connection.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
            finally {
                threadLocal.remove();
            }
        }
    }
}
