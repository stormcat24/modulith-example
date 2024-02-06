package dev.stormcat.tools.generator.jdbc;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;

public final class DataTypeConverter {

    public static String toJavaType(int dataType) {
        return switch (dataType) {
            case Types.BIT, Types.BOOLEAN -> "boolean";
            case Types.TINYINT -> "byte";
            case Types.SMALLINT -> "short";
            case Types.INTEGER -> "int";
            case Types.BIGINT -> "long";
            case Types.REAL -> "float";
            case Types.FLOAT, Types.DOUBLE -> "double";
            case Types.NUMERIC, Types.DECIMAL -> BigDecimal.class.getName();
            case Types.CHAR, Types.VARCHAR, Types.LONGVARCHAR, Types.NCHAR, Types.NVARCHAR, Types.LONGNVARCHAR -> "String";
            case Types.DATE, Types.TIME, Types.TIMESTAMP -> LocalDateTime.class.getName();
            case Types.BINARY, Types.VARBINARY, Types.LONGVARBINARY, Types.BLOB, Types.CLOB -> "byte[]";
            case Types.ARRAY -> Array.class.getName();
            case Types.REF -> Ref.class.getName();
            case Types.STRUCT -> Struct.class.getName();
            case Types.DATALINK -> URL.class.getName();
            case Types.JAVA_OBJECT, Types.OTHER -> Object.class.getName();
            case Types.ROWID -> RowId.class.getName();
            case Types.SQLXML -> SQLXML.class.getName();
            default -> throw new IllegalArgumentException("Unsupported data type: " + dataType);
        };
    }
}
