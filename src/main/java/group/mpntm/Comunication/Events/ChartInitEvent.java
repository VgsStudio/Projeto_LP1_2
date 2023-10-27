package group.mpntm.Comunication.Events;

import group.mpntm.Comunication.MesasgeContent.ChartInitContent;
import group.mpntm.client.Candle;

import java.util.HashSet;

public class ChartInitEvent {

    private static ChartInitEvent instance = null;
    public static ChartInitEvent getInstance() {
        if (instance == null) {
            instance = new ChartInitEvent();
        }
        return instance;
    }
    private HashSet<ChartInitListener> chartInitListeners = new HashSet<ChartInitListener>();

    public void Invoke(ChartInitContent content){
        for (var chartInitListener : chartInitListeners) {
            chartInitListener.OnChartInit(content);
        }
    }

    public void AddListener(ChartInitListener chartInitListener) {
        chartInitListeners.add(chartInitListener);
    }

    public void RemoveListener(ChartInitListener chartInitListener) {
        chartInitListeners.remove(chartInitListener);
    }
}


