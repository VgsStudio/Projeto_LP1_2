package group.mpntm.initialize;

import java.io.IOException;

import group.mpntm.server.comunication.domain.ServerCommunication;

public class MainServer {
    public static void main(String[] args) throws IOException {
        ServerCommunication.execute();
    }
}
