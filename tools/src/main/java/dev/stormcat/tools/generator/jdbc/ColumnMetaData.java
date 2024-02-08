package dev.stormcat.tools.generator.jdbc;

import lombok.*;

import java.sql.Types;

@Data
@Builder
public class ColumnMetaData {

    private final String columnName;

    private final int dataType;

    private final boolean isNullable;

    private final boolean isAutoIncrement;

    private final boolean isPrimaryKey;

    private int columnSize;

    private final String decimalDigits;

    private EnumMetaData enumMetaData;

    public String getLowerCamelCaseColumnName() {
        String upperCamelCase = StringUtil.toCamelCase(columnName);
        return upperCamelCase.substring(0, 1).toLowerCase() + upperCamelCase.substring(1);
    }

    public String getJavaType() {
        return DataTypeConverter.toJavaType(dataType);
    }
}
