package group.mpntm.server.comunication.messageImplementations.Client;

import com.google.gson.Gson;
import group.mpntm.server.comunication.events.CandleReceivedEvent;
import group.mpntm.server.comunication.messageImplementations.IClientMessageImplementation;
import group.mpntm.share.entity.Candle;

public class CandleReceiver implements IClientMessageImplementation {

    @Override
    public void Activate(String messageContent) {
        Candle candle = new Gson().fromJson(messageContent, Candle.class);

//        System.out.println(candle);

        CandleReceivedEvent.getInstance().Invoke(candle);

    }

}
