package at.ac.tgm.student.eoezcan2;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
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
    public WordTrainer loadContent(String filePath) {
        if(!new File(filePath).exists()) return null;

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String json = bufferedReader.readLine();
            bufferedReader.close();

            JSONObject jsonObject = new JSONObject(json);
            WordTrainer wordTrainer = new WordTrainer(filePath);
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
            wordCards.add(new WordCard(jsonObject.getString("word"), jsonObject.getString("translation")));
        }
        return wordCards;
    }
}
