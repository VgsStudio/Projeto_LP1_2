package group.mpntm.client;

import group.mpntm.server.Server;
import group.mpntm.share.cripto.Criptography;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket clientSocket; 
    private Scanner scanner;
    private PrintWriter saida;
    private BufferedReader entrada;
    
    public Client(){
        scanner = new Scanner(System.in);
    }

    public int start(String login, String password) throws IOException{
        clientSocket = new Socket(Server.ADDRESS, Server.PORT);
        saida = new PrintWriter(clientSocket.getOutputStream(), true);
        entrada = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));
        System.out.println("Cliente " + Server.ADDRESS + ":" + Server.PORT + " conectado ao servidor!");

        int response = loginUser(login, password);

        if (response == 1){
            System.out.println("Login realizado com sucesso!");
            return 1;
        } else {
            System.out.println("Erro ao realizar o login!");
            clientSocket.close();
            return 0;
        }

    }

    public void stop() throws IOException{
        clientSocket.close();
    }

    public void requestInfo(){

    }


    public int loginUser(String login, String password) throws IOException {
        String encryptedPass = encryptPassword(password);
        saida.println(login + " " + encryptedPass + " ;");
        String response = entrada.readLine();
        return Integer.parseInt(response);
    }


    private void messageLoop() throws IOException{
        String msg;
        System.out.println("Aguardando a mensagem de uma mensagem!");
        do{
            System.out.print("Digite uma mensagem (ou <sair> para finalizar): ");
            msg = scanner.nextLine();
            saida.println(msg);
            System.out.println(entrada.readLine());
        }while(!msg.equals("<sair>"));
    }

    private String encryptPassword(String password){
        
        try {
            String encryptedPass = Criptography.encryptRSA(password); 
            System.out.println("Senha criptografada: " + encryptedPass);
            return encryptedPass;
        } catch (Exception e) {
            System.out.println("Erro ao criptografar a senha: " + e.getMessage());
            return "-1";
        }
    }
}
