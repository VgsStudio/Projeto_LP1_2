package group.mpntm.server.comunication.events;

import group.mpntm.server.comunication.Message;

public interface MessageListener {
    public void OnMessageReceived(Message message);
}
