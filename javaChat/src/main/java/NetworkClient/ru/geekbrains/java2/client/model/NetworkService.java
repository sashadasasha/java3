package NetworkClient.ru.geekbrains.java2.client.model;

import NetworkClient.ru.geekbrains.java2.client.history.History;
import NetworkClientServer.ru.geekbrains.java2.client.Command;
import NetworkClientServer.ru.geekbrains.java2.client.command.AuthCommand;
import NetworkClientServer.ru.geekbrains.java2.client.command.ErrorCommand;
import NetworkClientServer.ru.geekbrains.java2.client.command.MessageCommand;
import NetworkClientServer.ru.geekbrains.java2.client.command.UpdateUsersListCommand;
import NetworkClient.ru.geekbrains.java2.client.controller.AuthEvent;
import NetworkClient.ru.geekbrains.java2.client.controller.ClientController;
import NetworkClient.ru.geekbrains.java2.client.controller.MessageHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

public class NetworkService {

    private final String host;
    private final int port;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    private ClientController controller;

    private MessageHandler messageHandler;
    private static AuthEvent successfulAuthEvent;
    private String nickname;
    int userID;

    public NetworkService(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect(ClientController controller) throws IOException {
        this.controller = controller;
        socket = new Socket(host, port);
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        runReadThread();
    }

    private void runReadThread() {
        new Thread(() -> {
            while (true) {
                try {
                    Command command = (Command) in.readObject();
                    switch (command.getType()) {
                        case AUTH: {
                            AuthCommand commandData = (AuthCommand) command.getData();
                            nickname = commandData.getUsername();
                            userID = commandData.getId();
                            successfulAuthEvent.authIsSuccessful(nickname);
                            break;
                        }
                        case MESSAGE: {
                            MessageCommand commandData = (MessageCommand) command.getData();
                            if (messageHandler != null) {
                                String message = commandData.getMessage();
                                String username = commandData.getUsername();
                                if (username != null) {
                                    message = username + ": " + message;
                                }
                                History history = new History(nickname, userID);
                                history.createHistoryFile(message);
                                messageHandler.handle(message);
                            }
                            break;
                        }
                        case AUTH_ERROR:
                        case ERROR: {
                            ErrorCommand commandData = (ErrorCommand) command.getData();
                            controller.showErrorMessage(commandData.getErrorMessage());
                            break;
                        }
                        case UPDATE_USERS_LIST: {
                            UpdateUsersListCommand commandData = (UpdateUsersListCommand) command.getData();
                            List<String> users = commandData.getUsers();
                            controller.updateUsersList(users);
                            break;
                        }
                        default:
                            System.err.println("Unknown type of command: " + command.getType());
                    }
                } catch (IOException e) {
                    System.out.println("Поток чтения был прерван!");
                    controller.getAuthDialog().showError("Вы не авторизовались!");
                    controller.getAuthDialog().onCancel();
                    return;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendCommand(Command command) throws IOException {
        out.writeObject(command);
    }

    public void setMessageHandler(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    public void setSuccessfulAuthEvent(AuthEvent successfulAuthEvent) {
        this.successfulAuthEvent = successfulAuthEvent;
    }

    public static AuthEvent getSuccessfulAuthEvent() {
        return successfulAuthEvent;
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
