package org.sodogynyba.pagrindinesklases;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter

public class Tile {
    private final int row;
    private final int col;
    private boolean isPath;
    private boolean isGarden;
    private Tower tower;

    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
        this.isPath = false;
        this.isGarden = false;
        this.tower = null;
    }

    public boolean isEmpty() {
        return !isPath && tower == null;
    }
    public void placeTower(Tower tower) {
        if (isEmpty()) {
            this.tower = tower;
        }
    }
    public void removeTower() {
        this.tower = null;
    }

    public void render(Graphics g, int tileSize) {
        int x = col * tileSize;
        int y = row * tileSize;

        if (isPath) g.setColor(MapGrid.PATH_COLOR);
        else if (isGarden) g.setColor(MapGrid.GARDEN_COLOR);
        else g.setColor(MapGrid.GRASS_COLOR);

        g.fillRect(x, y, tileSize, tileSize);
        g.setColor(Color.BLACK);
        g.drawRect(x, y, tileSize, tileSize);

        if (tower != null) {
            g.setColor(MapGrid.TOWER_COLOR);
            int padding = tileSize / 4;
            g.fillRect(x + padding, y + padding, tileSize / 2, tileSize / 2);
        }
    }
}
