package org.TradeScope.backtester.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {

    private JLabel selectedStockLabel;
    private JButton runButton;
    private JComboBox<Integer> chartCountComboBox;

    private String selectedStock;

    public ControlPanel(String selectedStock) {

        this.selectedStock = selectedStock;

        setLayout(new GridBagLayout());
        setBorder(
            BorderFactory.createEmptyBorder(
                20,
                20,
                20,
                20
            )
        );

        selectedStockLabel = new JLabel("Selected stock: " + selectedStock);
        JLabel chartCountLabel = new JLabel("Number of charts:");

        chartCountComboBox = new JComboBox<>( new Integer[]{1, 2, 4, 6});
        chartCountComboBox.setSelectedItem(1);
        runButton = new JButton("Run Backtest");

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets =
            new Insets(
                0,
                0,
                15,
                0
            );

        // Selected stock
        add(selectedStockLabel, gbc);

        // Chart count label
        gbc.gridy++;

        gbc.insets =
            new Insets(
                0,
                0,
                5,
                0
            );

        add(chartCountLabel, gbc);

        // Chart count combo box
        gbc.gridy++;

        gbc.insets =
            new Insets(
                0,
                0,
                15,
                0
            );

        add(chartCountComboBox, gbc);

        // Run button
        gbc.gridy++;

        gbc.insets =
            new Insets(
                0,
                0,
                0,
                0
            );

        add(runButton, gbc);

        gbc.gridy++;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        add(new JPanel(), gbc);
    }


    public void addRunListener(
            ActionListener listener
    ) {
        runButton.addActionListener(listener);
    }

    public void addChartCountListener(
            ActionListener listener
    ) {
        chartCountComboBox.addActionListener(listener);
    }

    public int getChartCount() {

        Integer count =
                (Integer) chartCountComboBox
                        .getSelectedItem();

        if (count == null) {
            return 1;
        }

        return count;
    }

    public String getSelectedStock() {
        return selectedStock;
    }


    public void setSelectedStock(String stock) {

        this.selectedStock = stock;

        selectedStockLabel.setText(
                "Selected stock: " + stock
        );
    }

    public void setRunButtonEnabled(
            boolean enabled
    ) {
        runButton.setEnabled(enabled);
    }
}
