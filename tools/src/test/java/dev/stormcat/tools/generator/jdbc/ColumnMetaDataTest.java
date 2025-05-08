package dev.stormcat.tools.generator.jdbc;

import org.junit.jupiter.api.Test;

import java.sql.Types;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ColumnMetaDataTest {

    @Test
    void getJavaType_when_DATA_TYPE_is_INTEGER() {
        ColumnMetaData columnMetaData = ColumnMetaData.builder()
                .dataType(Types.INTEGER)
                .build();

        assertEquals("int", columnMetaData.getJavaType());
    }

    @Test
    void getArgumentClause_when_DATA_TYPE_is_INTEGER() {
        ColumnMetaData columnMetaData = ColumnMetaData.builder()
                .dataType(Types.INTEGER)
                .columnName("test_column")
                .build();

        assertEquals("@Param(\"testColumn\") int testColumn", columnMetaData.getArgumentClause());
    }
}
