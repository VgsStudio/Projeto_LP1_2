package group.mpntm.Comunication.MessageImplementations.Server;

import group.mpntm.Comunication.Profiles.ClientProfile;
import group.mpntm.Comunication.MessageImplementations.IServerMessageImplementation;
import com.google.gson.Gson;

/**
 * this implementation is used to send the server's public key to the client.
 * the client will use this key to encrypt the password that it sends to the server.
 */
public class PublicKeySender implements IServerMessageImplementation {

    @Override
    public void Activate(ClientProfile clientProfile, String requestMessageContent) {
        var gson = new Gson();
        System.out.println(requestMessageContent);
        var clientPublicKey = "asdasdasdasdasdas";


//        clientProfile.clientCommunicationServerSide.SendMessage(clientPublicKey, PublicKeyReceiver.class.getName());
    }

    private class ServerPublicKeySenderRequestMessage {
    }

    private class ServerPublicKeySenderResponseMessage {
        public String server_public_key;

        public ServerPublicKeySenderResponseMessage(String clientPublicKey) {
            this.server_public_key = clientPublicKey;
        }
    }
}
