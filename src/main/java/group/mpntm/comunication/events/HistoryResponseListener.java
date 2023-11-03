package group.mpntm.comunication.events;

import group.mpntm.comunication.mesasgeContent.HistoryResponseContent;

public interface HistoryResponseListener {
    public void OnHistoryResponseReceived(HistoryResponseContent historyResponseContent);
}
