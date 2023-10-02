package at.ac.tgm.student.eoezcan2;

import java.util.ArrayList;

public class WordTrainer {

    private ArrayList<WordCard> wordCards;
    private SaveStrategy saveStrategy;
    private String filePath;
    private int currentCard;
    private int correctAnswers;
    private int wrongAnswers;
    private int tries;

    public WordTrainer(String filePath) {
        this.filePath = filePath;
        this.wordCards = new ArrayList<WordCard>();
        this.saveStrategy = new JsonSave();
        this.currentCard = 0;
        this.correctAnswers = 0;
        this.wrongAnswers = 0;
        this.tries = 0;

        load();
    }

    private void load() {
        WordTrainer loaded = this.saveStrategy.loadContent(this.filePath);
        if(loaded != null) {
            this.wordCards = loaded.getWordCards();
            this.currentCard = loaded.getCurrentCard();
            this.correctAnswers = loaded.getCorrectAnswers();
            this.wrongAnswers = loaded.getWrongAnswers();
            this.tries = loaded.getTries();
        }
    }

    private void save() {
        this.saveStrategy.saveContent(this.filePath, this);
    }

    private void randomizeCards() {
        // Shuffle array
        for (int i = 0; i < this.wordCards.size(); i++) {
            int randomIndexToSwap = (int) (Math.random() * this.wordCards.size());
            WordCard temp = this.wordCards.get(randomIndexToSwap);
            this.wordCards.set(randomIndexToSwap, this.wordCards.get(i));
            this.wordCards.set(i, temp);
        }
    }

    public WordCard getCardAt(int index) {
        return this.wordCards.get(index);
    }

    public SaveStrategy getSaveStrategy() {
        return saveStrategy;
    }

    public void setSaveStrategy(SaveStrategy saveStrategy) {
        this.saveStrategy = saveStrategy;
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
