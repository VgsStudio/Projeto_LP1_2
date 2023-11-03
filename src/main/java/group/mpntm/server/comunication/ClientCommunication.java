package group.mpntm.server.comunication;

import group.mpntm.server.comunication.events.LoginButtonPressedEvent;
import group.mpntm.server.comunication.events.MessageReceivedEvent;
import group.mpntm.server.comunication.mesasgeContent.LoginContent;
import group.mpntm.server.comunication.messageHandlers.ClientMessageHandler;
import group.mpntm.server.comunication.messageImplementations.Client.EncryptedLoginSender;
import group.mpntm.server.comunication.messageImplementations.IClientMessageImplementation;
import group.mpntm.server.comunication.messageImplementations.MessageImplementationFactory;
import group.mpntm.server.comunication.messageImplementations.Server.EncryptedLoginReceiver;
import com.google.gson.Gson;
import group.mpntm.share.cripto.Criptography;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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
        clientSocket = new Socket(ServerCommunication.ADDRESS, ServerCommunication.PORT);
        saida = new PrintWriter(clientSocket.getOutputStream(), true);
        entrada = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));
        System.out.println("Cliente " + ServerCommunication.ADDRESS + ":" + ServerCommunication.PORT + " conectado ao servidor!");

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

    
    public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException, InstantiationException, IllegalAccessException {

        ClientCommunication client = new ClientCommunication();
        ClientMessageHandler clientMessageHandler = new ClientMessageHandler(client);

        var imp = MessageImplementationFactory.createMessageImplementationInstance(IClientMessageImplementation.class,EncryptedLoginSender.class.getSimpleName());
        LoginButtonPressedEvent.getInstance().AddListener((username, password) -> {
            String encryptedPassword = null;
            try {
                encryptedPassword = Criptography.encryptRSA(password);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            } catch (NoSuchPaddingException e) {
                throw new RuntimeException(e);
            } catch (InvalidKeyException e) {
                throw new RuntimeException(e);
            } catch (IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            } catch (BadPaddingException e) {
                throw new RuntimeException(e);
            } catch (InvalidAlgorithmParameterException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            LoginContent loginContent = new LoginContent(username, encryptedPassword);

            var json = new Gson().toJson(loginContent);

            client.SendMessage(json, EncryptedLoginReceiver.class.getSimpleName());

        });
        Thread.sleep(1000);

        LoginButtonPressedEvent.getInstance().Invoke("admin", "admin");
    }

    public void SendMessage(String message, String messageType) {
        Gson gson = new Gson();
        Message msg = new Message(message, messageType);
        String json = gson.toJson(msg);
        saida.println(json);
    }
}
