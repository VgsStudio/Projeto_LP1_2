package group.mpntm.server.generator;

public class Main {
    public static void main(String[] args) {
        OHLCGenerator ohlcGenerator = new OHLCGenerator();
        OHLCGenerator.NumberGeneratedEvent.getInstance().AddListener(
                System.out::println
        );
    }
}
