package group.mpntm.Comunication.Events;

import group.mpntm.Comunication.MesasgeContent.ChartInitContent;
import group.mpntm.client.Candle;

public interface ChartInitListener {
    public void OnChartInit(ChartInitContent chartInitContent);
}
