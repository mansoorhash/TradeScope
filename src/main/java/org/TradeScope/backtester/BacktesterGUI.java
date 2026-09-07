package org.TradeScope.backtester;

import org.TradeScope.backtester.ui.ChartGridPanel;
import org.TradeScope.backtester.ui.ControlPanel;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

public class BacktesterGUI {

    private JFrame frame;

    public BacktesterGUI(String selectedStock) {

        frame = new JFrame("TradeScope");

        ChartGridPanel chartGridPanel = new ChartGridPanel();
        ControlPanel controlPanel = new ControlPanel(selectedStock);

        /*
         * Controller connects the control panel
         * to the chart panel and handles application logic.
         */
        new BacktesterController(
            controlPanel,
            chartGridPanel
        );

        JSplitPane splitPane =
            new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                chartGridPanel,
                controlPanel
            );

        splitPane.setResizeWeight(0.75);
        splitPane.setDividerLocation(898);

        frame.add(splitPane);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(1200, 750);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new BacktesterGUI("AAPL");
        });
    }
}
