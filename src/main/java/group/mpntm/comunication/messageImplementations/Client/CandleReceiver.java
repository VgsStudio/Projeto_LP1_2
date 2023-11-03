package group.mpntm.comunication.messageImplementations.Client;

import com.google.gson.Gson;
import group.mpntm.comunication.events.CandleReceivedEvent;
import group.mpntm.comunication.messageImplementations.IClientMessageImplementation;
import group.mpntm.client.Candle;

public class CandleReceiver implements IClientMessageImplementation {

    @Override
    public void Activate(String messageContent) {
        Candle candle = new Gson().fromJson(messageContent, Candle.class);

//        System.out.println(candle);

        CandleReceivedEvent.getInstance().Invoke(candle);

    }

}
