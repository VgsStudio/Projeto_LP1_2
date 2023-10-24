package Comunication.MessageHandlers;

import Comunication.Profiles.ClientProfile;
import Comunication.Message;
import Comunication.MessageImplementations.MessageImplementationFactory;
import Comunication.MessageImplementations.IServerMessageImplementation;

import java.io.IOException;

/**
 * this class is used to handle the messages that are received by the server, it connects to the client communication's message received event and when a message is received it will call the correct message implementation based on the message type.
 */
public class ServerMessageHandler {
    public ServerMessageHandler(ClientProfile clientProfile) {
        clientProfile.clientCommunicationServerSide.messageReceivedEvent.AddListener(message -> {OnMessageReceived(clientProfile,message);});
    }

    private void OnMessageReceived(ClientProfile clientProfile,Message message){

            System.out.println("server message received: " + message.toString());
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
