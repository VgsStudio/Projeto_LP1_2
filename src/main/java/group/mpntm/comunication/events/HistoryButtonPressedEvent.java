package group.mpntm.comunication.events;

import java.util.HashSet;

public class HistoryButtonPressedEvent{
    private static HistoryButtonPressedEvent instance = null;
    public static HistoryButtonPressedEvent getInstance() {
        if (instance == null) {
            instance = new HistoryButtonPressedEvent();
        }
        return instance;
    }
    private HashSet<HistoryButtonPressedListener> historyButtonPressedListeners = new HashSet<HistoryButtonPressedListener>();

    public void Invoke(){
        for (var historyButtonPressedListener : historyButtonPressedListeners) {
            historyButtonPressedListener.OnHistoryButtonPressed();
        }
    }

    public void AddListener(HistoryButtonPressedListener historyButtonPressedListener) {
        historyButtonPressedListeners.add(historyButtonPressedListener);
    }

    public void RemoveListener(HistoryButtonPressedListener historyButtonPressedListener) {
        historyButtonPressedListeners.remove(historyButtonPressedListener);
    }
}