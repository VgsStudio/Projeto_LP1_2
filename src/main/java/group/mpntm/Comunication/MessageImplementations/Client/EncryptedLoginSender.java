package group.mpntm.Comunication.MessageImplementations.Client;

import group.mpntm.Comunication.MessageImplementations.IClientMessageImplementation;

/**
 * this implementation is used to receive the server's public key.
 * the client will use this key to encrypt the password that it sends to the server.
 */
public class EncryptedLoginSender implements IClientMessageImplementation {
    @Override
    public void Activate(String messageContent) {

    }
}