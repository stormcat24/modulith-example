package dev.stormcat.tools.generator.jdbc;

import lombok.Getter;
import picocli.CommandLine;

@Getter
public class SpringDataJdbcOption {

    @CommandLine.Option(names = { "--url" }, description = "the jdbc url", required = true)
    private String url;

    @CommandLine.Option(names = { "--username" }, description = "the jdbc username", required = true)
    private String username;

    @CommandLine.Option(names = { "--password" }, description = "the jdbc password", required = true)
    private String password;

    @CommandLine.Option(names = { "--ignore-table" }, description = "the ignored table names", required = false)
    private String[] ignoredTables = new String[] {};

    @CommandLine.Option(names = { "--repository-package-name" }, description = "the repository package name", required = true)
    private String repositoryPackageName;

    @CommandLine.Option(names = { "--entity-package-name" }, description = "the entity package name", required = true)
    private String entityPackageName;

}
