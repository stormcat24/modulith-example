package dev.stormcat.tools.generator.jdbc;

import com.mysql.cj.x.protobuf.MysqlxResultset;
import lombok.AllArgsConstructor;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class TableAnalyzer {

    private DatabaseMetaData metaData;

    private String tableName;

    public TableMetaData analyze() throws SQLException {
        ResultSet columns = metaData.getColumns(null, null, tableName, null);

        ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
        Set<String> primaryKeyColumnNames = HashSet.newHashSet(0);
        while (primaryKeys.next()) {
            primaryKeyColumnNames.add(primaryKeys.getString("COLUMN_NAME"));
        }

        TableMetaData tableMetaData = new TableMetaData(tableName);
        tableMetaData.setPrimaryKeyColumnNames(primaryKeyColumnNames);

        while (columns.next()) {
            String columnName = columns.getString("COLUMN_NAME");
            int datatype = columns.getInt("DATA_TYPE");
            String typeName = columns.getString("TYPE_NAME");
            String columnSize = columns.getString("COLUMN_SIZE");
            String decimalDigits = columns.getString("DECIMAL_DIGITS");
            String isNullable = columns.getString("IS_NULLABLE");
            String isAutoIncrement = columns.getString("IS_AUTOINCREMENT");

            ColumnMetaData columnMetaData = ColumnMetaData.builder()
                    .columnName(columnName)
                    .dataType(datatype)
                    .isNullable("YES".equals(isNullable))
                    .isAutoIncrement("YES".equals(isAutoIncrement))
                    .isPrimaryKey(primaryKeyColumnNames.contains(columnName))
                    .columnSize(Integer.parseInt(columnSize))
                    .decimalDigits(decimalDigits)
                    .isEnum("ENUM".equals(typeName))
                    .build();
            tableMetaData.addColumnMetaData(columnMetaData);
        }

        return tableMetaData;
    }
}
