package com.revature.ecommercep0.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory { //singleton instance, minimizes security risk
    private static ConnectionFactory instance; //static makes it part of the class, can access it at will
    private Connection conn;

    private ConnectionFactory() throws IOException, SQLException { //private constructor makes it singleton, cant access/instatiate from outside class, 
        loadProperties(); // calls DB
    }
    public static ConnectionFactory getInstance() throws SQLException, IOException {
        if (instance == null || instance.getConnection().isClosed()) {
            return new ConnectionFactory();
        }
        return instance;
    }
    public Connection getConnection() {
        return this.conn;
    }
    private void loadProperties() throws IOException, SQLException{
        InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties");
        Properties p = new Properties();
        p.load(is);

        conn = DriverManager.getConnection(p.getProperty("url"), p.getProperty("username"), p.getProperty("password"));
    }
}
