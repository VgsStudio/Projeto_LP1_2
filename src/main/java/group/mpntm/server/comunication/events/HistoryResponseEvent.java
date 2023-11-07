package group.mpntm.server.comunication.events;

import java.util.HashSet;

import group.mpntm.server.comunication.mesasgeContent.HistoryResponseContent;

public class HistoryResponseEvent {
    private static HistoryResponseEvent instance = null;
    public static HistoryResponseEvent getInstance() {
        if (instance == null) {
            instance = new HistoryResponseEvent();
        }
        return instance;
    }
    private HashSet<HistoryResponseListener> historyResponseListeners = new HashSet<HistoryResponseListener>();

    public void Invoke(HistoryResponseContent content){
        for (var historyResponseListener : historyResponseListeners) {
            historyResponseListener.OnHistoryResponseReceived(content);
        }
    }

    public void AddListener(HistoryResponseListener historyResponseListener) {
        historyResponseListeners.add(historyResponseListener);
    }

    public void RemoveListener(HistoryResponseListener historyResponseListener) {
        historyResponseListeners.remove(historyResponseListener);
    }
}
