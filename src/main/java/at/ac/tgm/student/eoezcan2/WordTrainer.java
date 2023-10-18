package at.ac.tgm.student.eoezcan2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class WordTrainer {

    public static void main(String[] args) {
        WordTrainer wt = new WordTrainer("src/main/resources/words.json");
        wt.play();
    }

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

        try {
            this.wordCards.add(new WordCard("https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Google_2015_logo.svg/2000px-Google_2015_logo.svg.png", "Google"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            this.wordCards.add(new WordCard("https://upload.wikimedia.org/wikipedia/commons/thumb/5/51/Facebook_f_logo_%282019%29.svg/2000px-Facebook_f_logo_%282019%29.svg.png", "Facebook"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        load();
    }

    public void resetStats() {
        this.currentCard = 0;
        this.correctAnswers = 0;
        this.wrongAnswers = 0;
        this.tries = 0;
        randomizeCards();
    }

    public void play() {
        if(currentCard >= this.wordCards.size()) this.resetStats();
        if(currentCard == 0) randomizeCards(); // Randomize cards if first time playing
        while(currentCard < this.wordCards.size()) {
            Image img = getImage();

            String res = (String) JOptionPane.showInputDialog(null, "Wie heißt das Wort auf dem Bild?",
                    "Word Trainer", JOptionPane.QUESTION_MESSAGE, new ImageIcon(img), null, null);
            if(res.isEmpty()) {
                save();
                return;
            }
            boolean guessed = res.equalsIgnoreCase(getCardAt(currentCard).getWord());
            if(guessed) {
                this.correctAnswers++;
                this.currentCard++;
            } else {
                this.wrongAnswers++;
            }
            this.tries++;
            JOptionPane.showMessageDialog(null, (guessed ? "Richtig!" : "Falsch!") + "\nVersuche: " + tries + "\nRichtig: " + correctAnswers + "\nFalsch: " + wrongAnswers);
            if(guessed) this.tries = 0;
            this.save();
        }
        resetStats();
        this.save();
    }

    private Image getImage() {
        Image img = null;
        System.out.println(getCardAt(this.currentCard).getUrl());
        try {
            URL url = new URL(getCardAt(this.currentCard).getUrl());
            img = ImageIO.read(url);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return img;
    }

    public void load() {
        WordTrainer loaded = this.saveStrategy.loadContent(this.filePath, this);
        if(loaded != null) {
            this.wordCards = loaded.getWordCards();
            this.currentCard = loaded.getCurrentCard();
            this.correctAnswers = loaded.getCorrectAnswers();
            this.wrongAnswers = loaded.getWrongAnswers();
            this.tries = loaded.getTries();
        }
    }

    public void save() {
        try {
            this.saveStrategy.saveContent(this.filePath, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public void setWordCards(ArrayList<WordCard> wordCards) {
        this.wordCards = wordCards;
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

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public void setCurrentCard(int currentCard) {
        this.currentCard = currentCard;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public void setTries(int tries) {
        this.tries = tries;
    }

    public void clearCards() {
        this.wordCards.clear();
    }

    public void addCard(WordCard card) {
        this.wordCards.add(card);
    }
}
