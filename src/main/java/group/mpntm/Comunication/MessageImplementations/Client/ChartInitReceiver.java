package group.mpntm.Comunication.MessageImplementations.Client;

import com.google.gson.Gson;
import group.mpntm.Comunication.Events.CandleReceivedEvent;
import group.mpntm.Comunication.Events.ChartInitEvent;
import group.mpntm.Comunication.MesasgeContent.ChartInitContent;
import group.mpntm.Comunication.MessageImplementations.IClientMessageImplementation;
import group.mpntm.client.Candle;

public class ChartInitReceiver implements IClientMessageImplementation {

    @Override
    public void Activate(String messageContent) {
        ChartInitContent chartInitContent = new Gson().fromJson(messageContent, ChartInitContent.class);

        ChartInitEvent.getInstance().Invoke(chartInitContent);



    }

}
