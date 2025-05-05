package com.adriannebulao.coding_practices.leave.repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class Repository {
    public Connection getConnection () {
        try {
            return DriverManager.getConnection("jdbc:hsqldb:leaves", "sa", "");
        } catch (SQLException e) {
            throw new DataAccessException("DataAccessException", e);
        }
    }
}
