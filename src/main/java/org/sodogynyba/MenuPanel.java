package org.sodogynyba;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private JLabel titleLabel;
    private JButton startButton;
    private JButton quitButton;

    public MenuPanel(JFrame frame, GamePanel gamePanel) {
        titleLabel = new JLabel("Sodo Gynyba");
        startButton = new JButton("Start");
        quitButton = new JButton("Quit");

        startButton.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.add(gamePanel);
            frame.revalidate();
            frame.repaint();

        });
        quitButton.addActionListener(e -> {
            System.exit(0);
        });

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(startButton);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(quitButton);
    }
}
