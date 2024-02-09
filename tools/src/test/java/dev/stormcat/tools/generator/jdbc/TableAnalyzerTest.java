package dev.stormcat.tools.generator.jdbc;

import dev.stormcat.tools.test.MysqlExtension;
import dev.stormcat.tools.test.TestDataLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.List;

@ExtendWith(MysqlExtension.class)
public class TableAnalyzerTest {

    @Test
    void analyze_when_test_schema_sql(Connection connection) throws Exception {
        TestDataLoader.load(connection, "/test-schema.sql");
        EnumAnalyzer enumAnalyzer = new EnumAnalyzer(connection);
        List<EnumMetaData> enumMetaDataList = enumAnalyzer.analyze();
        TableMetaData metadata = new TableAnalyzer(connection.getMetaData(), enumMetaDataList, "test_table").analyze();

        assertEquals("test_table", metadata.getTableName());
        assertEquals("TestTable", metadata.getEntityName());
        assertEquals("test_table", metadata.getLemmaTableName());
        assertEquals("TestTableRepository", metadata.getRepositoryName());

        ColumnMetaData idColumn = metadata.getColumns().get(0);
        assertEquals("id", idColumn.getColumnName());
        assertEquals("id", idColumn.getLowerCamelCaseColumnName());
        assertEquals("int", idColumn.getJavaType());
        assertEquals(10, idColumn.getColumnSize());
        assertEquals(false, idColumn.isNullable());
        assertEquals(true, idColumn.isPrimaryKey());
        assertEquals(true, idColumn.isAutoIncrement());

        ColumnMetaData varcharColumn = metadata.getColumns().get(1);
        assertEquals("varchar_column", varcharColumn.getColumnName());
        assertEquals("varcharColumn", varcharColumn.getLowerCamelCaseColumnName());
        assertEquals("String", varcharColumn.getJavaType());
        assertEquals(100, varcharColumn.getColumnSize());
        assertEquals(true, varcharColumn.isNullable());
        assertEquals(false, varcharColumn.isPrimaryKey());
        assertEquals(false, varcharColumn.isAutoIncrement());

        ColumnMetaData charColumn = metadata.getColumns().get(2);
        assertEquals("char_column", charColumn.getColumnName());
        assertEquals("charColumn", charColumn.getLowerCamelCaseColumnName());
        assertEquals("String", charColumn.getJavaType());
        assertEquals(100, charColumn.getColumnSize());
        assertEquals(true, charColumn.isNullable());
        assertEquals(false, charColumn.isPrimaryKey());
        assertEquals(false, charColumn.isAutoIncrement());

        ColumnMetaData textColumn = metadata.getColumns().get(3);
        assertEquals("text_column", textColumn.getColumnName());
        assertEquals("textColumn", textColumn.getLowerCamelCaseColumnName());
        assertEquals("String", textColumn.getJavaType());
        assertEquals(65535, textColumn.getColumnSize());
        assertEquals(true, textColumn.isNullable());
        assertEquals(false, textColumn.isPrimaryKey());
        assertEquals(false, textColumn.isAutoIncrement());

        ColumnMetaData longtextColumn = metadata.getColumns().get(4);
        assertEquals("longtext_column", longtextColumn.getColumnName());
        assertEquals("longtextColumn", longtextColumn.getLowerCamelCaseColumnName());
        assertEquals("String", longtextColumn.getJavaType());
        assertEquals(2147483647, longtextColumn.getColumnSize());
        assertEquals(true, longtextColumn.isNullable());
        assertEquals(false, longtextColumn.isPrimaryKey());
        assertEquals(false, longtextColumn.isAutoIncrement());

        ColumnMetaData floatColumn = metadata.getColumns().get(5);
        assertEquals("float_column", floatColumn.getColumnName());
        assertEquals("floatColumn", floatColumn.getLowerCamelCaseColumnName());
        assertEquals("float", floatColumn.getJavaType());
        assertEquals(12, floatColumn.getColumnSize());
        assertEquals(true, floatColumn.isNullable());
        assertEquals(false, floatColumn.isPrimaryKey());
        assertEquals(false, floatColumn.isAutoIncrement());

        ColumnMetaData doubleColumn = metadata.getColumns().get(6);
        assertEquals("double_column", doubleColumn.getColumnName());
        assertEquals("doubleColumn", doubleColumn.getLowerCamelCaseColumnName());
        assertEquals("double", doubleColumn.getJavaType());
        assertEquals(22, doubleColumn.getColumnSize());
        assertEquals(true, doubleColumn.isNullable());
        assertEquals(false, doubleColumn.isPrimaryKey());
        assertEquals(false, doubleColumn.isAutoIncrement());

        ColumnMetaData decimalColumn = metadata.getColumns().get(7);
        assertEquals("decimal_column", decimalColumn.getColumnName());
        assertEquals("decimalColumn", decimalColumn.getLowerCamelCaseColumnName());
        assertEquals("java.math.BigDecimal", decimalColumn.getJavaType());
        assertEquals(10, decimalColumn.getColumnSize());
        assertEquals(true, decimalColumn.isNullable());
        assertEquals(false, decimalColumn.isPrimaryKey());
        assertEquals(false, decimalColumn.isAutoIncrement());

        ColumnMetaData bitColumn = metadata.getColumns().get(8);
        assertEquals("bit_column", bitColumn.getColumnName());
        assertEquals("bitColumn", bitColumn.getLowerCamelCaseColumnName());
        assertEquals("boolean", bitColumn.getJavaType());
        assertEquals(1, bitColumn.getColumnSize());
        assertEquals(true, bitColumn.isNullable());
        assertEquals(false, bitColumn.isPrimaryKey());
        assertEquals(false, bitColumn.isAutoIncrement());

        ColumnMetaData dateColumn = metadata.getColumns().get(9);
        assertEquals("date_column", dateColumn.getColumnName());
        assertEquals("dateColumn", dateColumn.getLowerCamelCaseColumnName());
        assertEquals("java.time.LocalDateTime", dateColumn.getJavaType());
        assertEquals(10, dateColumn.getColumnSize());
        assertEquals(true, dateColumn.isNullable());
        assertEquals(false, dateColumn.isPrimaryKey());
        assertEquals(false, dateColumn.isAutoIncrement());

        ColumnMetaData datetimeColumn = metadata.getColumns().get(10);
        assertEquals("datetime_column", datetimeColumn.getColumnName());
        assertEquals("datetimeColumn", datetimeColumn.getLowerCamelCaseColumnName());
        assertEquals("java.time.LocalDateTime", datetimeColumn.getJavaType());
        assertEquals(19, datetimeColumn.getColumnSize());
        assertEquals(true, datetimeColumn.isNullable());
        assertEquals(false, datetimeColumn.isPrimaryKey());
        assertEquals(false, datetimeColumn.isAutoIncrement());

        ColumnMetaData timestampColumn = metadata.getColumns().get(11);
        assertEquals("timestamp_column", timestampColumn.getColumnName());
        assertEquals("timestampColumn", timestampColumn.getLowerCamelCaseColumnName());
        assertEquals("java.time.LocalDateTime", timestampColumn.getJavaType());
        assertEquals(19, timestampColumn.getColumnSize());
        assertEquals(false, timestampColumn.isNullable());
        assertEquals(false, timestampColumn.isPrimaryKey());
        assertEquals(false, timestampColumn.isAutoIncrement());

        ColumnMetaData timeColumn = metadata.getColumns().get(12);
        assertEquals("time_column", timeColumn.getColumnName());
        assertEquals("timeColumn", timeColumn.getLowerCamelCaseColumnName());
        assertEquals("java.time.LocalDateTime", timeColumn.getJavaType());
        assertEquals(8, timeColumn.getColumnSize());
        assertEquals(true, timeColumn.isNullable());
        assertEquals(false, timeColumn.isPrimaryKey());
        assertEquals(false, timeColumn.isAutoIncrement());

        ColumnMetaData yearColumn = metadata.getColumns().get(13);
        assertEquals("year_column", yearColumn.getColumnName());
        assertEquals("yearColumn", yearColumn.getLowerCamelCaseColumnName());
        assertEquals("java.time.LocalDateTime", yearColumn.getJavaType());
        assertEquals(4, yearColumn.getColumnSize());
        assertEquals(true, yearColumn.isNullable());
        assertEquals(false, yearColumn.isPrimaryKey());
        assertEquals(false, yearColumn.isAutoIncrement());

        ColumnMetaData binaryColumn = metadata.getColumns().get(14);
        assertEquals("binary_column", binaryColumn.getColumnName());
        assertEquals("binaryColumn", binaryColumn.getLowerCamelCaseColumnName());
        assertEquals("byte[]", binaryColumn.getJavaType());
        assertEquals(100, binaryColumn.getColumnSize());
        assertEquals(true, binaryColumn.isNullable());
        assertEquals(false, binaryColumn.isPrimaryKey());
        assertEquals(false, binaryColumn.isAutoIncrement());

        ColumnMetaData varbinaryColumn = metadata.getColumns().get(15);
        assertEquals("varbinary_column", varbinaryColumn.getColumnName());
        assertEquals("varbinaryColumn", varbinaryColumn.getLowerCamelCaseColumnName());
        assertEquals("byte[]", varbinaryColumn.getJavaType());
        assertEquals(100, varbinaryColumn.getColumnSize());
        assertEquals(true, varbinaryColumn.isNullable());
        assertEquals(false, varbinaryColumn.isPrimaryKey());
        assertEquals(false, varbinaryColumn.isAutoIncrement());

        ColumnMetaData blobColumn = metadata.getColumns().get(16);
        assertEquals("blob_column", blobColumn.getColumnName());
        assertEquals("blobColumn", blobColumn.getLowerCamelCaseColumnName());
        assertEquals("byte[]", blobColumn.getJavaType());
        assertEquals(65535, blobColumn.getColumnSize());
        assertEquals(true, blobColumn.isNullable());
        assertEquals(false, blobColumn.isPrimaryKey());
        assertEquals(false, blobColumn.isAutoIncrement());

        ColumnMetaData tinyblobColumn = metadata.getColumns().get(17);
        assertEquals("tinyblob_column", tinyblobColumn.getColumnName());
        assertEquals("tinyblobColumn", tinyblobColumn.getLowerCamelCaseColumnName());
        assertEquals("byte[]", tinyblobColumn.getJavaType());
        assertEquals(255, tinyblobColumn.getColumnSize());
        assertEquals(true, tinyblobColumn.isNullable());
        assertEquals(false, tinyblobColumn.isPrimaryKey());
        assertEquals(false, tinyblobColumn.isAutoIncrement());

        ColumnMetaData mediumblobColumn = metadata.getColumns().get(18);
        assertEquals("mediumblob_column", mediumblobColumn.getColumnName());
        assertEquals("mediumblobColumn", mediumblobColumn.getLowerCamelCaseColumnName());
        assertEquals("byte[]", mediumblobColumn.getJavaType());
        assertEquals(16777215, mediumblobColumn.getColumnSize());
        assertEquals(true, mediumblobColumn.isNullable());
        assertEquals(false, mediumblobColumn.isPrimaryKey());
        assertEquals(false, mediumblobColumn.isAutoIncrement());

        ColumnMetaData longblobColumn = metadata.getColumns().get(19);
        assertEquals("longblob_column", longblobColumn.getColumnName());
        assertEquals("longblobColumn", longblobColumn.getLowerCamelCaseColumnName());
        assertEquals("byte[]", longblobColumn.getJavaType());
        assertEquals(2147483647, longblobColumn.getColumnSize());
        assertEquals(true, longblobColumn.isNullable());
        assertEquals(false, longblobColumn.isPrimaryKey());
        assertEquals(false, longblobColumn.isAutoIncrement());

        ColumnMetaData enumColumn = metadata.getColumns().get(20);
        assertEquals("enum_column", enumColumn.getColumnName());
        assertEquals("enumColumn", enumColumn.getLowerCamelCaseColumnName());
        assertEquals("String", enumColumn.getJavaType());
        assertEquals(1, enumColumn.getColumnSize());
        assertEquals(true, enumColumn.isNullable());
        assertEquals(false, enumColumn.isPrimaryKey());
        assertEquals(false, enumColumn.isAutoIncrement());
        EnumMetaData expectedEnum = new EnumMetaData("test_table", "enum_column", "enum('a','b','c')");
        assertEquals("TestTableEnumColumn", expectedEnum.getEnumName());
        assertEquals(List.of("a", "b", "c"), expectedEnum.getEnumValues());
    }

}
