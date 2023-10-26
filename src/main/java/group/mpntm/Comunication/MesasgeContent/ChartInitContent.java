package group.mpntm.Comunication.MesasgeContent;

public class ChartInitContent {

    String period;
    int interval;
    String title;
    String date;

    public ChartInitContent(String period, int interval, String title, String date) {
        this.period = period;
        this.interval = interval;
        this.title = title;
        this.date = date;
    }

    public String toString() {
        return "days " + interval + " " + period + " " + title + " " + date;
    }



}

