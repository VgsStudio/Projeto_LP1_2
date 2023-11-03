package group.mpntm.server.comunication.profiles;

import group.mpntm.server.comunication.ClientCommunicationServerSide;

import java.io.IOException;
import java.net.Socket;

/**
 *  the client profile class is used to store the client's ip,
 *  public key, private key and client communication
 *  and any other information that is needed to be stored about the client on the server.
 *
 *
 */
public class ClientProfile {
    public String clientIp;
    public Boolean isLogged = false;
    public ClientCommunicationServerSide clientCommunicationServerSide;

    public ClientProfile(Socket socket) throws IOException {
        this.clientIp = socket.getRemoteSocketAddress().toString();

        this.clientCommunicationServerSide = new ClientCommunicationServerSide(socket);
    }

    @Override
    public String toString() {
        return "ClientProfile{" +
                "client_ip='" + clientIp + '\'' +
                '}';
    }
}
