package Comunication;

/**
 * this class is used to send messages between the server and the client
 * the message type is used to determine what the message is about and what to do with it when it is received by the other side (client/server)
 * the message content is used to send the actual message, it can be a string, a json, a file, etc.
 *
 */
public class Message{
    public String messageType;
    public String messageContent;

    public Message(String message, String messageType) {
        this.messageContent = message;
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageType='" + messageType + '\'' +
                ", messageContent='" + messageContent + '\'' +
                '}';
    }
}
