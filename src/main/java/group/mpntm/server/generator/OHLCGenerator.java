package group.mpntm.server.generator;

import group.mpntm.Comunication.Events.LoginButtonPressedEvent;
import group.mpntm.Comunication.Events.LoginButtonPressedListener;
import group.mpntm.client.Candle;

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

//            System.out.println(lastCandle);

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
