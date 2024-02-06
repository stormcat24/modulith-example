package dev.stormcat.tools.generator.jdbc;

import org.junit.jupiter.api.Test;

import java.sql.Types;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

public class DataTypeConverterTest {

    @Test
    public void convert() {
        assertEquals("boolean", DataTypeConverter.toJavaType(Types.BIT));
        assertEquals("boolean", DataTypeConverter.toJavaType(Types.BOOLEAN));
        assertEquals("short", DataTypeConverter.toJavaType(Types.SMALLINT));
        assertEquals("int", DataTypeConverter.toJavaType(Types.INTEGER));
        assertEquals("long", DataTypeConverter.toJavaType(Types.BIGINT));
        assertEquals("float", DataTypeConverter.toJavaType(Types.REAL));
        assertEquals("double", DataTypeConverter.toJavaType(Types.FLOAT));
        assertEquals("double", DataTypeConverter.toJavaType(Types.DOUBLE));
        assertEquals("java.math.BigDecimal", DataTypeConverter.toJavaType(Types.NUMERIC));
        assertEquals("java.math.BigDecimal", DataTypeConverter.toJavaType(Types.DECIMAL));
        assertEquals("String", DataTypeConverter.toJavaType(Types.CHAR));
        assertEquals("String", DataTypeConverter.toJavaType(Types.VARCHAR));
        assertEquals("String", DataTypeConverter.toJavaType(Types.LONGVARCHAR));
        assertEquals("String", DataTypeConverter.toJavaType(Types.NCHAR));
        assertEquals("String", DataTypeConverter.toJavaType(Types.NVARCHAR));
        assertEquals("String", DataTypeConverter.toJavaType(Types.LONGNVARCHAR));
        assertEquals("java.time.LocalDateTime", DataTypeConverter.toJavaType(Types.DATE));
        assertEquals("java.time.LocalDateTime", DataTypeConverter.toJavaType(Types.TIME));
        assertEquals("java.time.LocalDateTime", DataTypeConverter.toJavaType(Types.TIMESTAMP));
        assertEquals("byte[]", DataTypeConverter.toJavaType(Types.BINARY));
        assertEquals("byte[]", DataTypeConverter.toJavaType(Types.VARBINARY));
        assertEquals("byte[]", DataTypeConverter.toJavaType(Types.LONGVARBINARY));
        assertEquals("java.sql.Clob", DataTypeConverter.toJavaType(Types.CLOB));
        assertEquals("java.sql.Array", DataTypeConverter.toJavaType(Types.ARRAY));
        assertEquals("java.sql.Ref", DataTypeConverter.toJavaType(Types.REF));
        assertEquals("java.sql.Struct", DataTypeConverter.toJavaType(Types.STRUCT));
        assertEquals("java.net.URL", DataTypeConverter.toJavaType(Types.DATALINK));
        assertEquals("java.lang.Object", DataTypeConverter.toJavaType(Types.JAVA_OBJECT));
        assertEquals("java.lang.Object", DataTypeConverter.toJavaType(Types.OTHER));
        assertEquals("java.sql.RowId", DataTypeConverter.toJavaType(Types.ROWID));
        assertEquals("java.sql.SQLXML", DataTypeConverter.toJavaType(Types.SQLXML));
        assertThrowsExactly(IllegalArgumentException.class, () -> DataTypeConverter.toJavaType(0));
    }
}
