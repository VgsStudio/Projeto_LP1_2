package group.mpntm.Comunication.Events;

import group.mpntm.client.Candle;

import java.util.HashSet;

public class CandleReceivedEvent {

    private static CandleReceivedEvent instance = null;
    public static CandleReceivedEvent getInstance() {
        if (instance == null) {
            instance = new CandleReceivedEvent();
        }
        return instance;
    }
    private HashSet<CandleReceivedListener> candleReceivedListeners = new HashSet<CandleReceivedListener>();

    public void Invoke(Candle candle){
        for (var candleReceivedListener : candleReceivedListeners) {
            candleReceivedListener.OnCandleReceived(candle);
        }
    }

    public void AddListener(CandleReceivedListener candleReceivedListener) {
        candleReceivedListeners.add(candleReceivedListener);
    }

    public void RemoveListener(CandleReceivedListener candleReceivedListener) {
        candleReceivedListeners.remove(candleReceivedListener);
    }
}


