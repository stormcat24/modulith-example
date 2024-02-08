package dev.stormcat.tools.generator.jdbc;

import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class EnumAnalyzer {

    private final Connection connection;

    public List<EnumMetaData> analyze() {
        List<EnumMetaData> enumMetaDataList = new ArrayList<>();

        String sql = "SELECT TABLE_NAME, COLUMN_NAME, COLUMN_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE COLUMN_TYPE LIKE 'enum%'";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery(sql);
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                String columnName = rs.getString("COLUMN_NAME");
                String columnType = rs.getString("COLUMN_TYPE");
                enumMetaDataList.add(new EnumMetaData(tableName, columnName, columnType));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return enumMetaDataList;
    }
}
