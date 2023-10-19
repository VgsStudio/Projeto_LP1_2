package group.mpntm.client;

import group.mpntm.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;

public class Client {
    private Socket clientSocket;
    private Scanner scanner;
    private PrintWriter saida;
    private BufferedReader entrada;

    public Client(){
        scanner = new Scanner(System.in);
    }

    public void start() throws IOException{
        clientSocket = new Socket(Server.ADDRESS, Server.PORT);
        saida = new PrintWriter(clientSocket.getOutputStream(), true);
        entrada = new BufferedReader(new java.io.InputStreamReader(clientSocket.getInputStream()));
        System.out.println("Cliente " + Server.ADDRESS + ":" + Server.PORT + " conectado ao servidor!");
        messageLoop();
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

    public static void main(String[] args) {
        System.out.println("====== Console do Cliente ======");
        try {
            Client client = new Client();
            client.start();
        } catch (IOException e) {
            System.out.println("Erro ao iniciar o cliente: " + e.getMessage());
        }
        System.out.println("Cliente finalizado!");

    }
}
