package group.mpntm.comunication.events;

import group.mpntm.comunication.Message;

public interface MessageListener {
    public void OnMessageReceived(Message message);
}
