package group.mpntm.Comunication.MesasgeContent;

public class LoginContent{
    public String username;
    public String encryptedPassword;
    public LoginContent(String username, String encryptedPassword){
        this.username = username;
        this.encryptedPassword = encryptedPassword;
    }
}