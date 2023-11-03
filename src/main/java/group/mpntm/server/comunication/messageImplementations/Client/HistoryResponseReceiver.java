package group.mpntm.server.comunication.messageImplementations.Client;

import com.google.gson.Gson;

import group.mpntm.server.comunication.events.HistoryResponseEvent;
import group.mpntm.server.comunication.mesasgeContent.HistoryResponseContent;
import group.mpntm.server.comunication.messageImplementations.IClientMessageImplementation;

public class HistoryResponseReceiver implements IClientMessageImplementation {
    @Override
    public void Activate(String messageContent) {
        HistoryResponseContent candles = new Gson().fromJson(messageContent, HistoryResponseContent.class);
        HistoryResponseEvent.getInstance().Invoke(candles);
    }
}
