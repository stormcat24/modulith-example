package dev.stormcat.tools.nlp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WordAnalyzerTest {

    private WordAnalyzer wordAnalyzer;

    @BeforeEach
    public void setUp() {
        wordAnalyzer = new WordAnalyzer();
    }

    @Test
    void createCoreDocument_ShouldThrowException_WhenTextIsEmpty() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            wordAnalyzer.createCoreDocument("");
        });
        assertEquals("text is empty", exception.getMessage());
    }

    @Test
    void createCoreDocument_ShouldThrowException_WhenTextContainsSpace() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            wordAnalyzer.createCoreDocument("book cars");
        });
        assertEquals("text should not contain space", exception.getMessage());
    }

    @Test
    void isSingular_ShouldReturnTrue_WhenTextIsSingularNoun() {
        assertTrue(wordAnalyzer.isSingular("book"));
    }

    @Test
    void isSingular_ShouldReturnFalse_WhenTextIsPluralNoun() {
        assertFalse(wordAnalyzer.isSingular("posts"));
    }

    @Test
    void toLemma_ShouldReturnSingular_WhenTextIsSingularNoun() {
        assertEquals("book", wordAnalyzer.toLemma("book"));
    }

    @Test
    void toLemma_ShouldReturnSingular_WhenTextIsPluralNoun() {
        assertEquals("post", wordAnalyzer.toLemma("posts"));
    }

    @Test
    void toLemmaWithUnderscore_ShouldReturnSingular_WhenTextIsPluralNoun() {
        assertEquals("post", wordAnalyzer.toLemmaWithUnderscore("posts"));
    }

    @Test
    void toLemmaWithUnderscore_ShouldReturnSingular_WhenTextIsPluralNounWithUnderscore() {
        assertEquals("test_user", wordAnalyzer.toLemmaWithUnderscore("test_users"));
    }
}
