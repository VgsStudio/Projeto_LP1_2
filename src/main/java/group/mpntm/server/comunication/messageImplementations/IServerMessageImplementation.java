package group.mpntm.server.comunication.messageImplementations;

import group.mpntm.server.comunication.profiles.ClientProfile;

/**
 * this interface is used to implement the server message implementations
 * the server message implementations are used to handle the messages that are received by the server
 */
public interface IServerMessageImplementation {
    void Activate(ClientProfile clientProfile, String messageContent);
}
