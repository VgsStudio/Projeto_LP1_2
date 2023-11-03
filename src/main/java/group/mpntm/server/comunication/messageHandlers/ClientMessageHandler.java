package group.mpntm.server.comunication.messageHandlers;

import group.mpntm.server.comunication.ClientCommunication;
import group.mpntm.server.comunication.Message;
import group.mpntm.server.comunication.messageImplementations.MessageImplementationFactory;
import group.mpntm.server.comunication.messageImplementations.IClientMessageImplementation;

/**
 * this class is used to handle the messages that are received by the client,
 * it connects to the client communication's message received event and when a message is received it will call the correct message implementation based on the message type.
 *
 */
public class ClientMessageHandler {
    public ClientMessageHandler(ClientCommunication clientCommunication){
        clientCommunication.messageReceivedEvent.AddListener(this::OnMessageReceived);
    }

    private void OnMessageReceived(Message message) {
        try {
            IClientMessageImplementation messageImplementation = MessageImplementationFactory.createMessageImplementationInstance(IClientMessageImplementation.class,message.messageType);
            messageImplementation.Activate(message.messageContent);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
