package dev.stormcat.tools.generator.jdbc;

import com.mysql.cj.x.protobuf.MysqlxResultset;
import lombok.AllArgsConstructor;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

@AllArgsConstructor
public class TableAnalyzer {

    private DatabaseMetaData metaData;

    private List<EnumMetaData> enumMetaDataList;

    private String tableName;

    public TableMetaData analyze() throws SQLException {
        ResultSet columns = metaData.getColumns(null, null, tableName, null);
        ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
        Set<String> primaryKeyColumnNames = HashSet.newHashSet(0);
        while (primaryKeys.next()) {
            primaryKeyColumnNames.add(primaryKeys.getString("COLUMN_NAME"));
        }

        TableMetaData tableMetaData = new TableMetaData(tableName);

        Map<String, ColumnMetaData> columnMetaDataMap = new HashMap<>();
        while (columns.next()) {
            String columnName = columns.getString("COLUMN_NAME");
            int datatype = columns.getInt("DATA_TYPE");
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
                    .build();

            enumMetaDataList.stream().findFirst()
                    .filter(em -> em.getTableName().equals(tableName) && em.getColumnName().equals(columnName))
                    .ifPresent(em -> columnMetaData.setEnumMetaData(em));

            tableMetaData.addColumnMetaData(columnMetaData);
            columnMetaDataMap.put(columnName, columnMetaData);
        }

        ResultSet indexes = metaData.getIndexInfo(null, null, tableName, false, true);
        Map<String, IndexMetaData> indexMetaDataMap = new HashMap<>();
        while (indexes.next()) {
            String indexName = indexes.getString("INDEX_NAME");
            if ("PRIMARY".equals(indexName)) {
                continue;
            }
            IndexMetaData currentIndex = indexMetaDataMap.get(indexName);
            if (currentIndex == null) {
                currentIndex = new IndexMetaData(indexName, !indexes.getBoolean("NON_UNIQUE"));
                indexMetaDataMap.put(indexName, currentIndex);
            }

            String columnName = indexes.getString("COLUMN_NAME");
            ColumnMetaData columnMetaData = columnMetaDataMap.get(columnName);
            if (columnMetaData != null) {
                currentIndex.addColumn(columnMetaData);
            }
        }
        tableMetaData.addAllIndex(indexMetaDataMap.values().stream().toList());
        return tableMetaData;
    }
}
