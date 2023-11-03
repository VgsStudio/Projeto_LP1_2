package group.mpntm.comunication.messageImplementations.Client;

import com.google.gson.Gson;

import group.mpntm.comunication.events.HistoryResponseEvent;
import group.mpntm.comunication.mesasgeContent.HistoryResponseContent;
import group.mpntm.comunication.messageImplementations.IClientMessageImplementation;

public class HistoryResponseReceiver implements IClientMessageImplementation {
    @Override
    public void Activate(String messageContent) {
        HistoryResponseContent candles = new Gson().fromJson(messageContent, HistoryResponseContent.class);
        HistoryResponseEvent.getInstance().Invoke(candles);
    }
}
