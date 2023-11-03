package group.mpntm.comunication.messageImplementations.Client;

import com.google.gson.Gson;

import group.mpntm.comunication.events.LoginFailedEvent;
import group.mpntm.comunication.events.LoginSuccessfulEvent;
import group.mpntm.comunication.mesasgeContent.LoginContentResponse;
import group.mpntm.comunication.messageImplementations.IClientMessageImplementation;

public class LoginResponseReceiver implements IClientMessageImplementation{

    @Override
    public void Activate(String messageContent) {
        LoginContentResponse response = new Gson().fromJson(messageContent, LoginContentResponse.class);
        if(response.value){
            LoginSuccessfulEvent.getInstance().Invoke();
        }
        else{
            LoginFailedEvent.getInstance().Invoke();
        }
    }
    
}
