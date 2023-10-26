package group.mpntm.client;

import group.mpntm.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public void requestInfo(Chart chart){

        saida.println("info");
        String response;
        try {

            response = entrada.readLine();

            String[] msgSplit = response.split(" ");
            if (msgSplit.length != 4){
                System.out.println("Erro: Formato de mensagem inválido!");
                return;
            }

            String period = msgSplit[0];
            int interval = Integer.parseInt(msgSplit[1]);
            String title = msgSplit[2];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss");
            LocalDateTime start = LocalDateTime.parse(msgSplit[3], formatter);

            chart.go(title, period, interval, start);

            while((response = entrada.readLine()) != null){
                if (response.equals("end")){
                    break;
                }
                msgSplit = response.split(" ");
                if (msgSplit.length != 4){
                    System.out.println("Erro: Formato de mensagem inválido!");
                    continue;
                }
                double open = Double.parseDouble(msgSplit[0]);
                double close = Double.parseDouble(msgSplit[1]);
                double high = Double.parseDouble(msgSplit[2]);
                double low = Double.parseDouble(msgSplit[3]);
                LocalDateTime date = LocalDateTime.now();
                Candle candle = new Candle(open, close, high, low, date);
                chart.addCandle(candle);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }




    }


    public int loginUser(String login, String password) throws IOException {
        String encryptedPass = encryptPassword(password);
        saida.println(login + " " + encryptedPass);
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

    public static void main(String[] args) {
        System.out.println("====== Console do Cliente ======");
        try {
            Client client = new Client();
            if (client.start("admin", "admin") == 0){
                System.out.println("Erro ao iniciar o cliente!");
            } else {
                System.out.println("Cliente iniciado com sucesso!");
            };
        } catch (IOException e) {
            System.out.println("Erro ao iniciar o cliente: " + e.getMessage());
        }
        System.out.println("Cliente finalizado!");

    }

    private String encryptPassword(String password){
        String encryptedPass = password; // TODO: Criptografar a senha
        return encryptedPass;
    }
}
