package Comunication.Events;

import Comunication.Message;

public interface MessageListener {
    public void OnMessageReceived(Message message);
}
