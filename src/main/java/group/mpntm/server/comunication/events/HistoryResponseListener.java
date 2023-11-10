package group.mpntm.server.comunication.events;

import group.mpntm.server.comunication.mesasgeContent.HistoryResponseContent;

public interface HistoryResponseListener {
    public void OnHistoryResponseReceived(HistoryResponseContent historyResponseContent);
}
