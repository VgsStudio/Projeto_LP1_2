package group.mpntm.server.comunication.events;

import java.util.HashSet;

import group.mpntm.server.comunication.domain.Message;

/**
 * this is the class that uses the observer pattern to notify the message listeners
 * when a message is received by the client communication on the server or the client communication on the client.
 */
public class MessageReceivedEvent {
    private HashSet<MessageListener> messageListeners = new HashSet<MessageListener>();

    public void Invoke(Message message) {
        for (var messageListener : messageListeners) {
            messageListener.OnMessageReceived(message);
        }
    }

    public void AddListener(MessageListener messageListener) {
        messageListeners.add(messageListener);
    }

    public void RemoveListener(MessageListener messageListener) {
        messageListeners.remove(messageListener);
    }
}
