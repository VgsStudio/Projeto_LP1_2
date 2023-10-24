package Comunication.Profiles;

import Comunication.ClientCommunication;

import java.io.IOException;

/**
 *  the client profile class is used to store the client's ip,
 *  public key, private key and client communication
 *  and any other information that is needed to be stored about the client on the server.
 *
 *
 */
public class ClientProfile {
    public String client_ip;
    public String client_public_key;
    public String client_private_key;
    public ClientCommunication clientCommunication;

    public ClientProfile(String client_ip) throws IOException {
        this.client_ip = client_ip;
//        this.client_public_key = client_public_key;
//        this.client_private_key = client_private_key;
        this.clientCommunication = new ClientCommunication();
    }

    @Override
    public String toString() {
        return "ClientProfile{" +
                "client_ip='" + client_ip + '\'' +
                ", client_public_key='" + client_public_key + '\'' +
                ", client_private_key='" + client_private_key + '\'' +
                '}';
    }

    public String getPublicKey() {
        return client_public_key;
    }
}
