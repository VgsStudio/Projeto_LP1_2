package group.mpntm.server.comunication.messageImplementations.Server;

import com.google.gson.Gson;

import group.mpntm.server.comunication.messageImplementations.Client.ChartInitReceiver;
import group.mpntm.server.comunication.mesasgeContent.ChartInitContent;
import group.mpntm.server.comunication.mesasgeContent.LoginContent;
import group.mpntm.server.comunication.mesasgeContent.LoginContentResponse;
import group.mpntm.server.comunication.messageImplementations.IServerMessageImplementation;
import group.mpntm.server.comunication.messageImplementations.Client.LoginResponseReceiver;
import group.mpntm.server.comunication.profiles.ClientProfile;
import group.mpntm.server.database.repo.RepositoryMySQL;
import group.mpntm.share.cripto.Criptography;

import java.time.LocalDateTime;


/**
 * this implementation is used to receive the server's public key.
 * the client will use this key to encrypt the password that it sends to the server.
 */

public class EncryptedLoginReceiver implements IServerMessageImplementation {
    @Override
    public void Activate(ClientProfile clientProfile, String messageContent) {
        LoginContent loginContent = new Gson().fromJson(messageContent, LoginContent.class);
        
        LoginContentResponse loginContentResponse = new LoginContentResponse(validateLogin(loginContent));


        String json = new Gson().toJson(loginContentResponse);

        clientProfile.clientCommunicationServerSide.SendMessage(json, LoginResponseReceiver.class.getSimpleName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        LocalDateTime date = LocalDateTime.now();

        var chartInitJson = new Gson().toJson(new ChartInitContent("sec", 3, "CALV7", date.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss"))));

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (loginContentResponse.value){
            clientProfile.clientCommunicationServerSide.SendMessage(chartInitJson, ChartInitReceiver.class.getSimpleName());
            clientProfile.isLogged = loginContentResponse.value;
        }


    }

    public boolean validateLogin(LoginContent loginContent) {


        // Definindo variável de response
        boolean response = true;
        
        // Pegando senha do banco e verificando se user está cadastrado nele
        String criptPass = RepositoryMySQL.getPass(loginContent.username);
        if(criptPass.equals("-1")){
            System.out.println("Erro ao pegar senha do banco!");
            response = false;
        }
        else{
            // Descriptografando senha
            String validPass;
            try {
                // Descriptografando senha do banco
                validPass = Criptography.decryptRSA(criptPass);

                // Descriptografando senha recebida do cliente
                String inputPass;
                inputPass = Criptography.decryptRSA(loginContent.encryptedPassword);
        
                // Verificando se senhas são iguais
                if (!inputPass.equals(validPass)) {
                    response = false;
                }
            } catch (Exception e) {
                response = false;
            }
            
        }

        return response;
    }
}
