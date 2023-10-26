package group.mpntm.Comunication.Profiles;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

/**
 * The Client Profile Manager is used to store all the client profiles on the server side.
 * It is used to register new clients and to get the client profile of a client.
 * It is a singleton class, so it can only be instantiated once
 *
 */
public class ClientProfileManager {

    private static ClientProfileManager instance;
    HashMap<String, ClientProfile> clientProfiles = new HashMap<String,ClientProfile>();

    public static ClientProfileManager getInstance() {

        if (instance == null) {
            instance = new ClientProfileManager();
        }

        return instance;
    }

    public ClientProfile RegisterClientProfile(Socket socket) throws IOException {
        ClientProfile clientProfile = new ClientProfile(socket);
        clientProfiles.put(String.valueOf(socket.getInetAddress()),clientProfile );
        return clientProfile;
    }

    public ClientProfile getClient(String clientIp) {
        return clientProfiles.get(clientIp);
    }

    public List<ClientProfile> getAllClients() {

        return clientProfiles.values().stream().toList();
    }
}
