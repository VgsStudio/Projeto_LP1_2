package group.mpntm.Comunication.MessageImplementations.Client;

import com.google.gson.Gson;

import group.mpntm.Comunication.Events.LoginFailedEvent;
import group.mpntm.Comunication.Events.LoginSuccessfulEvent;
import group.mpntm.Comunication.MesasgeContent.LoginContentResponse;
import group.mpntm.Comunication.MessageImplementations.IClientMessageImplementation;

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
