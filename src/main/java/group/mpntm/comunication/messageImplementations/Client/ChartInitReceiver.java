package group.mpntm.comunication.messageImplementations.Client;

import com.google.gson.Gson;
import group.mpntm.comunication.events.ChartInitEvent;
import group.mpntm.comunication.mesasgeContent.ChartInitContent;
import group.mpntm.comunication.messageImplementations.IClientMessageImplementation;

public class ChartInitReceiver implements IClientMessageImplementation {

    @Override
    public void Activate(String messageContent) {
        ChartInitContent chartInitContent = new Gson().fromJson(messageContent, ChartInitContent.class);

        ChartInitEvent.getInstance().Invoke(chartInitContent);



    }

}
