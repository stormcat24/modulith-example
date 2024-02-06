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

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MysqlExtension.class)
public class CodeWriterTest {

    private static VelocityEngine velocityEngine;

    @BeforeAll
    public static void setUp() {
        velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("resource.loader", "class");
        velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.init();
    }

    @Test
    public void writeRepository_when_test_schema_sql(Connection connection) throws Exception {
        TestDataLoader.load(connection, "/TableAnalyzerTest/test-schema.sql");
        TableMetaData metadata = new TableAnalyzer(connection.getMetaData(), "test_table").analyze();

        Writer entityWriter = new StringWriter();
        Writer repositoryWriter = new StringWriter();


        CodeWriter codeWriter = new CodeWriter(
                velocityEngine,
                metadata,
                "dev.stormcat.tools.entity",
                "dev.stormcat.tools.repository",
                entityWriter,
                repositoryWriter
        );
        codeWriter.writeAll();

        String expectedEntity = FileLoader.load("/CodeWriterTest/expected-entity.txt");
        String expectedRepository = FileLoader.load("/CodeWriterTest/expected-repository.txt");
        assertEquals(expectedEntity, entityWriter.toString());
        assertEquals(expectedRepository, repositoryWriter.toString());
    }
}
