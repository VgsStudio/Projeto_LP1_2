package group.mpntm.Comunication.MessageImplementations;

import group.mpntm.Comunication.Profiles.ClientProfile;

/**
 * this interface is used to implement the server message implementations
 * the server message implementations are used to handle the messages that are received by the server
 */
public interface IServerMessageImplementation {
    void Activate(ClientProfile clientProfile, String messageContent);
}
