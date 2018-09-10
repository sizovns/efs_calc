package view;

import javax.swing.*;

public class JFrameApp extends JFrame {

    public JFrameApp() {
        setBounds(100, 100, 465, 500);
        setTitle("Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new CalculatorPanel());
        setVisible(true);
    }
}
