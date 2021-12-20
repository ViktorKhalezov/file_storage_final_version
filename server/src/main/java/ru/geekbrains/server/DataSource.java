package ru.geekbrains.server;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    private static final ComboPooledDataSource cpds = new ComboPooledDataSource();

    static {
        try {
            cpds.setDriverClass("org.sqlite.JDBC");
            cpds.setJdbcUrl("jdbc:sqlite:server/src/main/resources/ru/geekbrains/server/file_storage.db");
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }

}

