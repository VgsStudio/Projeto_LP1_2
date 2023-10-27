package group.mpntm.client;

import group.mpntm.Comunication.Events.CandleReceivedEvent;
import group.mpntm.Comunication.Events.HistoryButtonPressedEvent;
import group.mpntm.Comunication.Events.HistoryResponseEvent;
import group.mpntm.Comunication.MesasgeContent.ChartInitContent;
import org.knowm.xchart.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class Chart extends JFrame {
    private JButton historyButton;
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
    private JPanel upperPanel;
    private JTable historyTable;
    private JScrollPane historyScrollPane;

    int col = 0;

    public Chart(LangChooser langChooser) {
        super(langChooser.getBn().getString("chart.title"));
        this.bn = langChooser.getBn();
        this.langChooser = langChooser;

    }


    public void go(ChartInitContent content) {

        upperPanel = new JPanel();

        // Menu
        menu = new JMenu(bn.getString("login.language"));
        menuBar = new JMenuBar();

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
        setJMenuBar(menuBar);

        // Chart

        chart = new OHLCChartBuilder().width(800).height(600).title(content.title)
                .xAxisTitle(bn.getString("chart.xAxisTitle"))
                .yAxisTitle(bn.getString("chart.yAxisTitle"))
                .build();

        this.title = content.title;

        chart.getStyler().setLegendVisible(false);
        chart.getStyler().setXAxisLabelRotation(45);

        chart.getStyler().setYAxisDecimalPattern("$ ####.##");

        LocalDateTime start = LocalDateTime.parse(content.start, DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss"));

        ResourceBundle bn = langChooser.getBn();

        String datetimeFromatter;

        if (bn.getLocale().getLanguage() == "en"){
            datetimeFromatter = "hh:mm:ss";

        } else {
            datetimeFromatter = "HH:mm:ss";
        }


        chart.getStyler()
                .setxAxisTickLabelsFormattingFunction(
                        x -> start.plusSeconds(x.longValue() * content.interval).format(DateTimeFormatter.ofPattern(datetimeFromatter))
                );

        chart.getStyler().setToolTipsEnabled(true);

        chartPanel = new XChartPanel<OHLCChart>(chart);
        upperPanel.add(chartPanel);

        // upperPanel
        add(upperPanel);


        // History
        historyButton = new JButton(bn.getString("chart.history"));
        historyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        historyButton.addActionListener(e -> {
            Candle[] candles = new Candle[20];
            for (int i = 0; i < 20; i++) {
                candles[i] = new Candle();
            }
            addHistoryTable(List.of(candles));
            HistoryButtonPressedEvent.getInstance().Invoke();
            historyButton.setEnabled(false);
        });
        add(historyButton);

        HistoryResponseEvent.getInstance().AddListener(
                (historyResponseContent) -> {
                    for (Candle candle : historyResponseContent.candles) {
                        System.out.println(candle);
                    }
                    historyButton.setEnabled(true);
                }
        );



        // General
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        pack();
        setLocationRelativeTo(null);

        setResizable(false);

        // Listener
        CandleReceivedEvent.getInstance().AddListener(
                this::addCandle
        );
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
        chart.setXAxisTitle(bn.getString("chart.xAxisTitle"));
        chart.setYAxisTitle(bn.getString("chart.yAxisTitle"));
        historyButton.setText(bn.getString("chart.history"));

        if (historyScrollPane != null){
            historyScrollPane.setBorder(BorderFactory.createTitledBorder(bn.getString("chart.history")));
            String[] headers = {bn.getString("chart.date"), bn.getString("chart.open"), bn.getString("chart.close"), bn.getString("chart.high"), bn.getString("chart.low")};
            historyTable.getTableHeader().getColumnModel().getColumn(0).setHeaderValue(headers[0]);
            historyTable.getTableHeader().getColumnModel().getColumn(1).setHeaderValue(headers[1]);
            historyTable.getTableHeader().getColumnModel().getColumn(2).setHeaderValue(headers[2]);
            historyTable.getTableHeader().getColumnModel().getColumn(3).setHeaderValue(headers[3]);
            historyTable.getTableHeader().getColumnModel().getColumn(4).setHeaderValue(headers[4]);

        }

        chartPanel.repaint();
    }

    public void addHistoryTable(java.util.List<Candle> candles){

        String[] headers = {bn.getString("chart.date"), bn.getString("chart.open"), bn.getString("chart.close"), bn.getString("chart.high"), bn.getString("chart.low")};

        String[][] data = new String[candles.size()][5];

        for (int i = 0; i < candles.size(); i++) {
            data[i][0] = candles.get(i).date;
            data[i][1] = String.valueOf(candles.get(i).getOpen());
            data[i][2] = String.valueOf(candles.get(i).getClose());
            data[i][3] = String.valueOf(candles.get(i).getHigh());
            data[i][4] = String.valueOf(candles.get(i).getLow());
        }

        this.historyTable = new JTable(data, headers);
        historyScrollPane = new JScrollPane(historyTable);
        historyScrollPane.setBorder(BorderFactory.createTitledBorder(bn.getString("chart.history")));
        add(historyScrollPane);
        pack();
    }
}
