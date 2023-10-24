package Comunication.Profiles;

import java.io.IOException;
import java.util.HashMap;

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

    public ClientProfile RegisterClientProfile(String client_ip) throws IOException {
        ClientProfile put = clientProfiles.put(client_ip, new ClientProfile(client_ip));
        return put;
    }

    public ClientProfile getClient(String clientIp) {
        return clientProfiles.get(clientIp);
    }
}
