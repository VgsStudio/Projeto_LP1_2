package group.mpntm.Comunication.MessageHandlers;

import group.mpntm.Comunication.Profiles.ClientProfile;
import group.mpntm.Comunication.Message;
import group.mpntm.Comunication.MessageImplementations.MessageImplementationFactory;
import group.mpntm.Comunication.MessageImplementations.IServerMessageImplementation;

import java.io.IOException;

/**
 * this class is used to handle the messages that are received by the server, it connects to the client communication's message received event and when a message is received it will call the correct message implementation based on the message type.
 */
public class ServerMessageHandler {
    public ServerMessageHandler(ClientProfile clientProfile) {
        clientProfile.clientCommunicationServerSide.messageReceivedEvent.AddListener(message -> {OnMessageReceived(clientProfile,message);});
    }

    private void OnMessageReceived(ClientProfile clientProfile,Message message){

        IServerMessageImplementation messageImplementation = null;
        try {
            messageImplementation = MessageImplementationFactory.createMessageImplementationInstance(IServerMessageImplementation.class,message.messageType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        messageImplementation.Activate(clientProfile,message.messageContent);

    }
}
