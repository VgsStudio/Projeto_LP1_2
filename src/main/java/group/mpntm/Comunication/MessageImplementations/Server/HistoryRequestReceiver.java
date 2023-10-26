package group.mpntm.Comunication.MessageImplementations.Server;
import com.google.gson.Gson;

import group.mpntm.Comunication.MesasgeContent.HistoryRequestContent;
import group.mpntm.Comunication.MesasgeContent.HistoryResponseContent;
import group.mpntm.Comunication.MessageImplementations.IServerMessageImplementation;
import group.mpntm.Comunication.MessageImplementations.Client.HistoryResponseReceiver;
import group.mpntm.Comunication.Profiles.ClientProfile;
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