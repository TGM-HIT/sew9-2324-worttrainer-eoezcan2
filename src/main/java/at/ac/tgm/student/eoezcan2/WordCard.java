package at.ac.tgm.student.eoezcan2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class WordCard {

    private String url;
    private String word;

    public WordCard(String url, String word) throws IOException {
        // Check if URL is an image
        setUrl(url);
        setWord(word);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) throws IllegalArgumentException, IOException {

        Image image = ImageIO.read(new URL(url));
        if(image != null){
            this.url = url;
        }else{
            throw new IllegalArgumentException("URL is not an image!");
        }
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String toString() {
        return this.word + ":" + this.url;
    }

}
