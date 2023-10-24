package group.mpntm.server;

import group.mpntm.client.ClientSocket;
import group.mpntm.server.database.repo.RepositoryMySQL;
import group.mpntm.share.cripto.Criptography;
import group.mpntm.share.functions.Utils;

import java.io.IOException;
import java.net.ServerSocket;

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
            String fullMsg = "";
            while((msg = clientSocket.getMessage()) != null){
                fullMsg = fullMsg + msg;
                if(fullMsg.charAt(fullMsg.length()-1) == ';' && fullMsg.charAt(fullMsg.length() - 2) == ' '){
                    if (!isLogged){
                        System.out.println("fullMsg: " + fullMsg);
                        msg = fullMsg.substring(0, fullMsg.length()-2);
                        System.out.println("msg: " + msg + "\n\n\n");
                        String[] msgSplit = Utils.splitFirstSpace(msg);
                        if (msgSplit.length != 2){
                            clientSocket.sendMsg("Erro: Formato de mensagem inválido!");
                            continue;
                        }

                        String user = msgSplit[0];
                        String pass = msgSplit[1];

                        if (loginUser(user, pass)){
                            clientSocket.sendMsg("1");
                            isLogged = true;
                        } else {
                            clientSocket.sendMsg("0");
                        }

                    }
                    else {
                        clientSocket.sendMsg("Mensagem recebida: " + msg);
                    }
                    fullMsg = "";
                }
                else{

                }

            }
        }
        finally {
            clientSocket.close();
        }
    }

    public boolean loginUser(String user, String pass){
        // Pegando senha do banco e verificando se user está cadastrado nele
        String criptPass = RepositoryMySQL.getPass(user);
        if(criptPass.equals("-1")){
            System.out.println("Erro ao pegar senha do banco!");
            return false;
        }
        
        // Descriptografando senha
        String validPass;
        try {
            validPass = Criptography.decryptRSA(criptPass);
        } catch (Exception e) {
            System.out.println("Erro ao descriptografar senha do banco " + criptPass);
            return false;
        }
        
        // Descriptografando senha recebida do cliente
        String inputPass;
        try{
            inputPass = Criptography.decryptRSA(pass);
        } catch (Exception e) {
            System.out.println("Erro ao descriptografar senha do cliente " + pass);
            return false;
        }

        // Verificando se senhas são iguais
        if (inputPass.equals(validPass)) {
            return true;
        }
        else{
            System.out.println("Senha inválida: " + inputPass + " != " + validPass);
            return false;
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
