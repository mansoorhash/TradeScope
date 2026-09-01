package org.TradeScope.stockSelector;

import org.TradeScope.backtester.BacktesterGUI;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StockSelectorGUI{
    private JFrame frame;
    private JPanel panel;
    private String selectedStock;
    private JButton button;
    private JLabel label = new JLabel("Select a stock: ");
    private String[] stockList = {"AAPL", "MSFT", "NVDA", "AMZN", "TSLA"};
    private JComboBox<String> dropdown;


    public StockSelectorGUI() {
        frame = new JFrame();
        panel = new JPanel();

        button = new JButton("Select");
        button.addActionListener(e -> {
            new BacktesterGUI(selectedStock);
            frame.dispose();
        });

        dropdown = new JComboBox<>(stockList);
        this.selectedStock = (String) dropdown.getSelectedItem();
        dropdown.addActionListener(e -> {
            this.selectedStock = (String) dropdown.getSelectedItem();
        });

        panel.setBorder(BorderFactory.createEmptyBorder(25, 50, 25, 50));
        panel.setLayout(new GridLayout(0, 1, 0, 1));
        
        panel.add(label);
        panel.add(dropdown);
        panel.add(button);
        
        
        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("TradeScope - Stock Selector");
        frame.setPreferredSize(new java.awt.Dimension(300, 150));
        frame.pack();
        frame.setVisible(true);
    }
} 
