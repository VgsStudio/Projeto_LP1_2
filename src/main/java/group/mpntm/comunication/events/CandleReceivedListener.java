package group.mpntm.comunication.events;

import group.mpntm.client.Candle;

public interface CandleReceivedListener {
    public void OnCandleReceived(Candle candle);
}
