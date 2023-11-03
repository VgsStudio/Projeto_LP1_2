package group.mpntm.server.comunication.messageImplementations.Server;
import com.google.gson.Gson;

import group.mpntm.server.comunication.mesasgeContent.HistoryRequestContent;
import group.mpntm.server.comunication.mesasgeContent.HistoryResponseContent;
import group.mpntm.server.comunication.messageImplementations.IServerMessageImplementation;
import group.mpntm.server.comunication.messageImplementations.Client.HistoryResponseReceiver;
import group.mpntm.server.comunication.profiles.ClientProfile;
import group.mpntm.server.database.repo.RepositoryMySQL;

public class HistoryRequestReceiver implements IServerMessageImplementation {
    @Override
    public void Activate(ClientProfile clientProfile, String messageContent) {
        HistoryRequestContent historyRequestContent = new Gson().fromJson(messageContent, HistoryRequestContent.class);
        HistoryResponseContent candles = new HistoryResponseContent(RepositoryMySQL.tailCandles(historyRequestContent.number));
        var json = new Gson().toJson(candles);
        clientProfile.clientCommunicationServerSide.SendMessage(json, HistoryResponseReceiver.class.getSimpleName());
    }
}