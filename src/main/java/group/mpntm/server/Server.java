package group.mpntm.server;

import group.mpntm.client.ClientSocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Objects;

public class Server {
    public static final String ADDRESS = "127.0.0.1"; // IP Address local do servidor
    public static final int PORT = 4000; // ou 3334
    private ServerSocket serverSocket;

    public void start() throws IOException{
        serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor iniciado na porta " + PORT);
        clientConnectionLoop();

        do {
            ClientSocket clientSocket = new ClientSocket(serverSocket.accept());
            new Thread(() -> clientMessageLoop(clientSocket)).start();
        } while (true);

    }

    private void clientConnectionLoop() throws IOException{
        System.out.println("Aguardando conexão de um cliente!");
        do{
            ClientSocket clientSocket = new ClientSocket(serverSocket.accept());
            new Thread(() -> clientMessageLoop(clientSocket)).start();
        } while(true);
    }

    public void clientMessageLoop(ClientSocket clientSocket){
        String msg;
        boolean isLogged = false;
        try{
            while((msg = clientSocket.getMessage()) != null){

                if (!isLogged){

                    String[] msgSplit = msg.split(" ");

                    if (msgSplit.length != 2){
                        clientSocket.sendMsg("Erro: Formato de mensagem inválido!");
                        continue;
                    }

                    String username = msgSplit[0];
                    String password = msgSplit[1];

                    if (loginUser(username, password)){
                        clientSocket.sendMsg("1");
                        isLogged = true;
                    } else {
                        clientSocket.sendMsg("0");
                    }

                }
                else {
                    clientSocket.sendMsg("Mensagem recebida: " + msg);
                }


            }
        }
        finally {
            clientSocket.close();
        }
    }

    public boolean loginUser(String username, String password){

        // TODO: implementar a lógica de login
        System.out.println("Login: " + username + " " + password);
        return true;

        // if (Objects.equals(username, "admin") && Objects.equals(password, "admin")) {
        //     return true;
        // }
        // return false;
    }


    public static void main(String args[]){
        System.out.println("====== CONSOLE DO SERVIDOR ======");
        try {
            Server server = new Server();
            server.start();
        } catch (IOException e) {
            System.out.println("Erro no Servidor: " + e.getMessage());
        }
        System.out.println("Servidor encerrado");
    }
}
