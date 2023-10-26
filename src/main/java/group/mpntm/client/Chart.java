package group.mpntm.client;

import org.knowm.xchart.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;

public class Chart extends JFrame {
    private JLabel label;
    private JButton button;
    public SwingWrapper<XYChart> sw;
    public JPanel chartPanel;
    public OHLCChart chart;
    public LinkedList<Candle> fifo = new LinkedList<>();
    public LinkedList<Double> xData = new LinkedList<>();

    int col = 0;


    public void go(String title, String period, int interval, LocalDateTime start) {
        label = new JLabel("TODO: Implementar Login ");
        button = new JButton("Sair");

        button.addActionListener(e -> {
            dispose();
        });
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(button);

        chart = new OHLCChartBuilder().width(800).height(600).title(title).build();

        chart.getStyler().setLegendVisible(false);

        chart.getStyler().setXAxisLabelRotation(45);

        chart.getStyler()
                .setxAxisTickLabelsFormattingFunction(
                        x -> start.plusSeconds(x.longValue() * interval).format(DateTimeFormatter.ofPattern("dd hh:mm:ss"))
                );

        chart.getStyler().setToolTipsEnabled(true);


        // Show it
        chartPanel = new XChartPanel<OHLCChart>(chart);
        add(panel);
        add(chartPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        pack();

        setLocationRelativeTo(null);
    }


    public void addCandle(Candle candle) {

        xData.add((double) ++col);
        fifo.add(candle);
        if (fifo.size() > 20) {
            fifo.removeFirst();
        }
        if (xData.size() > 20) {
            xData.removeFirst();
        }

        if (chart.getSeriesMap().containsKey("series")) {
            chart.updateOHLCSeries("series", xData.stream().mapToDouble(Double::doubleValue).toArray(), fifo.stream().mapToDouble(Candle::getOpen).toArray(), fifo.stream().mapToDouble(Candle::getHigh).toArray(), fifo.stream().mapToDouble(Candle::getLow).toArray(), fifo.stream().mapToDouble(Candle::getClose).toArray());
        } else {
            chart.addSeries("series", xData.stream().mapToDouble(Double::doubleValue).toArray(), fifo.stream().mapToDouble(Candle::getOpen).toArray(), fifo.stream().mapToDouble(Candle::getHigh).toArray(), fifo.stream().mapToDouble(Candle::getLow).toArray(), fifo.stream().mapToDouble(Candle::getClose).toArray())
                    .setDownColor(Color.RED)
                    .setUpColor(Color.GREEN);
        }

        chartPanel.repaint();

    }
}