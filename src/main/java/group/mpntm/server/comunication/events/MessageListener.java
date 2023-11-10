package group.mpntm.server.comunication.events;

import group.mpntm.server.comunication.domain.Message;

public interface MessageListener {
    public void OnMessageReceived(Message message);
}
