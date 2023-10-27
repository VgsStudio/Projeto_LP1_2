package group.mpntm.Comunication.Events;


import java.util.HashSet;

/**
 * This event is triggered when the user successfully logs in.
 */
public class LoginSuccessfulEvent {
    private static LoginSuccessfulEvent instance = null;
    private HashSet<LoginSuccessfulListener> loginSuccessfulListeners = new HashSet<LoginSuccessfulListener>();

    public static LoginSuccessfulEvent getInstance() {
        if (instance == null) {
            instance = new LoginSuccessfulEvent();
        }
        return instance;
    }

    public void Invoke() {
        for (var loginSuccessfulListener : loginSuccessfulListeners) {
            loginSuccessfulListener.OnLoginSuccessful();
        }
    }

    public void AddListener(LoginSuccessfulListener loginSuccessfulListener) {
        loginSuccessfulListeners.add(loginSuccessfulListener);
    }

    public void RemoveListener(LoginSuccessfulListener loginSuccessfulListener) {
        loginSuccessfulListeners.remove(loginSuccessfulListener);
    }
}
