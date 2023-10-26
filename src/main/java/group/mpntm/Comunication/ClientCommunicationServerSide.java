package group.mpntm.Comunication;

import group.mpntm.Comunication.Events.MessageReceivedEvent;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientCommunicationServerSide extends Thread{
    private final Socket clientSocket;
    private final PrintWriter saida;
    private final BufferedReader entrada;
    public final MessageReceivedEvent messageReceivedEvent = new MessageReceivedEvent();

    public void SendMessage(String message, String messageType) {
        Gson gson = new Gson();
        Message msg = new Message(message, messageType);
        String json = gson.toJson(msg);
        saida.println(json);
    }

    public ClientCommunicationServerSide(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        saida = new PrintWriter(clientSocket.getOutputStream(), true);
        entrada = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));

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
}
