package group.mpntm.Comunication.Connector;

import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import group.mpntm.Comunication.ClientCommunication;
import group.mpntm.Comunication.Events.LoginButtonPressedEvent;
import group.mpntm.Comunication.Events.LoginButtonPressedListener;
import group.mpntm.share.cripto.Criptography;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import com.google.gson.Gson;

import group.mpntm.Comunication.MesasgeContent.LoginContent;
import group.mpntm.Comunication.MessageImplementations.Server.EncryptedLoginReceiver;

public class ButtonClickingConnection implements LoginButtonPressedListener{
    private ClientCommunication clientCommunication;

    public ButtonClickingConnection(ClientCommunication clientCommunication){
        this.clientCommunication = clientCommunication;
        LoginButtonPressedEvent.getInstance().AddListener(this);
    }

    
    @Override
    public void OnLoginButtonPressed(String username, String password){
    
        String encryptedPassword = null;
        try {
            encryptedPassword = Criptography.encryptRSA(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        LoginContent loginContent = new LoginContent(username, encryptedPassword);

        System.out.println("Sending login content: " + loginContent.toString());

        var json = new Gson().toJson(loginContent);

        this.clientCommunication.SendMessage(json, EncryptedLoginReceiver.class.getSimpleName());

    
    }

}