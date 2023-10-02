package at.ac.tgm.student.eoezcan2;

import java.util.ArrayList;

public class WordTrainer {

    private ArrayList<WordCard> wordCards;
    private String filePath;
    private int currentCard;
    private int correctAnswers;
    private int wrongAnswers;
    private int tries;

    public WordTrainer(String filePath) {
        this.filePath = filePath;
        this.wordCards = new ArrayList<>();
        this.currentCard = 0;
        this.correctAnswers = 0;
        this.wrongAnswers = 0;
        this.tries = 0;
    }

    public ArrayList<WordCard> getWordCards() {
        return wordCards;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getCurrentCard() {
        return currentCard;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public int getTries() {
        return tries;
    }
}
