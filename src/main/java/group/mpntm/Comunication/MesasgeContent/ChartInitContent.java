package group.mpntm.Comunication.MesasgeContent;

public class ChartInitContent {

    public String period;
    public int interval;
    public String title;
    public String start;

    public ChartInitContent(String period, int interval, String title, String start) {
        this.period = period;
        this.interval = interval;
        this.title = title;
        this.start = start;
    }

    public String toString() {
        return "days " + interval + " " + period + " " + title + " " + start;
    }



}

