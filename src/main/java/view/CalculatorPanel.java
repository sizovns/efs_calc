package view;


import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorPanel extends JPanel {

    private JButton display;
    private JPanel panel;
    private double result;
    private String lastCommand;
    private boolean start;


    public CalculatorPanel() {
        setLayout(new BorderLayout());
        result = 0;
        lastCommand = "=";
        start = true;

        display = new JButton("0");
        display.setEnabled(false);
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
            /*if (start) {
                if (command.equals("-")) {
                    display.setText(command);
                    start = false;
                } else {
                    lastCommand = command;
                }
            } else {
                calculate(Double.parseDouble(display.getText()));
                lastCommand = command;
                start = true;
            }*/


            if (command.equals("=")){
                System.out.println(display.getText());
                //String str = display.getText();
                //str.split("(");
            } else if (command.equals("AC")){
                display.setText("0");
            } else if (command.equals("DEL")){
                String str = display.getText();
                int lastIndex = str.length() - 1;
                display.setText(str.substring(0,lastIndex-1));
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


    public void calculate(double x) {
        if (lastCommand.equals("+")) {
            result += x;
        } else if (lastCommand.equals("-")) {
            result -= x;
        } else if (lastCommand.equals("/")) {
            result /= x;
        } else if (lastCommand.equals("*")) {
            result *= x;
        } else if (lastCommand.equals("=")) {
            result = x;
        } else if (lastCommand.equals("AC")) {
            result = 0;
        }
        display.setText("" + result);
    }

}
