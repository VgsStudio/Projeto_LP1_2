package group.mpntm.Comunication.MessageImplementations.Client;

import java.util.List;

import com.google.gson.Gson;

import group.mpntm.Comunication.Events.HistoryResponseEvent;
import group.mpntm.Comunication.MesasgeContent.HistoryResponseContent;
import group.mpntm.Comunication.MessageImplementations.IClientMessageImplementation;
import group.mpntm.client.Candle;

public class HistoryResponseReceiver implements IClientMessageImplementation {
    @Override
    public void Activate(String messageContent) {
        HistoryResponseContent candles = new Gson().fromJson(messageContent, HistoryResponseContent.class);
        System.out.println("Recebendo as candles: ");
        for (Candle candle : candles.candles) {
            System.out.println(candle);
        }
        HistoryResponseEvent.getInstance().Invoke(candles);
    }
}