package dev.stormcat.tools.nlp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WordAnalyzerTest {

    private WordAnalyzer wordAnalyzer;

    @BeforeEach
    public void setUp() {
        wordAnalyzer = new WordAnalyzer();
    }

    @Test
    public void createCoreDocument_ShouldThrowException_WhenTextIsEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            wordAnalyzer.createCoreDocument("");
        });
        assertEquals("text is empty", exception.getMessage());
    }

    @Test
    public void createCoreDocument_ShouldThrowException_WhenTextContainsSpace() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            wordAnalyzer.createCoreDocument("book cars");
        });
        assertEquals("text should not contain space", exception.getMessage());
    }

    @Test
    public void isSingular_ShouldReturnTrue_WhenTextIsSingularNoun() {
        assertTrue(wordAnalyzer.isSingular("book"));
    }

    @Test
    public void isSingular_ShouldReturnFalse_WhenTextIsPluralNoun() {
        assertFalse(wordAnalyzer.isSingular("posts"));
    }

    @Test
    public void toLemma_ShouldReturnSingular_WhenTextIsSingularNoun() {
        assertEquals("book", wordAnalyzer.toLemma("book"));
    }

    @Test
    public void toLemma_ShouldReturnSingular_WhenTextIsPluralNoun() {
        assertEquals("post", wordAnalyzer.toLemma("posts"));
    }

    @Test
    public void toLemmaWithUnderscore_ShouldReturnSingular_WhenTextIsPluralNoun() {
        assertEquals("post", wordAnalyzer.toLemmaWithUnderscore("posts"));
    }

    @Test
    public void toLemmaWithUnderscore_ShouldReturnSingular_WhenTextIsPluralNounWithUnderscore() {
        assertEquals("test_user", wordAnalyzer.toLemmaWithUnderscore("test_users"));
    }
}
