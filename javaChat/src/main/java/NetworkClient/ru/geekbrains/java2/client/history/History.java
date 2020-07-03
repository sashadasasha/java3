package NetworkClient.ru.geekbrains.java2.client.history;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class History {

    private String userName;
    private int userID;

    public History(String userName, int userID) {
        this.userName = userName;
        this.userID = userID;
    }

    public void createHistoryFile(String message) throws IOException {
        if (!Files.exists(Paths.get("F:\\GeekBrains\\java3\\javaChat\\data\\" + userID + ".txt"))) {
            Files.createFile(Paths.get("F:\\GeekBrains\\java3\\javaChat\\data\\" + userID + ".txt"));
        }
        Files.writeString(Paths.get("F:\\GeekBrains\\java3\\javaChat\\data\\" + userID + ".txt"), message + "\n", StandardOpenOption.APPEND);
    }

    public List<String> readHistoryFile() throws IOException {
        if (Files.exists(Paths.get("F:\\GeekBrains\\java3\\javaChat\\data\\" + userID + ".txt"))) {
            return Files.readAllLines(Paths.get("F:\\GeekBrains\\java3\\javaChat\\data\\" + userID + ".txt"));
        }

        return new ArrayList<String>(){{add("");}};
    }

}
