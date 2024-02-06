package dev.stormcat.tools.test;

import org.junit.jupiter.api.extension.*;
import org.testcontainers.containers.MySQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;

public class MysqlExtension implements BeforeAllCallback, AfterEachCallback, ExtensionContext.Store.CloseableResource, ParameterResolver {

    private static MySQLContainer mysqlContainer;
    private Connection connection;

    public MysqlExtension() {
        mysqlContainer = new MySQLContainer("mysql:5.7")
                .withDatabaseName("tools_test")
                .withUsername("username")
                .withPassword("password");
        mysqlContainer.start();
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        String jdbcUrl = mysqlContainer.getJdbcUrl();
        String username = mysqlContainer.getUsername();
        String password = mysqlContainer.getPassword();
        connection = DriverManager.getConnection(jdbcUrl, username, password);
        extensionContext.getStore(ExtensionContext.Namespace.GLOBAL).put("connection", connection);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Override
    public void close() throws Throwable {
        if (mysqlContainer != null) {
            mysqlContainer.stop();
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType() == Connection.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return connection;
    }
}
