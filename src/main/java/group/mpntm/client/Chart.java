package group.mpntm.client;

import group.mpntm.Comunication.Events.CandleReceivedEvent;
import group.mpntm.Comunication.MesasgeContent.ChartInitContent;
import org.knowm.xchart.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.ResourceBundle;

public class Chart extends JFrame {
    private JLabel label;
    private JButton button;
    public SwingWrapper<XYChart> sw;
    public JPanel chartPanel;
    public OHLCChart chart;
    public LinkedList<Candle> fifo = new LinkedList<>();
    public LinkedList<Double> xData = new LinkedList<>();
    private ResourceBundle bn;
    private String title;
    private JMenu menu;
    private JMenuBar menuBar;
    private JMenuItem menuItem;
    private String[] lang = ClientScreen.lang;
    private LangChooser langChooser;

    int col = 0;

    public Chart(LangChooser langChooser) {
        super(langChooser.getBn().getString("chart.title"));
        this.bn = langChooser.getBn();
        this.langChooser = langChooser;

        CandleReceivedEvent.getInstance().AddListener(
                this::addCandle
        );
    }


    public void go(ChartInitContent content) {
//        label = new JLabel("TODO: Implementar Login ");
//        button = new JButton("Sair");

//        button.addActionListener(e -> {
//            dispose();
//        });
//        JPanel panel = new JPanel();
//        panel.add(label);
//        panel.add(button);

        menu = new JMenu(bn.getString("login.language"));
        menuBar = new JMenuBar();


        // add menu item to each menu
        for (int i = 0; i < lang.length; i++) {
            menuItem = new JMenuItem(lang[i]);
            int finalI = i;
            menuItem.addActionListener(e -> {
                langChooser.chooseLang(lang[finalI]);
                bn = langChooser.getBn();
                setLanguage();
            });
            menu.add(menuItem);
        }

        menuBar.add(menu);
        add(menuBar);


        chart = new OHLCChartBuilder().width(800).height(600).title(content.title).build();
        this.title = content.title;

        chart.getStyler().setLegendVisible(false);

        chart.getStyler().setXAxisLabelRotation(45);

        LocalDateTime start = LocalDateTime.parse(content.start, DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss"));

        chart.getStyler()
                .setxAxisTickLabelsFormattingFunction(
                        x -> start.plusSeconds(x.longValue() * content.interval).format(DateTimeFormatter.ofPattern("dd hh:mm:ss"))
                );

        chart.getStyler().setToolTipsEnabled(true);


        // Show it
        chartPanel = new XChartPanel<OHLCChart>(chart);
//        add(panel);
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

        if (chart.getSeriesMap().containsKey(this.title)) {
            chart.updateOHLCSeries(this.title, xData.stream().mapToDouble(Double::doubleValue).toArray(), fifo.stream().mapToDouble(Candle::getOpen).toArray(), fifo.stream().mapToDouble(Candle::getHigh).toArray(), fifo.stream().mapToDouble(Candle::getLow).toArray(), fifo.stream().mapToDouble(Candle::getClose).toArray());
        } else {
            chart.addSeries(this.title, xData.stream().mapToDouble(Double::doubleValue).toArray(), fifo.stream().mapToDouble(Candle::getOpen).toArray(), fifo.stream().mapToDouble(Candle::getHigh).toArray(), fifo.stream().mapToDouble(Candle::getLow).toArray(), fifo.stream().mapToDouble(Candle::getClose).toArray())
                    .setDownColor(Color.RED)
                    .setUpColor(Color.GREEN);
        }

        chartPanel.repaint();

    }

    public void setLanguage(){
        this.setTitle(bn.getString("chart.title"));
        menu.setText(bn.getString("login.language"));
        menuItem.setText(bn.getString("login.language"));
    }
}
