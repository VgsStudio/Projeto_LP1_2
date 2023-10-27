package group.mpntm.Comunication.Events;

import group.mpntm.Comunication.Message;

public interface MessageListener {
    public void OnMessageReceived(Message message);
}
