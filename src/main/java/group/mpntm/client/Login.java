package group.mpntm.client;



import java.util.ArrayList;
import java.util.List;

public class Login {
    private int control; 
    public List<String> breaker(List<String> list){
        List<String> parts = new ArrayList<String>();

        for(String str: list){
            String regex = "\\s:\\s"; 
            String[] parta = str.split(regex);
            for(String word: parta){
                
                parts.add(word);
            }
        }
        return parts; 
        
    }

    public boolean validate(String user, String password, List<String> list) {
        List<String> parts = breaker(list);

        for (String login : parts) {
            if (login.equals(user)) {            
                for(String pass: parts) {
                    if (pass.equals(password)) {
                        control = 0;
                        return true;
                    } else if(!pass.equals(password)) {
                        // Senha invalida
                        control = 1;
                        return false;
                    }
                } 
            }
        }
        // User invalido 
        control = -1; 
        return false;
    }
    public int getControl() {
        return control;
    }
}
