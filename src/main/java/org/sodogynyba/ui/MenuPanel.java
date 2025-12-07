package org.sodogynyba.ui;

import org.sodogynyba.utils.TriConsumer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {

    private static final int TITLE_FONT_SIZE = 40;
    private static final Dimension BUTTON_SIZE = new Dimension(200, 50);
    private static final int BUTTON_GAP = 20;

    public MenuPanel(TriConsumer<Integer, Integer, JFrame> startGameCallback, JFrame parentFrame) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Sodo Gynyba", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, TITLE_FONT_SIZE));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        JButton startButton = createButton("Start Game", e -> showGameSetup(startGameCallback, parentFrame));
        JButton quitButton = createButton("Quit Game", e -> System.exit(0));

        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, BUTTON_GAP)));
        buttonPanel.add(quitButton);
        buttonPanel.add(Box.createVerticalGlue());

        add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(BUTTON_SIZE);
        button.setPreferredSize(BUTTON_SIZE);
        button.addActionListener(listener);
        return button;
    }
    private void showGameSetup(TriConsumer<Integer, Integer, JFrame> startGameCallback, JFrame parentFrame) {
        String pathChoice = (String) JOptionPane.showInputDialog(
                parentFrame, "Enter number of paths:", "Path Selection",
                JOptionPane.PLAIN_MESSAGE, null, new String[]{"1 Path", "2 Paths"}, "1 Path");
        if (pathChoice == null) return;
        int pathType = pathChoice.equals("1 Path") ? 1 : 2;

        int numWaves = 0;
        while (numWaves <= 0 || numWaves > 10) {
            String input = JOptionPane.showInputDialog(parentFrame,
                    "Enter number of Waves (1-10):", "5");
            if (input == null) return;
            try {
                numWaves = Integer.parseInt(input);
            } catch (NumberFormatException ignored) {}
        }

        startGameCallback.accept(pathType, numWaves, parentFrame);
    }
}
