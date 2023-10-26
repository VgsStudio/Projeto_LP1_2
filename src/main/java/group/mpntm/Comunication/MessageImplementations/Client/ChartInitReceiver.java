package group.mpntm.Comunication.MessageImplementations.Client;

import com.google.gson.Gson;
import group.mpntm.Comunication.Events.CandleReceivedEvent;
import group.mpntm.Comunication.MessageImplementations.IClientMessageImplementation;
import group.mpntm.client.Candle;

public class ChartInitReceiver implements IClientMessageImplementation {

    @Override
    public void Activate(String messageContent) {
//        Candle candle = new Gson().fromJson(messageContent, Candle.class);

//        CandleReceivedEvent.getInstance().Invoke(candle);

    }

}
