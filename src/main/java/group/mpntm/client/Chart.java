package group.mpntm.client;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.util.LinkedList;

public class Chart extends JFrame {
    private JLabel label;
    private JButton button;
    public SwingWrapper<XYChart> sw;
    public JPanel chartPanel;
    public XYChart chart;
    public int i = 0;
    LinkedList <Double> fifo = new LinkedList<Double>();
    LinkedList <Double> col = new LinkedList<Double>();

    public Chart() {
        label = new JLabel("TODO: Implementar Login ");
        button = new JButton("Login");
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(button);

        // Create Chart
        chart = QuickChart.getChart("SwingWorker XChart Real-time Demo", "Time", "Value", "series", new double[] { 2 }, new double[] { 0 });
        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setXAxisLabelRotation(45);
        chart.getStyler().setxAxisTickLabelsFormattingFunction(value -> String.format("%.0f", value));

        // Show it
        chartPanel = new XChartPanel<XYChart>(chart);
        add(chartPanel);
        add(panel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        pack();

        setLocationRelativeTo(null);

        button.addActionListener(e -> {
            try {
                int random = (int) (Math.random() * 100);
                addPoint(i, random);
                i++;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
    }


    public void addPoint(double x, double y) {
        fifo.add(y);
        col.add(x);
        chart.updateXYSeries("series", col, fifo, null);
        // change chart scale

        chartPanel.repaint();
    }
}
