package at.ac.tgm.student.eoezcan2;

import java.io.IOException;

public interface SaveStrategy {

    void saveContent(String filePath, WordTrainer wordTrainer) throws IOException;
    WordTrainer loadContent(String filePath);

}
