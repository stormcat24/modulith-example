package dev.stormcat.tools.generator.jdbc;

import dev.stormcat.tools.nlp.WordAnalyzer;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
public class TableMetaData {

    private final String tableName;

    @Setter(AccessLevel.NONE)
    private String lemmaTableName;

    private Set<String> primaryKeyColumnNames;

    @Setter(AccessLevel.NONE)
    private List<ColumnMetaData> columnMetaDataList = new ArrayList<>();

    public TableMetaData(String tableName) {
        this.tableName = tableName;
        lemmaTableName = new WordAnalyzer().toLemmaWithUnderscore(tableName);
    }

    public String getEntityName() {
        String[] split = lemmaTableName.split("_");
        StringBuilder sb = new StringBuilder();
        for (String s : split) {
            sb.append(s.substring(0, 1).toUpperCase()).append(s.substring(1).toLowerCase());
        }
        return sb.toString();
    }

    public String getRepositoryName() {
        return String.format("%sRepository", getEntityName());
    }

    public void addColumnMetaData(ColumnMetaData columnMetaData) {
        columnMetaDataList.add(columnMetaData);
    }

}
