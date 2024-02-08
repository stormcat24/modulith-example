package dev.stormcat.tools.generator.jdbc;

import dev.stormcat.tools.test.MysqlExtension;
import dev.stormcat.tools.test.TestDataLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MysqlExtension.class)
public class EnumMetaDataTest {

    @Test
    public void analyze(Connection connection) {
        TestDataLoader.load(connection, "/TableAnalyzerTest/test-schema.sql");

        List<EnumMetaData> enumMetaDataList = new EnumAnalyzer(connection).analyze();
        assertEquals(1, enumMetaDataList.size());
        EnumMetaData actual = enumMetaDataList.get(0);
        assertEquals("TestTableEnumColumn", actual.getEnumName());
        assertEquals(List.of("a", "b", "c"), actual.getEnumValues());
    }
}
