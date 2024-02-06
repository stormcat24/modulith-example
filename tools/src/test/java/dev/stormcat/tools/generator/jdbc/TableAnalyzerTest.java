package dev.stormcat.tools.generator.jdbc;

import dev.stormcat.tools.test.MysqlExtension;
import dev.stormcat.tools.test.TestDataLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.util.Set;

@ExtendWith(MysqlExtension.class)
public class TableAnalyzerTest {

    @Test
    void analyze_when_test_schema_sql(Connection connection) throws Exception {
        TestDataLoader.load(connection, "/TableAnalyzerTest/test-schema.sql");
        TableMetaData metadata = new TableAnalyzer(connection.getMetaData(), "test_table").analyze();

        assertEquals("test_table", metadata.getTableName());
        assertEquals("TestTable", metadata.getEntityName());
        assertEquals("test_table", metadata.getLemmaTableName());
        assertEquals("TestTableRepository", metadata.getRepositoryName());
        assertEquals(Set.of("id"), metadata.getPrimaryKeyColumnNames());

        ColumnMetaData idColumn = metadata.getColumnMetaDataList().get(0);
        assertEquals("id", idColumn.getColumnName());
        assertEquals("Id", idColumn.getCamelCaseColumnName());
        assertEquals("int", idColumn.getJavaType());
        assertEquals(10, idColumn.getColumnSize());
        assertEquals(false, idColumn.isNullable());
        assertEquals(true, idColumn.isPrimaryKey());
        assertEquals(true, idColumn.isAutoIncrement());
        assertEquals(false, idColumn.isEnum());

        ColumnMetaData varcharColumn = metadata.getColumnMetaDataList().get(1);
        assertEquals("varchar_column", varcharColumn.getColumnName());
        assertEquals("VarcharColumn", varcharColumn.getCamelCaseColumnName());
        assertEquals("String", varcharColumn.getJavaType());
        assertEquals(100, varcharColumn.getColumnSize());
        assertEquals(true, varcharColumn.isNullable());
        assertEquals(false, varcharColumn.isPrimaryKey());
        assertEquals(false, varcharColumn.isAutoIncrement());
        assertEquals(false, varcharColumn.isEnum());

        ColumnMetaData charColumn = metadata.getColumnMetaDataList().get(2);
        assertEquals("char_column", charColumn.getColumnName());
        assertEquals("CharColumn", charColumn.getCamelCaseColumnName());
        assertEquals("String", charColumn.getJavaType());
        assertEquals(100, charColumn.getColumnSize());
        assertEquals(true, charColumn.isNullable());
        assertEquals(false, charColumn.isPrimaryKey());
        assertEquals(false, charColumn.isAutoIncrement());
        assertEquals(false, charColumn.isEnum());

        ColumnMetaData textColumn = metadata.getColumnMetaDataList().get(3);
        assertEquals("text_column", textColumn.getColumnName());
        assertEquals("TextColumn", textColumn.getCamelCaseColumnName());
        assertEquals("String", textColumn.getJavaType());
        assertEquals(65535, textColumn.getColumnSize());
        assertEquals(true, textColumn.isNullable());
        assertEquals(false, textColumn.isPrimaryKey());
        assertEquals(false, textColumn.isAutoIncrement());
        assertEquals(false, textColumn.isEnum());

        ColumnMetaData longtextColumn = metadata.getColumnMetaDataList().get(4);
        assertEquals("longtext_column", longtextColumn.getColumnName());
        assertEquals("LongtextColumn", longtextColumn.getCamelCaseColumnName());
        assertEquals("String", longtextColumn.getJavaType());
        assertEquals(2147483647, longtextColumn.getColumnSize());
        assertEquals(true, longtextColumn.isNullable());
        assertEquals(false, longtextColumn.isPrimaryKey());
        assertEquals(false, longtextColumn.isAutoIncrement());
        assertEquals(false, longtextColumn.isEnum());

        ColumnMetaData floatColumn = metadata.getColumnMetaDataList().get(5);
        assertEquals("float_column", floatColumn.getColumnName());
        assertEquals("FloatColumn", floatColumn.getCamelCaseColumnName());
        assertEquals("float", floatColumn.getJavaType());
        assertEquals(12, floatColumn.getColumnSize());
        assertEquals(true, floatColumn.isNullable());
        assertEquals(false, floatColumn.isPrimaryKey());
        assertEquals(false, floatColumn.isAutoIncrement());
        assertEquals(false, floatColumn.isEnum());

        ColumnMetaData doubleColumn = metadata.getColumnMetaDataList().get(6);
        assertEquals("double_column", doubleColumn.getColumnName());
        assertEquals("DoubleColumn", doubleColumn.getCamelCaseColumnName());
        assertEquals("double", doubleColumn.getJavaType());
        assertEquals(22, doubleColumn.getColumnSize());
        assertEquals(true, doubleColumn.isNullable());
        assertEquals(false, doubleColumn.isPrimaryKey());
        assertEquals(false, doubleColumn.isAutoIncrement());
        assertEquals(false, doubleColumn.isEnum());

        ColumnMetaData decimalColumn = metadata.getColumnMetaDataList().get(7);
        assertEquals("decimal_column", decimalColumn.getColumnName());
        assertEquals("DecimalColumn", decimalColumn.getCamelCaseColumnName());
        assertEquals("java.math.BigDecimal", decimalColumn.getJavaType());
        assertEquals(10, decimalColumn.getColumnSize());
        assertEquals(true, decimalColumn.isNullable());
        assertEquals(false, decimalColumn.isPrimaryKey());
        assertEquals(false, decimalColumn.isAutoIncrement());
        assertEquals(false, decimalColumn.isEnum());

        ColumnMetaData bitColumn = metadata.getColumnMetaDataList().get(8);
        assertEquals("bit_column", bitColumn.getColumnName());
        assertEquals("BitColumn", bitColumn.getCamelCaseColumnName());
        assertEquals("boolean", bitColumn.getJavaType());
        assertEquals(1, bitColumn.getColumnSize());
        assertEquals(true, bitColumn.isNullable());
        assertEquals(false, bitColumn.isPrimaryKey());
        assertEquals(false, bitColumn.isAutoIncrement());
        assertEquals(false, bitColumn.isEnum());

        ColumnMetaData dateColumn = metadata.getColumnMetaDataList().get(9);
        assertEquals("date_column", dateColumn.getColumnName());
        assertEquals("DateColumn", dateColumn.getCamelCaseColumnName());
        assertEquals("java.time.LocalDateTime", dateColumn.getJavaType());
        assertEquals(10, dateColumn.getColumnSize());
        assertEquals(true, dateColumn.isNullable());
        assertEquals(false, dateColumn.isPrimaryKey());
        assertEquals(false, dateColumn.isAutoIncrement());
        assertEquals(false, dateColumn.isEnum());

        ColumnMetaData datetimeColumn = metadata.getColumnMetaDataList().get(10);
        assertEquals("datetime_column", datetimeColumn.getColumnName());
        assertEquals("DatetimeColumn", datetimeColumn.getCamelCaseColumnName());
        assertEquals("java.time.LocalDateTime", datetimeColumn.getJavaType());
        assertEquals(19, datetimeColumn.getColumnSize());
        assertEquals(true, datetimeColumn.isNullable());
        assertEquals(false, datetimeColumn.isPrimaryKey());
        assertEquals(false, datetimeColumn.isAutoIncrement());
        assertEquals(false, datetimeColumn.isEnum());

        ColumnMetaData timestampColumn = metadata.getColumnMetaDataList().get(11);
        assertEquals("timestamp_column", timestampColumn.getColumnName());
        assertEquals("TimestampColumn", timestampColumn.getCamelCaseColumnName());
        assertEquals("java.time.LocalDateTime", timestampColumn.getJavaType());
        assertEquals(19, timestampColumn.getColumnSize());
        assertEquals(false, timestampColumn.isNullable());
        assertEquals(false, timestampColumn.isPrimaryKey());
        assertEquals(false, timestampColumn.isAutoIncrement());
        assertEquals(false, timestampColumn.isEnum());

        ColumnMetaData timeColumn = metadata.getColumnMetaDataList().get(12);
        assertEquals("time_column", timeColumn.getColumnName());
        assertEquals("TimeColumn", timeColumn.getCamelCaseColumnName());
        assertEquals("java.time.LocalDateTime", timeColumn.getJavaType());
        assertEquals(8, timeColumn.getColumnSize());
        assertEquals(true, timeColumn.isNullable());
        assertEquals(false, timeColumn.isPrimaryKey());
        assertEquals(false, timeColumn.isAutoIncrement());
        assertEquals(false, timeColumn.isEnum());

        ColumnMetaData yearColumn = metadata.getColumnMetaDataList().get(13);
        assertEquals("year_column", yearColumn.getColumnName());
        assertEquals("YearColumn", yearColumn.getCamelCaseColumnName());
        assertEquals("java.time.LocalDateTime", yearColumn.getJavaType());
        assertEquals(4, yearColumn.getColumnSize());
        assertEquals(true, yearColumn.isNullable());
        assertEquals(false, yearColumn.isPrimaryKey());
        assertEquals(false, yearColumn.isAutoIncrement());
        assertEquals(false, yearColumn.isEnum());

        ColumnMetaData binaryColumn = metadata.getColumnMetaDataList().get(14);
        assertEquals("binary_column", binaryColumn.getColumnName());
        assertEquals("BinaryColumn", binaryColumn.getCamelCaseColumnName());
        assertEquals("byte[]", binaryColumn.getJavaType());
        assertEquals(100, binaryColumn.getColumnSize());
        assertEquals(true, binaryColumn.isNullable());
        assertEquals(false, binaryColumn.isPrimaryKey());
        assertEquals(false, binaryColumn.isAutoIncrement());
        assertEquals(false, binaryColumn.isEnum());

        ColumnMetaData varbinaryColumn = metadata.getColumnMetaDataList().get(15);
        assertEquals("varbinary_column", varbinaryColumn.getColumnName());
        assertEquals("VarbinaryColumn", varbinaryColumn.getCamelCaseColumnName());
        assertEquals("byte[]", varbinaryColumn.getJavaType());
        assertEquals(100, varbinaryColumn.getColumnSize());
        assertEquals(true, varbinaryColumn.isNullable());
        assertEquals(false, varbinaryColumn.isPrimaryKey());
        assertEquals(false, varbinaryColumn.isAutoIncrement());
        assertEquals(false, varbinaryColumn.isEnum());

        ColumnMetaData blobColumn = metadata.getColumnMetaDataList().get(16);
        assertEquals("blob_column", blobColumn.getColumnName());
        assertEquals("BlobColumn", blobColumn.getCamelCaseColumnName());
        assertEquals("byte[]", blobColumn.getJavaType());
        assertEquals(65535, blobColumn.getColumnSize());
        assertEquals(true, blobColumn.isNullable());
        assertEquals(false, blobColumn.isPrimaryKey());
        assertEquals(false, blobColumn.isAutoIncrement());
        assertEquals(false, blobColumn.isEnum());

        ColumnMetaData tinyblobColumn = metadata.getColumnMetaDataList().get(17);
        assertEquals("tinyblob_column", tinyblobColumn.getColumnName());
        assertEquals("TinyblobColumn", tinyblobColumn.getCamelCaseColumnName());
        assertEquals("byte[]", tinyblobColumn.getJavaType());
        assertEquals(255, tinyblobColumn.getColumnSize());
        assertEquals(true, tinyblobColumn.isNullable());
        assertEquals(false, tinyblobColumn.isPrimaryKey());
        assertEquals(false, tinyblobColumn.isAutoIncrement());
        assertEquals(false, tinyblobColumn.isEnum());

        ColumnMetaData mediumblobColumn = metadata.getColumnMetaDataList().get(18);
        assertEquals("mediumblob_column", mediumblobColumn.getColumnName());
        assertEquals("MediumblobColumn", mediumblobColumn.getCamelCaseColumnName());
        assertEquals("byte[]", mediumblobColumn.getJavaType());
        assertEquals(16777215, mediumblobColumn.getColumnSize());
        assertEquals(true, mediumblobColumn.isNullable());
        assertEquals(false, mediumblobColumn.isPrimaryKey());
        assertEquals(false, mediumblobColumn.isAutoIncrement());
        assertEquals(false, mediumblobColumn.isEnum());

        ColumnMetaData longblobColumn = metadata.getColumnMetaDataList().get(19);
        assertEquals("longblob_column", longblobColumn.getColumnName());
        assertEquals("LongblobColumn", longblobColumn.getCamelCaseColumnName());
        assertEquals("byte[]", longblobColumn.getJavaType());
        assertEquals(2147483647, longblobColumn.getColumnSize());
        assertEquals(true, longblobColumn.isNullable());
        assertEquals(false, longblobColumn.isPrimaryKey());
        assertEquals(false, longblobColumn.isAutoIncrement());
        assertEquals(false, longblobColumn.isEnum());

        ColumnMetaData enumColumn = metadata.getColumnMetaDataList().get(20);
        assertEquals("enum_column", enumColumn.getColumnName());
        assertEquals("EnumColumn", enumColumn.getCamelCaseColumnName());
        assertEquals("String", enumColumn.getJavaType());
        assertEquals(3, enumColumn.getColumnSize());
        assertEquals(true, enumColumn.isNullable());
        assertEquals(false, enumColumn.isPrimaryKey());
        assertEquals(true, enumColumn.isEnum());
        assertEquals(false, enumColumn.isAutoIncrement());

    }

}
