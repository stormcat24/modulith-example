package dev.stormcat.tools.generator.jdbc;

import org.junit.jupiter.api.Test;

import java.sql.Types;

import static org.junit.jupiter.api.Assertions.*;

public class IndexMetaDataTest {

    @Test
    public void getColumnPairName_when_columns_is_empty() {
        IndexMetaData indexMetaData = new IndexMetaData("indexName", true);
        assertEquals("", indexMetaData.getUpperCamelColumnPairName());
    }

    @Test
    public void getColumnPairName_when_columns_is_not_empty() {
        IndexMetaData indexMetaData = new IndexMetaData("indexName", true);

        ColumnMetaData column1 = ColumnMetaData.builder()
                .columnName("test_column1")
                .dataType(Types.INTEGER)
                .isNullable(false)
                .isAutoIncrement(false)
                .isPrimaryKey(false)
                .columnSize(10)
                .build();
        ColumnMetaData column2 = ColumnMetaData.builder()
                .columnName("test_column2")
                .dataType(Types.INTEGER)
                .isNullable(false)
                .isAutoIncrement(false)
                .isPrimaryKey(false)
                .columnSize(10)
                .build();

        indexMetaData.addColumn(column1);
        indexMetaData.addColumn(column2);
        assertEquals("TestColumn1AndTestColumn2", indexMetaData.getUpperCamelColumnPairName());
    }

    @Test
    public void getJavaArgumentClause_when_columns_is_empty() {
        IndexMetaData indexMetaData = new IndexMetaData("indexName", true);
        assertEquals("", indexMetaData.getJavaArgumentClause());
    }

    @Test
    public void getJavaArgumentClause_when_columns_is_not_empty() {
        IndexMetaData indexMetaData = new IndexMetaData("indexName", true);

        ColumnMetaData column1 = ColumnMetaData.builder()
                .dataType(Types.INTEGER)
                .columnName("test_column1")
                .build();
        ColumnMetaData column2 = ColumnMetaData.builder()
                .dataType(Types.INTEGER)
                .columnName("test_column2")
                .build();

        indexMetaData.addColumn(column1);
        indexMetaData.addColumn(column2);
        assertEquals("@Param(\"testColumn1\") int testColumn1, @Param(\"testColumn2\") int testColumn2", indexMetaData.getJavaArgumentClause());
    }

    @Test
    public void getSpringDataJdbcArgumentClause_when_columns_is_empty() {
        IndexMetaData indexMetaData = new IndexMetaData("indexName", true);
        assertEquals("", indexMetaData.getSpringDataJdbcArgumentClause());
    }

    @Test
    public void getSprintDataJdbcArgumentClause_when_columns_is_not_empty() {
        IndexMetaData indexMetaData = new IndexMetaData("indexName", true);

        ColumnMetaData column1 = ColumnMetaData.builder()
                .columnName("test_column1")
                .dataType(Types.INTEGER)
                .isNullable(false)
                .isAutoIncrement(false)
                .isPrimaryKey(false)
                .columnSize(10)
                .build();
        ColumnMetaData column2 = ColumnMetaData.builder()
                .columnName("test_column2")
                .dataType(Types.INTEGER)
                .isNullable(false)
                .isAutoIncrement(false)
                .isPrimaryKey(false)
                .columnSize(10)
                .build();

        indexMetaData.addColumn(column1);
        indexMetaData.addColumn(column2);
        assertEquals("test_column1 = :testColumn1 AND test_column2 = :testColumn2", indexMetaData.getSpringDataJdbcArgumentClause());
    }
}
