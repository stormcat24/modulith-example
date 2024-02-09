package dev.stormcat.tools.generator.jdbc;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class IndexMetaData {

    private final String indexName;

    private final boolean isUnique;

    private List<ColumnMetaData> columns = new ArrayList<>();

    public void addColumn(ColumnMetaData column) {
        columns.add(column);
    }

    public String getUpperCamelColumnPairName() {
        return columns.stream().map(ColumnMetaData::getUpperCamelCaseColumnName).reduce((a, b) -> a + "And" + b).orElse("");
    }

    public String getJavaArgumentClause() {
        return columns.stream().map(ColumnMetaData::getArgumentClause).reduce((a, b) -> a + ", " + b).orElse("");
    }

    public String getSpringDataJdbcArgumentClause() {
        return columns.stream().map(ColumnMetaData::getSpringDataJdbcArgumentClause).reduce((a, b) -> a + " AND " + b).orElse("");
    }
}
