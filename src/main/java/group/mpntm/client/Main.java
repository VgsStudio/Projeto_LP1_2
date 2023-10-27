package group.mpntm.client;

import java.io.IOException;
import com.google.gson.Gson;
import group.mpntm.Comunication.ClientCommunication;
import group.mpntm.Comunication.Connector.ButtonClickingConnection;
import group.mpntm.Comunication.Events.HistoryButtonPressedEvent;
import group.mpntm.Comunication.MesasgeContent.HistoryRequestContent;
import group.mpntm.Comunication.MessageHandlers.ClientMessageHandler;
import group.mpntm.Comunication.MessageImplementations.Server.HistoryRequestReceiver;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws IOException{

        ClientCommunication clientCommunication;
        try{
            clientCommunication = new ClientCommunication();


        ButtonClickingConnection buttonClickingConnection = new ButtonClickingConnection(clientCommunication);
        ClientMessageHandler clientMessageHandler = new ClientMessageHandler(clientCommunication);
//        HistoryButtonPressedEvent.getInstance().AddListener(
//            () -> {
//                HistoryRequestContent historyRequestContent = new HistoryRequestContent(100);
//                var json = new Gson().toJson(historyRequestContent);
//                clientCommunication.SendMessage(json, HistoryRequestReceiver.class.getSimpleName());
//            }
//        );


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
