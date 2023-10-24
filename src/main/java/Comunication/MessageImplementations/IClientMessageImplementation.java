package Comunication.MessageImplementations;

/**
 * this interface is used to implement the client message implementations
 * the client message implementations are used to handle the messages that are received by the client
 */
public interface IClientMessageImplementation {
    void Activate(String messageContent);
}

