package group.mpntm.server.generator;

import group.mpntm.share.entity.Candle;

import java.util.HashSet;


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
                Thread.sleep(2500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            double random = Math.random();

            if (lastCandle == null) {
                open = 169;
                close = 140;
                high = 200;
                low = 70;
                 
                lastCandle = new Candle(open, close, high, low);
            }


            open = lastCandle.getClose();

            double correctionScale = 5.0;
            double randomScale = 0.5;


            double factor = (lastCandle.getOpen()/ lastCandle.getClose() - 1.0)/correctionScale + 1.0;


            var randomNumber = (Math.random()*2.0-1.0);


            close = open * factor + randomScale * randomNumber;


            low = (Math.min(open,close)  - (Math.abs(open-close)*0.2 * Math.random()));
            high = (Math.max(open,close) + (Math.abs(open-close)*0.2 * Math.random()));

            open = Math.round(open * 100.0) / 100.0;
            close = Math.round(close * 100.0) / 100.0;
            high = Math.round(high * 100.0) / 100.0;
            low = Math.round(low * 100.0) / 100.0;

            lastCandle = new Candle(open, close, high, low);


            NumberGeneratedEvent.getInstance().Invoke(lastCandle);
        }

    }

    public static class NumberGeneratedEvent{
        private static NumberGeneratedEvent instance = null;
        public static NumberGeneratedEvent getInstance() {
            if (instance == null) {
                instance = new NumberGeneratedEvent();
            }
            return instance;
        }
        private HashSet<NumberGeneratedListener> NumberGeneratedListeners = new HashSet<NumberGeneratedListener>();

        public void Invoke(Candle candle){
            for (var NumberGeneratedListener : NumberGeneratedListeners) {
                NumberGeneratedListener.onNumberGenerated(candle);
            }
        }

        public void AddListener(NumberGeneratedListener NumberGeneratedListener) {
            NumberGeneratedListeners.add(NumberGeneratedListener);
        }

        public void RemoveListener(NumberGeneratedListener NumberGeneratedListener) {
            NumberGeneratedListeners.remove(NumberGeneratedListener);
        }

    }
    
    public interface NumberGeneratedListener{
        void onNumberGenerated(Candle candle);
    }

}
