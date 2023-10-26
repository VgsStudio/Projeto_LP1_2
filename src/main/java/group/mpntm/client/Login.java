package group.mpntm.client;



import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Login {
    private int control;
    private Client client;

    public Login(Client client) {
        this.client = client;
    }

    public boolean validate(String user, String password) {

        try {
            control = client.start(user, password);

            if (control == 1) {
                return true;
            } else {
                return false;
            }

        } catch (IOException e) {
            control = 0;
            return false;
        }
    }
    public int getControl() {
        return control;
    }
}
