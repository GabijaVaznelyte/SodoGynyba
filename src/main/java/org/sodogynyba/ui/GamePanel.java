package org.sodogynyba.ui;

import lombok.Setter;
import org.sodogynyba.entities.enemies.Enemy;
import org.sodogynyba.entities.projectiles.Projectile;
import org.sodogynyba.entities.towers.Tower;
import org.sodogynyba.entities.towers.TowerFactory;
import org.sodogynyba.game.Game;
import org.sodogynyba.utils.GameConfig;
import org.sodogynyba.utils.colors.GameColors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GamePanel extends JPanel {
    private final Game game;
    @Setter
    private String selectedTowerType;

    public GamePanel(Game game) {
        this.game = game;
        this.selectedTowerType = null;

        setBackground(GameColors.BACKGROUND);
        addMouseListener(new TowerPlacer());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int xOffset = (getWidth() - GameConfig.BOARD_WIDTH) / 2;
        int yOffset = (getHeight() - GameConfig.BOARD_HEIGHT) / 2;

        drawBackground(g, xOffset, yOffset);
        drawPaths(g, xOffset, yOffset);
        drawGarden(g, xOffset, yOffset);
        drawGrid(g, xOffset, yOffset);
        drawTowers(g, xOffset, yOffset);
        drawEnemies(g, xOffset, yOffset);
        drawProjectiles(g, xOffset, yOffset);
    }
    private void drawBackground(Graphics g, int xOffset, int yOffset) {
        g.setColor(GameColors.BACKGROUND);
        g.fillRect(xOffset, yOffset, GameConfig.BOARD_WIDTH, GameConfig.BOARD_HEIGHT);
    }
    private void drawPaths(Graphics g, int xOffset, int yOffset) {
        g.setColor(GameColors.PATH);
        for (var path : game.getPaths()) {
            for (Point p : path.getWaypointsCopy()) {
                g.fillRect(xOffset + p.x, yOffset + p.y, GameConfig.BLOCK_SIZE, GameConfig.BLOCK_SIZE);
            }
        }
    }
    private void drawGarden(Graphics g, int xOffset, int yOffset) {
        g.setColor(GameColors.GARDEN);
        g.fillRect(xOffset, yOffset + (GameConfig.GRID_ROWS - 1) * GameConfig.BLOCK_SIZE,
                GameConfig.BOARD_WIDTH, GameConfig.BLOCK_SIZE);
        g.setColor(Color.WHITE);
        g.drawString("GARDEN", xOffset + 5, yOffset + (GameConfig.GRID_ROWS * GameConfig.BLOCK_SIZE) - 4);
    }
    private void drawGrid(Graphics g, int xOffset, int yOffset) {
        g.setColor(GameColors.GRID_LINES);
        for (int i = 0; i <= GameConfig.GRID_COLS; i++)
            g.drawLine(xOffset + i * GameConfig.BLOCK_SIZE, yOffset,
                    xOffset + i * GameConfig.BLOCK_SIZE, yOffset + GameConfig.BOARD_HEIGHT);
        for (int i = 0; i <= GameConfig.GRID_ROWS; i++)
            g.drawLine(xOffset, yOffset + i * GameConfig.BLOCK_SIZE,
                    xOffset + GameConfig.BOARD_WIDTH, yOffset + i * GameConfig.BLOCK_SIZE);
    }
    private void drawTowers(Graphics g, int xOffset, int yOffset) {
        for (Tower tower : game.getTowers()) {
            Point p = tower.getPositionCopy();
            g.setColor(tower.getColor());
            g.fillRect(xOffset + p.x, yOffset + p.y, GameConfig.BLOCK_SIZE, GameConfig.BLOCK_SIZE);
        }
    }
    private void drawEnemies(Graphics g, int xOffset, int yOffset) {
        for (Enemy enemy : game.getActiveEnemies()) {
            if (!enemy.isAlive()) continue;
            g.setColor(enemy.getColor());
            Point p = enemy.getPositionCopy();
            g.fillRect(xOffset + p.x, yOffset + p.y, GameConfig.BLOCK_SIZE, GameConfig.BLOCK_SIZE);
        }
    }
    private void drawProjectiles(Graphics g, int xOffset, int yOffset) {
        for (Projectile projectile : game.getProjectiles()) {
            if (projectile.isActive()) {
                g.setColor(projectile.getColor());
                Point p = projectile.getPositionCopy();
                g.fillOval(xOffset + p.x, yOffset + p.y, 6, 6);
            }
        }
    }

    private class TowerPlacer extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (selectedTowerType == null) return;

            Point clickPoint = calculateClickPosition(e);
            if (clickPoint == null) return;

            Tower tower = selectedTowerType.equals("Regular Tower")
                    ? TowerFactory.createTower(TowerFactory.REGULAR, clickPoint)
                    : TowerFactory.createTower(TowerFactory.SLOW, clickPoint);

            if (game.placeTower(tower)) {
                selectedTowerType = null;
            }
            repaint();
        }

        private Point calculateClickPosition(MouseEvent e) {
            int xOffset = (getWidth() - GameConfig.BOARD_WIDTH) / 2;
            int yOffset = (getHeight() - GameConfig.BOARD_HEIGHT) / 2;

            int col = (e.getX() - xOffset) / GameConfig.BLOCK_SIZE;
            int row = (e.getY() - yOffset) / GameConfig.BLOCK_SIZE;

            if (col < 0 || col >= GameConfig.GRID_COLS || row < 0 || row >= GameConfig.GRID_ROWS) {
                return null;
            }

            return new Point(col * GameConfig.BLOCK_SIZE, row * GameConfig.BLOCK_SIZE);
        }
    }
}
