package group.mpntm.server.generator;

import group.mpntm.client.Candle;


public class OHLCGenerator extends Thread{
    private double open;
    private double close;
    private double high;
    private double low;

    private Candle lastCandle;


public OHLCGenerator(){

        open = 0;
        close = 0;
        high = 0;
        low = 0;
        lastCandle = null;
        start();
    }

    @Override
    public void run(){

        while (true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            double random = Math.random()*100;

            open = lastCandle != null ? lastCandle.getClose() : random;
            close = random + Math.random()*100;
            high = close + Math.random()*100;
            low = open - Math.random()*100;

            lastCandle = new Candle(open, close, high, low);

            System.out.println(lastCandle);

        }

    }

}
