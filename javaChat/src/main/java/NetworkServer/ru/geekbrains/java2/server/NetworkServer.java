package NetworkServer.ru.geekbrains.java2.server;

import NetworkClientServer.ru.geekbrains.java2.client.Command;
import NetworkServer.ru.geekbrains.java2.server.auth.AuthService;
import NetworkServer.ru.geekbrains.java2.server.auth.BaseAuthService;
import NetworkServer.ru.geekbrains.java2.server.client.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class NetworkServer {

    private final int port;
    private final List<ClientHandler> clients = new CopyOnWriteArrayList<>();
    private final AuthService authService;

    public NetworkServer(int port) {
        this.port = port;
        this.authService = new BaseAuthService();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер был успешно запущен на порту " + port);
            authService.start();
            while (true) {
                System.out.println("Ожидание клиентского подключения...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Клиент подключился");
                ClientHandler ch = createClientHandler(clientSocket);
            }

        } catch (IOException | SQLException e) {
            System.out.println("Ошибка при работе сервера");
            e.printStackTrace();
        } finally {
            authService.stop();
        }

    }

    private ClientHandler createClientHandler(Socket clientSocket) {
        ClientHandler clientHandler = new ClientHandler(this, clientSocket);
        clientHandler.run();
        return clientHandler;
    }

    public void broadcastMessage(Command message, ClientHandler owner) throws IOException {
        for (ClientHandler client : clients) {
            if (client != owner) {
                client.sendMessage(message);
            }
        }
    }

    public void subscribe(ClientHandler clientHandler) throws IOException {
        clients.add(clientHandler);
        List<String> users = getAllUsernames();
        broadcastMessage(Command.updateUsersListCommand(users), null);
    }

    public void unsubscribe(ClientHandler clientHandler) throws IOException {
        clients.remove(clientHandler);
        List<String> users = getAllUsernames();
        broadcastMessage(Command.updateUsersListCommand(users), null);
    }

    private List<String> getAllUsernames() {
        List<String> usernames = new LinkedList<>();
        for (ClientHandler clientHandler : clients) {
            usernames.add(clientHandler.getNickname());
        }
        return usernames;
    }

    public void sendMessage(String receiver, Command commandMessage) throws IOException {
        for (ClientHandler client : clients) {
            if (client.getNickname().equals(receiver)) {
                client.sendMessage(commandMessage);
                break;
            }
        }
    }

    public boolean isNicknameBusy(String username) {
        for (ClientHandler client : clients) {
            if (client.getNickname().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public AuthService getAuthService() {
        return authService;
    }
}
