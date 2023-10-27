package group.mpntm.client;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Candle {
    private double open;
    private double close;
    private double high;
    private double low;
    public String date;

    public Candle(double open, double close, double high, double low, String date) {
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.date = date;
    }

    public Candle(double open, double close, double high, double low) {
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.date = String.valueOf(LocalDate.now());
    }

    public Candle() {
        double random = Math.random()*100;

        this.open = random;
        this.close = random + Math.random()*10;
        this.high = this.close + Math.random()*10;
        this.low = this.open - Math.random()*10;
        this.date = String.valueOf(LocalDateTime.now().plusDays((long) random));
    }

    public double getOpen() {
        return this.open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getClose() {
        return this.close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getHigh() {
        return this.high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return this.low;
    }

    public void setLow(double low) {
        this.low = low;
    }


    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString() {
        return "Open: " + this.open + " Close: " + this.close + " High: " + this.high + " Low: " + this.low + " Date: " + this.date;
    }

    public String[] toStringArray() {
        return new String[] {this.date, String.valueOf(this.open), String.valueOf(this.close), String.valueOf(this.high), String.valueOf(this.low)};
    }
}
