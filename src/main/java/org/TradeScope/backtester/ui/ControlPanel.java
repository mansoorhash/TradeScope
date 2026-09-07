package org.TradeScope.backtester.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {

    private JLabel selectedStockLabel;
    private JComboBox<Integer> chartCountComboBox;
    private JButton fillMissingButton;
    private JButton generateDataButton;
    private JButton runButton;
    private JButton stopButton;
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

        fillMissingButton = new JButton("Fill Missing");
        fillMissingButton.setEnabled(false);
        generateDataButton = new JButton("Generate Data");

        runButton = new JButton("Run Backtest");
        stopButton = new JButton("Stop Backtest");
        stopButton.setEnabled(false);
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
                5,
                0
            );

        add(chartCountComboBox, gbc);

        // Buttons
        JPanel buttonPanel =
            new JPanel(
                new GridLayout(
                    2,
                    2,
                    5,
                    5
                )
            );

        buttonPanel.add(generateDataButton);
        buttonPanel.add(fillMissingButton);
        buttonPanel.add(runButton);
        buttonPanel.add(stopButton);

        gbc.gridy++;

        gbc.insets =
            new Insets(
                0,
                0,
                0,
                0
            );

        add(buttonPanel, gbc);

        //Bottom
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

    public void addFillMissingListener(
            ActionListener listener
    ) {
        fillMissingButton.addActionListener(listener);
    }

    public void addGenerateDataListener(
            ActionListener listener
    ) {
        generateDataButton.addActionListener(listener);
    }

    public void addStopListener(
            ActionListener listener
    ) {
        stopButton.addActionListener(listener);
    }

    public void setStopButtonEnabled(
            boolean enabled
    ) {
        stopButton.setEnabled(enabled);
    }

    public void setFillMissingEnabled(
            boolean enabled
    ) {
        fillMissingButton.setEnabled(enabled);
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


    public void setRunButtonEnabled(
            boolean enabled
    ) {
        runButton.setEnabled(enabled);
    }
}
