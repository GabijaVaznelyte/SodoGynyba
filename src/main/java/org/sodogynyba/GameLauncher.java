package org.sodogynyba;

import org.sodogynyba.game.GameController;
import org.sodogynyba.ui.MenuPanel;
import org.sodogynyba.utils.GameConfig;

import javax.swing.*;
import java.awt.*;

public class GameLauncher {

    private static GameController controller;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Sodo Gynyba");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);

            showMenu(frame);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    public static void showMenu(JFrame frame) {
        frame.getContentPane().removeAll();
        MenuPanel menu = new MenuPanel(GameLauncher::startGame, frame);
        menu.setPreferredSize(new Dimension(GameConfig.BOARD_WIDTH, GameConfig.BOARD_HEIGHT + 100));
        frame.setContentPane(menu);
        frame.revalidate();
        frame.repaint();
    }

    public static void startGame(int pathType, int numWaves, JFrame frame) {
        controller = new GameController(pathType, numWaves);
        controller.start(frame);
    }

    public static void returnToMenu(JFrame frame) {
        showMenu(frame);
    }
}