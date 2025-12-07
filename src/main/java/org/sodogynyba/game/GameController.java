package org.sodogynyba.game;

import org.sodogynyba.GameLauncher;
import org.sodogynyba.ui.GamePanel;
import org.sodogynyba.ui.InfoPanel;
import org.sodogynyba.utils.GameConfig;

import javax.swing.*;
import java.awt.*;

public class GameController {

    private final Game game;
    private final GamePanel gamePanel;
    private final InfoPanel infoPanel;
    private final Timer gameTimer;

    public GameController(int pathType, int numWaves) {
        game = new Game(pathType, numWaves);

        gamePanel = new GamePanel(game);
        infoPanel = new InfoPanel(game);

        infoPanel.startWavesButton.addActionListener(e -> startWave());
        infoPanel.addTowerButton.addActionListener(e -> addTower());

        gameTimer = new Timer(200, e -> updateGame());
    }

    public void start(JFrame gameFrame) {
        gameFrame.getContentPane().removeAll();
        gameFrame.setLayout(new BorderLayout());

        gameFrame.add(infoPanel, BorderLayout.NORTH);

        gamePanel.setPreferredSize(new Dimension(GameConfig.BOARD_WIDTH, GameConfig.BOARD_HEIGHT));
        gamePanel.setMinimumSize(new Dimension(GameConfig.BOARD_WIDTH, GameConfig.BOARD_HEIGHT));
        gamePanel.setMaximumSize(new Dimension(GameConfig.BOARD_WIDTH, GameConfig.BOARD_HEIGHT));

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.add(gamePanel);
        gameFrame.add(centerPanel, BorderLayout.CENTER);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);

        gameTimer.start();
    }

    private void updateGame() {
        game.update();
        gamePanel.repaint();
        infoPanel.updateLabels();
        infoPanel.startWavesButton.setEnabled(!game.isWaveActive());

        if (game.isGameOver()) {
            game.clearProjectiles();
            gamePanel.repaint();
            endGame("Game Over");
        } else if (game.isVictory()) {
            game.clearProjectiles();
            gamePanel.repaint();
            endGame("Victory!");
        }
    }

    private void endGame(String message) {
        gameTimer.stop();
        JOptionPane.showMessageDialog(gamePanel, message);
        SwingUtilities.invokeLater(() -> GameLauncher.returnToMenu((JFrame) SwingUtilities.getWindowAncestor(gamePanel)));
    }

    private void startWave() {
        game.startNextWave();
    }

    private void addTower() {
        String[] options = {"Regular Tower", "Slow Tower"};
        String choice = (String) JOptionPane.showInputDialog(
                gamePanel,
                "Select tower type:",
                "Add Tower",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );
        if (choice != null) {
            gamePanel.setSelectedTowerType(choice);
        }
    }
}
