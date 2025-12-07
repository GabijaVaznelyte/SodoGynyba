package org.sodogynyba.ui;

import org.sodogynyba.utils.TriConsumer;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    public MenuPanel(TriConsumer<Integer, Integer, JFrame> startGameCallback, JFrame parentFrame) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Sodo Gynyba", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 40));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.LIGHT_GRAY);

        JButton startButton = new JButton("Start Game");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setMaximumSize(new Dimension(200, 50));
        startButton.setPreferredSize(new Dimension(200, 50));
        startButton.addActionListener(e -> showGameSetup(startGameCallback, parentFrame));

        JButton quitButton = new JButton("Quit Game");
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setMaximumSize(new Dimension(200, 50));
        quitButton.setPreferredSize(new Dimension(200, 50));
        quitButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(Box.createVerticalGlue());
        buttonPanel.add(startButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(quitButton);
        buttonPanel.add(Box.createVerticalGlue());

        add(buttonPanel, BorderLayout.CENTER);
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
            try { numWaves = Integer.parseInt(input); } catch (NumberFormatException ignored) {}
        }

        startGameCallback.accept(pathType, numWaves, parentFrame);
    }
}
