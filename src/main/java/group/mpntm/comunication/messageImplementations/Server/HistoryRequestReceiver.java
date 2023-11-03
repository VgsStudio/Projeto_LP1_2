package group.mpntm.comunication.messageImplementations.Server;
import com.google.gson.Gson;

import group.mpntm.comunication.mesasgeContent.HistoryRequestContent;
import group.mpntm.comunication.mesasgeContent.HistoryResponseContent;
import group.mpntm.comunication.messageImplementations.IServerMessageImplementation;
import group.mpntm.comunication.messageImplementations.Client.HistoryResponseReceiver;
import group.mpntm.comunication.profiles.ClientProfile;
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