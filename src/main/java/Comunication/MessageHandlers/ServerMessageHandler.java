package Comunication.MessageHandlers;

import Comunication.Profiles.ClientProfile;
import Comunication.Message;
import Comunication.MessageImplementations.MessageImplementationFactory;
import Comunication.MessageImplementations.IServerMessageImplementation;

/**
 * this class is used to handle the messages that are received by the server, it connects to the client communication's message received event and when a message is received it will call the correct message implementation based on the message type.
 */
public class ServerMessageHandler {
    public ServerMessageHandler(ClientProfile clientProfile) {
        clientProfile.clientCommunication.messageReceivedEvent.AddListener(message -> OnMessageReceived(clientProfile,message));
    }

    private void OnMessageReceived(ClientProfile clientProfile,Message message) {
        try {
            IServerMessageImplementation messageImplementation = MessageImplementationFactory.createMessageImplementationInstance(IServerMessageImplementation.class,message.messageType);
            messageImplementation.Activate(clientProfile,message.messageContent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
