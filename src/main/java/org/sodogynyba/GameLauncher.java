package org.sodogynyba;

import javax.swing.*;
import java.awt.*;


public class GameLauncher {
    static void main() {
        JFrame window = new JFrame("Sodo Gynyba");
        GamePanel gamePanel = new GamePanel();
        MenuPanel menuPanel = new MenuPanel(window, gamePanel);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setSize(800, 600);
        window.setVisible(true);

        window.setLayout(new GridBagLayout());
        window.add(menuPanel);
    }
}
