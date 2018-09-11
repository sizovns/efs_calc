package controller;


import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static service.CalculateService.calc;

public class CalculatorPanel extends JPanel {

    private JTextArea display;
    private JPanel panel;
    private boolean start;


    public CalculatorPanel() {
        setLayout(new BorderLayout());
        start = true;

        display = new JTextArea();
        display.setEnabled(true);
        add(display, BorderLayout.NORTH);

        ActionListener insert = new InsertAction();
        ActionListener command = new CommandAction();

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4));

        addButton("AC", command);
        addButton("(", insert);
        addButton(")", insert);
        addButton("/", insert);

        addButton("7", insert);
        addButton("8", insert);
        addButton("9", insert);
        addButton("*", insert);


        addButton("4", insert);
        addButton("5", insert);
        addButton("6", insert);
        addButton("-", insert);

        addButton("1", insert);
        addButton("2", insert);
        addButton("3", insert);
        addButton("+", insert);

        addButton("0", insert);
        addButton(".", insert);
        addButton("=", command);
        addButton("DEL", command);


        add(panel, BorderLayout.CENTER);


    }

    private void addButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        panel.add(button);
    }

    private class CommandAction implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("=")) {
                try {
                    double result = calc(display.getText());
                    String total2 = Double.toString(result);
                    display.setText(total2);
                } catch (NumberFormatException ex) {
                    display.setText("ERROR! Only math working here!! Press AC to clean this window");
                                    }
            } else if (command.equals("AC")) {
                display.setText("0");
            } else if (command.equals("DEL")) {
                String str = display.getText();
                if (!(str.length() <= 1)) {
                    int lastIndex = str.length() - 1;
                    display.setText(str.substring(0, lastIndex));
                } else {
                    display.setText("0");
                }
            }
        }
    }

    private class InsertAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String input = e.getActionCommand();
            if (start) {
                display.setText("");
                start = false;
            }
            display.setText(display.getText() + input);
        }
    }

}
