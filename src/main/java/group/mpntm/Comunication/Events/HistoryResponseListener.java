package group.mpntm.Comunication.Events;

import group.mpntm.Comunication.MesasgeContent.HistoryResponseContent;

public interface HistoryResponseListener {
    public void OnHistoryResponseReceived(HistoryResponseContent historyResponseContent);
}
