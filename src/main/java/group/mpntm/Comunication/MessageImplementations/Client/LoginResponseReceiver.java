package group.mpntm.Comunication.MessageImplementations.Client;

import com.google.gson.Gson;

import group.mpntm.Comunication.MessageImplementations.IClientMessageImplementation;
import group.mpntm.Comunication.MessageImplementations.Server.EncryptedLoginReceiver;

public class LoginResponseReceiver implements IClientMessageImplementation{

    @Override
    public void Activate(String messageContent) {
        EncryptedLoginReceiver.LoginContentResponse response = new Gson().fromJson(messageContent, EncryptedLoginReceiver.LoginContentResponse.class);
        if(response.value){
            System.out.println("Deu bom");
        }
        else{
            System.out.println("Deu merda!");
        }
    }
    
}
