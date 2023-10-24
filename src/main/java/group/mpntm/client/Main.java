package group.mpntm.client;

import java.io.IOException;
import java.util.ResourceBundle;

import group.mpntm.Comunication.ClientCommunication;
import group.mpntm.Comunication.Connector.ButtonClickingConnection;
import group.mpntm.Comunication.MessageHandlers.ClientMessageHandler;

public class Main {

    public static void main(String[] args) {
        
        ClientCommunication clientCommunication = null;
        try {
            clientCommunication = new ClientCommunication();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        ButtonClickingConnection buttonClickingConnection = new ButtonClickingConnection(clientCommunication);
        ClientMessageHandler clientMessageHandler = new ClientMessageHandler(clientCommunication);

        ClientScreen clientScreen = new ClientScreen();
        clientScreen.setVisible(true);


    }
}
