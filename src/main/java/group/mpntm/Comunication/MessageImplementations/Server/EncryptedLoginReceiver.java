package group.mpntm.Comunication.MessageImplementations.Server;

import com.google.gson.Gson;
import group.mpntm.Comunication.ClientCommunication;
import group.mpntm.Comunication.MessageImplementations.IClientMessageImplementation;
import group.mpntm.Comunication.MessageImplementations.IServerMessageImplementation;
import group.mpntm.Comunication.Profiles.ClientProfile;
import group.mpntm.share.cripto.Criptography;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * this implementation is used to receive the server's public key.
 * the client will use this key to encrypt the password that it sends to the server.
 */
public class EncryptedLoginReceiver implements IServerMessageImplementation {
    @Override
    public void Activate(ClientProfile clientProfile, String messageContent) {
        ClientCommunication.LoginContent loginContent = new Gson().fromJson(messageContent, ClientCommunication.LoginContent.class);

        System.out.println("encrypted password " + loginContent.encryptedPassword);
        try {
            System.out.println("decryoted password "+ Criptography.decryptRSA(loginContent.encryptedPassword));
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
//        clientProfile.isLogged = true;
    }
}
