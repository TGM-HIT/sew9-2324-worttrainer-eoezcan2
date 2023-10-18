package at.ac.tgm.student.eoezcan2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class JsonSave implements SaveStrategy {

    @Override
    public void saveContent(String filePath, WordTrainer wordTrainer) throws IOException {
        if(!new File(filePath).exists()) new File(filePath).createNewFile();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("wordCards", wordTrainer.getWordCards());
        jsonObject.put("currentCard", wordTrainer.getCurrentCard());
        jsonObject.put("correctAnswers", wordTrainer.getCorrectAnswers());
        jsonObject.put("wrongAnswers", wordTrainer.getWrongAnswers());
        jsonObject.put("tries", wordTrainer.getTries());

        FileWriter fileWriter = new FileWriter(filePath);
        fileWriter.write(jsonObject.toString());
        fileWriter.close();
    }

    @Override
    public WordTrainer loadContent(String filePath, WordTrainer wordTrainer) {
        if(!new File(filePath).exists()) return null;
        File file = new File(filePath);

        try {
            String content = new String(Files.readAllBytes(Paths.get(file.toURI())));

            JSONObject jsonObject = new JSONObject(content);
            wordTrainer.setWordCards(convertJSONArrayToArrayList(jsonObject.getJSONArray("wordCards")));
            wordTrainer.setCurrentCard(jsonObject.getInt("currentCard"));
            wordTrainer.setCorrectAnswers(jsonObject.getInt("correctAnswers"));
            wordTrainer.setWrongAnswers(jsonObject.getInt("wrongAnswers"));
            wordTrainer.setTries(jsonObject.getInt("tries"));

            return wordTrainer;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static ArrayList<WordCard> convertJSONArrayToArrayList(JSONArray jsonArray) {
        ArrayList<WordCard> wordCards = new ArrayList<WordCard>();
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            try {
                wordCards.add(new WordCard(jsonObject.getString("url"), jsonObject.getString("word")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return wordCards;
    }
}
