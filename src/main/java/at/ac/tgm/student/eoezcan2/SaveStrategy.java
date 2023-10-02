package at.ac.tgm.student.eoezcan2;

import java.io.IOException;

public interface SaveStrategy {

    void saveContent(String filePath, WordTrainer wordTrainer);
    WordTrainer loadContent(String filePath);

}
