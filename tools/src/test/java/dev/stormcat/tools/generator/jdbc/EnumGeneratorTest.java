package dev.stormcat.tools.generator.jdbc;

import dev.stormcat.tools.test.FileLoader;
import org.apache.velocity.app.VelocityEngine;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;
import java.io.Writer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnumGeneratorTest {

    private static VelocityEngine velocityEngine;

    @BeforeAll
    public static void setUp() {
        velocityEngine = VelocityEngineBuilder.build();
    }

    @Test
    public void write() {
        EnumMetaData enumMetaData = new EnumMetaData("test_table", "enum_column", "enum('a','b','c')");

        Writer writer = new StringWriter();
        EnumGenerator generator = new EnumGenerator(velocityEngine, enumMetaData, "dev.stormcat.tools.entity");
        generator.write(writer);

        String expected = FileLoader.load("/EnumGeneratorTest/expected-enum.txt");
        assertEquals(expected, writer.toString());
    }
}
