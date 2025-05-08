package dev.stormcat.tools.generator.jdbc;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TableMetaDataTest {

    @Test
    void whenTableNameIsSingularNoun() {
        TableMetaData tableMetaData = new TableMetaData("car");

        assertEquals("Car", tableMetaData.getEntityName());
        assertEquals("CarRepository", tableMetaData.getRepositoryName());
    }

    @Test
    void whenTableNameIsSingularNounWithUnderscore() {
        TableMetaData tableMetaData = new TableMetaData("electric_car");

        assertEquals("ElectricCar", tableMetaData.getEntityName());
        assertEquals("ElectricCarRepository", tableMetaData.getRepositoryName());
    }

    @Test
    void whenTableNameIsPluralNoun() {
        TableMetaData tableMetaData = new TableMetaData("posts");

        assertEquals("Post", tableMetaData.getEntityName());
        assertEquals("PostRepository", tableMetaData.getRepositoryName());
    }

    @Test
    void whenTableNameIsPluralNounWithUnderscore() {
        TableMetaData tableMetaData = new TableMetaData("test_users");

        assertEquals("TestUser", tableMetaData.getEntityName());
        assertEquals("TestUserRepository", tableMetaData.getRepositoryName());
    }
}
