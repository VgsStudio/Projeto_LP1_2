package group.mpntm.comunication.events;

import group.mpntm.share.entity.Candle;

public interface CandleReceivedListener {
    public void OnCandleReceived(Candle candle);
}
