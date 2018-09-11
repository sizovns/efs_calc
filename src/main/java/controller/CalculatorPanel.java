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
    /*
    // координаты текущей ячейки
    private int gridx, gridy;
    // настраиваемый объект GridBagConstraints
    private GridBagConstraints constraints;

    public GridBagConstraints get() {
        return constraints;
    }

    public CalculatorPanel nextCell() {
        constraints = new GridBagConstraints();
        constraints.gridx = gridx++;
        constraints.gridy = gridy;
        // для удобства возвращаем себя
        return this;
    }

    public CalculatorPanel nextRow() {
        gridy++;
        gridx = 0;
        constraints.gridx = 0;
        constraints.gridy = gridy;
        return this;
    }

    public CalculatorPanel span() {
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        return this;
    }

    public CalculatorPanel fillHorizontally() {
        constraints.fill = GridBagConstraints.HORIZONTAL;
        return this;
    }

    // вставляет распорку справа
    public CalculatorPanel gap(int size) {
        constraints.insets.right = size;
        return this;
    }

    public CalculatorPanel spanY() {
        constraints.gridheight = GridBagConstraints.REMAINDER;
        return this;
    }


    public CalculatorPanel fillBoth() {
        constraints.fill = GridBagConstraints.BOTH;
        return this;
    }

    public CalculatorPanel alignLeft() {
        constraints.anchor = GridBagConstraints.LINE_START;
        return this;
    }

    public CalculatorPanel alignRight() {
        constraints.anchor = GridBagConstraints.LINE_END;
        return this;
    }

    public CalculatorPanel setInsets(int left, int top, int right, int bottom) {
        Insets i = new Insets(top, left, bottom, right);
        constraints.insets = i;
        return this;
    }

    public CalculatorPanel setWeights(float horizontal, float vertical) {
        constraints.weightx = horizontal;
        constraints.weighty = vertical;
        return this;
    }

    public void insertEmptyRow(Container c, int height) {
        Component comp = Box.createVerticalStrut(height);
        nextCell().nextRow().fillHorizontally().span();
        c.add(comp, get());
        nextRow();
    }

    public void insertEmptyFiller(Container c) {
        Component comp = Box.createGlue();
        nextCell().nextRow().fillBoth().span().spanY().setWeights(1.0f, 1.0f);
        c.add(comp, get());
        nextRow();
    }*/

    public CalculatorPanel() {
        GridBagLayout gridbag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        setLayout(new BorderLayout());
        start = true;

        display = new JTextArea();
        display.setEnabled(true);
        add(display, BorderLayout.NORTH);

        ActionListener insert = new InsertAction();
        ActionListener command = new CommandAction();

        panel = new JPanel();
        //panel.setLayout(new GridLayout(5, 4));
        panel.setLayout(gridbag);

        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;

        addButton("AC", command, gridbag, c);
        addButton("(", insert, gridbag, c);
        addButton(")", insert, gridbag, c);
        addButton("/", insert, gridbag, c);

        c.gridwidth = GridBagConstraints.REMAINDER;
        c.weightx = 0.0;
        //c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;
        c.gridwidth = 1;                //reset to the default
        c.gridheight = 2;
        addButton("7", insert, gridbag, c);
        addButton("8", insert, gridbag, c);
        addButton("9", insert, gridbag, c);
        addButton("*", insert, gridbag, c);


        c.gridwidth = GridBagConstraints.REMAINDER;
        addButton("4", insert, gridbag, c);
        addButton("5", insert, gridbag, c);
        addButton("6", insert, gridbag, c);
        addButton("-", insert, gridbag, c);


        addButton("1", insert, gridbag, c);
        addButton("2", insert, gridbag, c);
        addButton("3", insert, gridbag, c);
        addButton("+", insert, gridbag, c);


        addButton("0", insert, gridbag, c);
        addButton(".", insert, gridbag, c);
        addButton("=", command, gridbag, c);
        addButton("DEL", command, gridbag, c);


        add(panel, GridBagConstraints.BOTH);


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
