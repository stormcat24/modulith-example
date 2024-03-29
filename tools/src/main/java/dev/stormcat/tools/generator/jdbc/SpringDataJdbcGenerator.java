package dev.stormcat.tools.generator.jdbc;

import org.apache.velocity.app.VelocityEngine;
import picocli.CommandLine;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

public class SpringDataJdbcGenerator {

    public static void main(String[] args) {
        SpringDataJdbcOption option = new SpringDataJdbcOption();
        new CommandLine(option).parseArgs(args);
        List<String> ignoreTables = Arrays.asList(option.getIgnoredTables());

        Path currentDir = Paths.get("");
        Path generatedJavaDir = currentDir.resolve("src/generated/java");

        try (Connection connection = DriverManager.getConnection(option.getUrl(), option.getUsername(), option.getPassword())) {
            EnumAnalyzer enumAnalyzer = new EnumAnalyzer(connection);
            List<EnumMetaData> enumMetaDataList = enumAnalyzer.analyze();

            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet tables = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                if (!ignoreTables.contains(tableName)) {
                    TableMetaData tableMetaData = new TableAnalyzer(metaData, enumMetaDataList, tableName).analyze();
                    generate(generatedJavaDir, tableMetaData, option.getEntityPackageName(), option.getRepositoryPackageName());
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void generate(Path outputDirectoryRoot, TableMetaData tableMetaData, String entityPackageName, String repositoryPackageName) throws IOException {
        Path entityDirectory = resolveOutputDirectory(outputDirectoryRoot, entityPackageName);
        if (!entityDirectory.toFile().exists()) {
            entityDirectory.toFile().mkdirs();
        }

        Path repositoryDirectory = resolveOutputDirectory(outputDirectoryRoot, repositoryPackageName);
        if (!repositoryDirectory.toFile().exists()) {
            repositoryDirectory.toFile().mkdirs();
        }

        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.setProperty("resource.loader", "class");
        velocityEngine.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        velocityEngine.init();

        FileWriter entityWriter = null;
        FileWriter repositoryWriter = null;

        try {
            entityWriter = new FileWriter(entityDirectory.resolve(tableMetaData.getEntityName() + ".java").toFile());
            repositoryWriter = new FileWriter(repositoryDirectory.resolve(tableMetaData.getRepositoryName() + ".java").toFile());

            new EntityGenerator(velocityEngine, tableMetaData, entityPackageName).write(entityWriter);
            new RepositoryGenerator(velocityEngine, tableMetaData, repositoryPackageName, entityPackageName).write(repositoryWriter);

            tableMetaData.getColumns().stream().filter(cm -> cm.getEnumMetaData() != null).map(ColumnMetaData::getEnumMetaData).forEach(em -> {
                writeEnum(velocityEngine, em, entityDirectory, entityPackageName);
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            entityWriter.close();
            repositoryWriter.close();
        }
    }

    private static void writeEnum(VelocityEngine velocityEngine, EnumMetaData enumMetaData, Path directory, String packageName) {
        try ( FileWriter enumWriter = new FileWriter(directory.resolve(enumMetaData.getEnumName() + ".java").toFile())) {
            new EnumGenerator(velocityEngine, enumMetaData, packageName).write(enumWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Path resolveOutputDirectory(Path root, String packageName) {
        return root.resolve(packageName.replace(".", "/"));
    }
}
