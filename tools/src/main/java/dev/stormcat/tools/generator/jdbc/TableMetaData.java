package dev.stormcat.tools.generator.jdbc;

import dev.stormcat.tools.nlp.WordAnalyzer;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TableMetaData {

    private final String tableName;

    private final String lemmaTableName;

    private final List<ColumnMetaData> columns = new ArrayList<>();

    private final List<IndexMetaData> indexes = new ArrayList<>();

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
        columns.add(columnMetaData);
    }

    public void addIndex(IndexMetaData indexMetaData) {
        indexes.add(indexMetaData);
    }

    public void addAllIndex(List<IndexMetaData> indexMetaDataList) {
        indexes.addAll(indexMetaDataList);
    }

}
