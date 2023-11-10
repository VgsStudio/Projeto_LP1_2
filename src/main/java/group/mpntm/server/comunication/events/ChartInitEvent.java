package group.mpntm.server.comunication.events;

import java.util.HashSet;

import group.mpntm.server.comunication.mesasgeContent.ChartInitContent;

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


