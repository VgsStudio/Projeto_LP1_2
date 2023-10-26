package group.mpntm.Comunication.MessageImplementations.Server;

import com.google.gson.Gson;
import group.mpntm.Comunication.ClientCommunication;
import group.mpntm.Comunication.MesasgeContent.LoginContent;
import group.mpntm.Comunication.MesasgeContent.LoginContentResponse;
import group.mpntm.Comunication.MessageImplementations.IServerMessageImplementation;
import group.mpntm.Comunication.MessageImplementations.Client.LoginResponseReceiver;
import group.mpntm.Comunication.Profiles.ClientProfile;
import group.mpntm.server.database.repo.RepositoryMySQL;
import group.mpntm.share.cripto.Criptography;


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
                    System.out.println("Senha inválida: " + inputPass + " != " + validPass);
                    response = false;
                }
            } catch (Exception e) {
                response = false;
            }
            
        }

        return response;
    }
}
