package dev.stormcat.tools.generator.jdbc;

import dev.stormcat.tools.test.FileLoader;
import dev.stormcat.tools.test.MysqlExtension;
import dev.stormcat.tools.test.TestDataLoader;
import org.apache.velocity.app.VelocityEngine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MysqlExtension.class)
public class RepositoryGeneratorTest {

    private static VelocityEngine velocityEngine;

    @BeforeAll
    public static void setUp() {
        velocityEngine = VelocityEngineBuilder.build();
    }

    @Test
    public void write(Connection connection) throws Exception {
        TestDataLoader.load(connection, "/test-schema.sql");
        EnumAnalyzer enumAnalyzer = new EnumAnalyzer(connection);
        List<EnumMetaData> enumMetaDataList = enumAnalyzer.analyze();
        TableMetaData metadata = new TableAnalyzer(connection.getMetaData(), enumMetaDataList, "test_table").analyze();

        Writer writer = new StringWriter();
        RepositoryGenerator generator = new RepositoryGenerator(
                velocityEngine,
                metadata,
                "dev.stormcat.tools.repository",
                "dev.stormcat.tools.entity"
        );
        generator.write(writer);

        String expected = FileLoader.load("/RepositoryGeneratorTest/expected-repository.txt");
        assertEquals(expected, writer.toString());
    }

}
