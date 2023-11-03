package group.mpntm.client;

import java.io.IOException;
import com.google.gson.Gson;
import group.mpntm.comunication.ClientCommunication;
import group.mpntm.comunication.connector.ButtonClickingConnection;
import group.mpntm.comunication.events.HistoryButtonPressedEvent;
import group.mpntm.comunication.mesasgeContent.HistoryRequestContent;
import group.mpntm.comunication.messageHandlers.ClientMessageHandler;
import group.mpntm.comunication.messageImplementations.Server.HistoryRequestReceiver;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws IOException{

        ClientCommunication clientCommunication;
        try{
            clientCommunication = new ClientCommunication();


        ButtonClickingConnection buttonClickingConnection = new ButtonClickingConnection(clientCommunication);
        ClientMessageHandler clientMessageHandler = new ClientMessageHandler(clientCommunication);
        HistoryButtonPressedEvent.getInstance().AddListener(
            () -> {
                HistoryRequestContent historyRequestContent = new HistoryRequestContent(100);
                var json = new Gson().toJson(historyRequestContent);
                clientCommunication.SendMessage(json, HistoryRequestReceiver.class.getSimpleName());
            }
        );


        ClientScreen clientScreen = new ClientScreen();
        clientScreen.setVisible(true);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Não foi possível conectar ao servidor", "Erro", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }


    }
}
