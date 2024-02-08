package dev.stormcat.tools.generator.jdbc;

import dev.stormcat.tools.nlp.WordAnalyzer;
import lombok.Getter;

import java.util.List;

@Getter
public class EnumMetaData {

    private final String tableName;

    private final String columnName;

    private final String columnType;

    private final String enumName;

    public EnumMetaData(String tableName, String columnName, String columnType) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.columnType = columnType;

        String camelTableName = StringUtil.toCamelCase(new WordAnalyzer().toLemmaWithUnderscore(tableName));
        String camelColumnName = StringUtil.toCamelCase(columnName);
        this.enumName = camelTableName + camelColumnName;
    }

    public List<String> getEnumValues() {
        String sanitized = columnType.replaceAll("enum\\(", "")
                .replaceAll("\\)", "")
                .replaceAll("'", "");
        return List.of(sanitized.split(","));
    }

}
