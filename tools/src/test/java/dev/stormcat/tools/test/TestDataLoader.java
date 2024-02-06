package dev.stormcat.tools.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestDataLoader {

    public static void load(Connection connection, String sqlPath) {
        String sql = FileLoader.load(sqlPath);
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
