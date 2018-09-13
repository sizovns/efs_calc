package controller;


import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static service.CalculateService.calc;

public class CalculatorPanel extends JPanel {

    private JTextPane display;
    private JPanel panel;
    private boolean start;


    public CalculatorPanel() {
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(new BorderLayout());
        start = true;

        display = new JTextPane();
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setAlignment(attrs, StyleConstants.ALIGN_CENTER);
        StyledDocument doc = (StyledDocument) display.getDocument();
        try {
            doc.insertString(0, "", attrs);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        doc.setParagraphAttributes(0, doc.getLength() - 1, attrs, false);
        add(display, BorderLayout.NORTH);

        ActionListener insert = new InsertAction();
        ActionListener command = new CommandAction();

        panel = new JPanel();
        panel.setLayout(gridbag);


        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1.0;
        c.weighty = 1.0;
        addButton("AC", command, gridbag, c);
        c.gridx = 1;
        addButton("(", insert, gridbag, c);
        c.gridx = 2;
        addButton(")", insert, gridbag, c);
        c.gridx = 3;
        addButton("/", insert, gridbag, c);


        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 1.0;
        c.weighty = 1.0;
        addButton("7", insert, gridbag, c);
        c.gridx = 1;
        addButton("8", insert, gridbag, c);
        c.gridx = 2;
        addButton("9", insert, gridbag, c);
        c.gridx = 3;
        addButton("*", insert, gridbag, c);

        c.gridx = 0;
        c.gridy = 3;
        c.weighty = 1.0;
        c.weightx = 1.0;
        addButton("4", insert, gridbag, c);
        c.gridx = 1;
        addButton("5", insert, gridbag, c);
        c.gridx = 2;
        addButton("6", insert, gridbag, c);
        c.gridx = 3;
        addButton("-", insert, gridbag, c);

        c.gridx = 0;
        c.gridy = 4;
        c.weighty = 1.0;
        c.weightx = 1.0;
        addButton("1", insert, gridbag, c);
        c.gridx = 1;
        addButton("2", insert, gridbag, c);
        c.gridx = 2;
        addButton("3", insert, gridbag, c);
        c.gridx = 3;
        addButton("+", insert, gridbag, c);


        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        c.weighty = 1.0;
        c.weightx = 1.0;
        addButton("0", insert, gridbag, c);
        c.gridx = 2;
        c.gridwidth = 1;
        addButton(".", insert, gridbag, c);

        c.gridx = 3;
        c.gridwidth = 1;
        c.gridheight = 2;
        c.weighty = 1.0;
        addButton("=", command, gridbag, c);

        c.weighty = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = 0;
        c.gridheight = 1;
        c.gridy = 6;
        c.gridwidth = 3;
        addButton("DEL", command, gridbag, c);

        add(panel);


    }

    private void addButton(String label, ActionListener listener, GridBagLayout gridbag, GridBagConstraints c) {
        JButton button = new JButton(label);
        gridbag.setConstraints(button, c);
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
                } catch (NumberFormatException | ArithmeticException ex) {
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
