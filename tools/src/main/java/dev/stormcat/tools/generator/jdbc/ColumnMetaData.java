package dev.stormcat.tools.generator.jdbc;

import lombok.*;

import java.sql.Types;

@Data
@Builder
//@RequiredArgsConstructor
public class ColumnMetaData {

    private final String columnName;

    private final int dataType;

    private final boolean isNullable;

    private final boolean isAutoIncrement;

    private final boolean isPrimaryKey;

    private int columnSize;

    private final String decimalDigits;

    private final boolean isEnum;

    public String getCamelCaseColumnName() {
        String[] split = columnName.split("_");
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1).toLowerCase());
        }
        return sb.toString();
    }

    public String getJavaType() {
        return DataTypeConverter.toJavaType(dataType);
    }
}
