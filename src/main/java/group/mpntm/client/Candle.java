package group.mpntm.client;

import java.util.Date;

public class Candle {
    private double open;
    private double close;
    private double high;
    private double low;
    private Date date;

    public Candle(double open, double close, double high, double low, Date date) {
        this.open = open;
        this.close = close;
        this.high = high;
        this.low = low;
        this.date = date;
    }

    public Candle() {
        this.open = 0;
        this.close = 0;
        this.high = 0;
        this.low = 0;
        this.date = new Date();
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


    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
