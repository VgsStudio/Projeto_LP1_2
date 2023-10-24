package group.mpntm.Comunication;

import group.mpntm.Comunication.MessageHandlers.ServerMessageHandler;
import group.mpntm.Comunication.Profiles.ClientProfileManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *  this class is responsible for the server communication with the clients, it is a thread that is always running and waiting for new clients to connect
 *  when a new client connects, it creates a new ClientProfile and a new ServerMessageHandler for that client, and adds the client to the ClientProfileManager
 *  the ServerMessageHandler is responsible for handling the messages received from the client, and the ClientProfileManager is responsible for managing the client profiles (adding, removing, getting)
 *
 */
public class ServerCommunication extends Thread {

    public static final String ADDRESS = "127.0.0.1"; // IP Address local do servidor
    public static final int PORT = 4000; // ou 3334
    private ServerSocket serverSocket;


    String json = "{\"messageContent\":\"valor teste\"}";

    public ServerCommunication() throws IOException {
        serverSocket = new ServerSocket(PORT);
        start();
    }
    @Override
    public void run() {
        super.run();

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                var clientProfile = ClientProfileManager.getInstance().RegisterClientProfile(clientSocket);
                ServerMessageHandler serverMessageHandler = new ServerMessageHandler(clientProfile);

                System.out.println("Cliente " + clientSocket.getInetAddress() + ":" + clientSocket.getPort() + " conectado ao servidor!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws IOException {
        ServerCommunication serverCommunication = new ServerCommunication();
    }
}
