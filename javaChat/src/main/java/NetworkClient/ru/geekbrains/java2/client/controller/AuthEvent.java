package NetworkClient.ru.geekbrains.java2.client.controller;

@FunctionalInterface
public interface AuthEvent {
    void authIsSuccessful(String nickname);
}
