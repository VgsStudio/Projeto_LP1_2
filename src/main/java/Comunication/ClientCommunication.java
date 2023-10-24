package Comunication;

import Comunication.Events.MessageReceivedEvent;
import Comunication.MessageImplementations.Server.PublicKeySender;
import com.google.gson.Gson;
import group.mpntm.server.Server;

import java.io.*;
import java.net.Socket;

/**
 * The client socket used to communicate with the server.
 * This socket is instantiated once in the client to send and receive messages from the server.
 * and is also used to send messages from the server to the client, it is instantiated once in the server per each client connected to it.
 *
 * it runs in a separate thread to avoid blocking the main thread. and it uses the MessageReceivedEvent to notify the main thread when a message is received.
 */
public class ClientCommunication extends Thread{

    private final Socket clientSocket;
    private final PrintWriter saida;
    private final BufferedReader entrada;
    public final MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent();

    public
    ClientCommunication() throws IOException {
        clientSocket = new Socket(Server.ADDRESS, Server.PORT);
        saida = new PrintWriter(clientSocket.getOutputStream(), true);
        entrada = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));
        System.out.println("Cliente " + Server.ADDRESS + ":" + Server.PORT + " conectado ao servidor!");

        start();
    }
    @Override
    public void run() {
        super.run();
        Gson gson = new Gson();
        while (true) {
            try {
                while (!entrada.ready()) {
                    // Wait until the reader is ready (has content)
                    Thread.sleep(1000); // Sleep for 100 milliseconds (adjust as needed)
                }

                String msg = entrada.readLine();

                if (msg == null) {
                    continue;
                }

                Message message = gson.fromJson(msg, Message.class);

                if (message== null){
                    continue;
                }

                messageReceivedEvent.Invoke(message);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static void main(String[] args) throws IOException, InterruptedException {

        var client = new ClientCommunication();

        client.messageReceivedEvent.AddListener(message -> System.out.println(message));

        Thread.sleep(1000);
        System.out.println(PublicKeySender.class.getSimpleName());
        client.SendMessage("Hello World", PublicKeySender.class.getSimpleName());

    }

    public void SendMessage(String message, String messageType) {
        Gson gson = new Gson();
        Message msg = new Message(message, messageType);
        String json = gson.toJson(msg);
        saida.println(json);
    }
}
