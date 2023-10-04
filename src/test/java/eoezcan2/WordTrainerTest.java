package eoezcan2;

import at.ac.tgm.student.eoezcan2.WordCard;
import at.ac.tgm.student.eoezcan2.WordTrainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WordTrainerTest {

    private WordTrainer wordTrainer;

    @BeforeEach
    public void setup() {
        this.wordTrainer = new WordTrainer("src/test/resources/words.json");
        this.wordTrainer.clearCards();
        this.wordTrainer.resetStats();
    }

    @Test
    public void testWordCountZero() {
        assertEquals(0, this.wordTrainer.getWordCards().size());
    }

    @Test
    public void testWordCountOne() {
        this.wordTrainer.addCard(new WordCard("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Google_2015_logo.svg/368px-Google_2015_logo.svg.png", "Google"));
        assertEquals(1, this.wordTrainer.getWordCards().size());
    }

    @Test
    public void testSerialization() {
        this.wordTrainer.addCard(new WordCard("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Google_2015_logo.svg/368px-Google_2015_logo.svg.png", "Google"));
        this.wordTrainer.save();
        WordTrainer wordTrainer = new WordTrainer("src/test/resources/words.json");
        assertEquals("Google", wordTrainer.getCardAt(0).getWord());
    }

    @Test
    public void testImageValidationError() {
        assertThrows(IllegalArgumentException.class, () -> {
            new WordCard("https://upload.wikimedia.org/wikipedia/commons/thumb/", "Google");
        });
    }

    @Test
    public void testImageValidation() {
        assertDoesNotThrow(() -> {
            new WordCard("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Google_2015_logo.svg/368px-Google_2015_logo.svg.png", "Google");
        });
    }

}
