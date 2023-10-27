package group.mpntm.Comunication.Events;

import java.util.HashSet;

public class LoginButtonPressedEvent {

    private static LoginButtonPressedEvent instance = null;
    public static LoginButtonPressedEvent getInstance() {
        if (instance == null) {
            instance = new LoginButtonPressedEvent();
        }
        return instance;
    }
    private HashSet<LoginButtonPressedListener> loginButtonPressedListeners = new HashSet<LoginButtonPressedListener>();

    public void Invoke(String username, String password){
        for (var loginButtonPressedListener : loginButtonPressedListeners) {
            loginButtonPressedListener.OnLoginButtonPressed(username,password);
        }
    }

    public void AddListener(LoginButtonPressedListener loginButtonPressedListener) {
        loginButtonPressedListeners.add(loginButtonPressedListener);
    }

    public void RemoveListener(LoginButtonPressedListener loginButtonPressedListener) {
        loginButtonPressedListeners.remove(loginButtonPressedListener);
    }
}


