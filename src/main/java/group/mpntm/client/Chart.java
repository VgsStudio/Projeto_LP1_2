package group.mpntm.client;

import group.mpntm.comunication.events.CandleReceivedEvent;
import group.mpntm.comunication.events.HistoryButtonPressedEvent;
import group.mpntm.comunication.events.HistoryResponseEvent;
import group.mpntm.comunication.mesasgeContent.ChartInitContent;
import group.mpntm.share.entity.Candle;
import org.knowm.xchart.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    public LinkedList<Candle> currentHistory = new LinkedList<>();
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
    private DefaultTableModel model;
    private LocalDateTime start;
    private int interval;

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
                langChooser.setLastLang(lang[finalI]);
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
        this.start = start;
        this.interval = content.interval;

        ResourceBundle bn = langChooser.getBn();

        String datetimeFormatter;

        if (bn.getLocale().getLanguage() == "en"){
            datetimeFormatter = "hh:mm:ss";

        } else {
            datetimeFormatter = "HH:mm:ss";
        }


        chart.getStyler()
                .setxAxisTickLabelsFormattingFunction(
                        x -> start.plusSeconds(x.longValue() * content.interval).format(DateTimeFormatter.ofPattern(datetimeFormatter))
                );

        chart.getStyler().setToolTipsEnabled(true);

        chartPanel = new XChartPanel<OHLCChart>(chart);
        upperPanel.add(chartPanel);

        // upperPanel
        upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.X_AXIS));
        add(upperPanel);


        // History
        historyButton = new JButton(bn.getString("chart.history"));
        historyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        historyButton.addActionListener(e -> {

            if (historyTable != null){
                upperPanel.remove(historyScrollPane);
                upperPanel.remove(historyTable);
                historyScrollPane = null;
                historyTable = null;
                this.repaint();
                this.revalidate();
                pack();

            }
            else {
            HistoryButtonPressedEvent.getInstance().Invoke();
            historyButton.setEnabled(false);
            }
        });
        add(historyButton);

        HistoryResponseEvent.getInstance().AddListener(
                (historyResponseContent) -> {
                    addHistoryTable(historyResponseContent.candles);
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
        

        if (chartPanel == null) {
            return;
        }

        xData.add((double) ++col);
        fifo.add(candle);
        if (fifo.size() > 20) {
            fifo.removeFirst();
        }
        if (xData.size() > 20) {
            xData.removeFirst();
        }

        String pattern;
        
        switch (bn.getLocale().getLanguage()) {
            case "pt":
                pattern = "dd-MM-yyyy";
                break;
            case "en":
                pattern = "MM-dd-yyyy";
                break;
        
            default:
                pattern = "yyyy-MM-dd";
                break;
        }

        candle.date = start.format(DateTimeFormatter.ofPattern(pattern));

        if (historyTable != null){
            currentHistory.add(candle);
            String[] data = candle.toStringArray();
            model = (DefaultTableModel) historyTable.getModel();
            model.insertRow(0, data);
            historyTable.setModel(model);
            historyTable.setRowSelectionInterval(0, 0);

        }


        if (chart.getSeriesMap().containsKey(this.title)) {
            chart.updateOHLCSeries(this.title, xData.stream().mapToDouble(Double::doubleValue).toArray(), fifo.stream().mapToDouble(Candle::getOpen).toArray(), fifo.stream().mapToDouble(Candle::getHigh).toArray(), fifo.stream().mapToDouble(Candle::getLow).toArray(), fifo.stream().mapToDouble(Candle::getClose).toArray());
        } else {
            chart.addSeries(this.title, xData.stream().mapToDouble(Double::doubleValue).toArray(), fifo.stream().mapToDouble(Candle::getOpen).toArray(), fifo.stream().mapToDouble(Candle::getHigh).toArray(), fifo.stream().mapToDouble(Candle::getLow).toArray(), fifo.stream().mapToDouble(Candle::getClose).toArray())
                    .setDownColor(Color.RED)
                    .setUpColor(Color.GREEN);
        }

        chartPanel.repaint();
        chartPanel.revalidate();

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

            upperPanel.remove(historyScrollPane);
            upperPanel.remove(historyTable);
            historyScrollPane = null;
            historyTable = null;
            java.util.List<Candle> candles = (List<Candle>) currentHistory.clone(); 
            addHistoryTable(candles);


        }

        chartPanel.repaint();
    }

    public void addHistoryTable(java.util.List<Candle> candles){
        currentHistory.clear();
        currentHistory.addAll(candles);
        String[] headers = {bn.getString("chart.date"), bn.getString("chart.open"), bn.getString("chart.close"), bn.getString("chart.high"), bn.getString("chart.low"), bn.getString("chart.time")};

        String[][] data = new String[candles.size()][6];

         String pattern;
            
        switch (bn.getLocale().getLanguage()) {
            case "pt":
                pattern = "dd-MM-yyyy";
                break;
            case "en":
                pattern = "MM-dd-yyyy";
                break;
            
            default:
                pattern = "yyyy-MM-dd";
                break;
            }

        for (int i = 0; i < candles.size(); i++) {
            data[i][0] = start.format(DateTimeFormatter.ofPattern(pattern));
            data[i][1] = String.valueOf(candles.get(i).getOpen());
            data[i][2] = String.valueOf(candles.get(i).getClose());
            data[i][3] = String.valueOf(candles.get(i).getHigh());
            data[i][4] = String.valueOf(candles.get(i).getLow());
            data[i][5] = String.valueOf(candles.get(i).getTime());
        }

        model = new DefaultTableModel(data, headers);
        this.historyTable = new JTable(model);
        historyScrollPane = new JScrollPane(historyTable);
        historyScrollPane.setBorder(BorderFactory.createTitledBorder(bn.getString("chart.history")));
        upperPanel.add(historyScrollPane);
        pack();
    }
}
