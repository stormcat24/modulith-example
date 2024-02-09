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

    public String getUpperCamelCaseColumnName() {
        return StringUtil.toCamelCase(columnName);
    }
    public String getLowerCamelCaseColumnName() {
        String upperCamelCase = getUpperCamelCaseColumnName();
        return upperCamelCase.substring(0, 1).toLowerCase() + upperCamelCase.substring(1);
    }

    public String getJavaType() {
        return DataTypeConverter.toJavaType(dataType);
    }

    public String getArgumentClause() {
        return String.format("@Param(\"%s\") %s %s", getLowerCamelCaseColumnName(), getJavaType(), getLowerCamelCaseColumnName());
    }

    public String getSpringDataJdbcArgumentClause()  {
        return String.format("%s = :%s", columnName, getLowerCamelCaseColumnName());
    }
}
