package group.mpntm.server.comunication.messageImplementations.Client;

import group.mpntm.server.comunication.messageImplementations.IClientMessageImplementation;

/**
 * this implementation is used to receive the server's public key.
 * the client will use this key to encrypt the password that it sends to the server.
 */
public class PublicKeyReceiver implements IClientMessageImplementation {
    @Override
    public void Activate(String messageContent) {

    }
}
