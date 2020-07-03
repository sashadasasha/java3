package NetworkClient.ru.geekbrains.java2.client.controller;

import java.io.IOException;

@FunctionalInterface
public interface AuthEvent {
    void authIsSuccessful(String nickname, Integer userID) throws IOException;
}
