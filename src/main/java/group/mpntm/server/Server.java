package group.mpntm.server;

import group.mpntm.client.ClientSocket;

import java.io.IOException;
import java.net.ServerSocket;

// Com esse novo servidor, cada cliente consegue se conectar  ao servidor, uma nova thread é criada e o servidor recebe informação de ambos os clientes
// Existe um número máximo por conta da porta, 16^4 = 65536 clientes!


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
        try{
            while((msg = clientSocket.getMessage()) != null && !msg.equals("sair")){
                System.out.printf("Mensagem recebida do cliente %s: %s\n", clientSocket.getRemoteSocketAddress(), msg);
            }
        }
        finally {
            clientSocket.close();
        }
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
