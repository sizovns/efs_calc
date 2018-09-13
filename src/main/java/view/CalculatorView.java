package view;

import controller.CalculatorPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.prefs.Preferences;


public class CalculatorView extends JFrame {

    private static final String PREF_POS_X = "x";
    private static final String PREF_POS_Y = "y";

    private static final String PREF_STATE = "state";

    private Dimension frameSize;
    private Point frameLocation;


    public CalculatorView() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        readSettings();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveSettings();
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                frameSize = getSize();
                System.out.println("Resized: " + frameSize);
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                if ((getExtendedState() & JFrame.MAXIMIZED_BOTH) == 0) {
                    frameLocation = getLocation();
                    System.out.println("Moved: " + frameLocation);
                }
            }
        });


        setTitle("Calculator");
        ImageIcon img = new ImageIcon("src\\main\\resources\\1.png");
        setIconImage(img.getImage());
        add(new CalculatorPanel());
        setVisible(true);
    }

    private void readSettings() {
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        int width = 465;
        int height = 500;
        setSize(width, height);
        frameSize = new Dimension(width, height);
        int x = prefs.getInt(PREF_POS_X, 100);
        int y = prefs.getInt(PREF_POS_Y, 100);
        setLocation(x, y);
        frameLocation = new Point(x, y);
        int state = prefs.getInt(PREF_STATE, JFrame.NORMAL);
        setExtendedState(state);
    }

    private void saveSettings() {
        Preferences prefs = Preferences.userNodeForPackage(getClass());
        prefs.putInt(PREF_POS_X, frameLocation.x);
        prefs.putInt(PREF_POS_Y, frameLocation.y);
        prefs.putInt(PREF_STATE, getExtendedState());
    }

}