package group.mpntm.server.comunication.events;


import java.util.HashSet;

/**
 * This event is triggered when the user failedly logs in.
 */
public class LoginFailedEvent {
    private static LoginFailedEvent instance = null;
    private HashSet<LoginFailedListener> loginFailedListeners = new HashSet<LoginFailedListener>();

    public static LoginFailedEvent getInstance() {
        if (instance == null) {
            instance = new LoginFailedEvent();
        }
        return instance;
    }

    public void Invoke() {
        for (var loginFailedListener : loginFailedListeners) {
            loginFailedListener.OnLoginFailed();
        }
    }

    public void AddListener(LoginFailedListener loginFailedListener) {
        loginFailedListeners.add(loginFailedListener);
    }

    public void RemoveListener(LoginFailedListener loginFailedListener) {
        loginFailedListeners.remove(loginFailedListener);
    }
}
