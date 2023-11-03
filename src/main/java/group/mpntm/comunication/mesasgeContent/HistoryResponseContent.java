package group.mpntm.comunication.mesasgeContent;

import java.util.List;

import group.mpntm.share.entity.Candle;

public class HistoryResponseContent {
    public List<Candle> candles;
    public HistoryResponseContent(List<Candle> candles){
        this.candles = candles;
    } 
}
