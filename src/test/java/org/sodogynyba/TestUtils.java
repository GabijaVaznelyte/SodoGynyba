package org.sodogynyba;

import org.sodogynyba.entities.enemies.Enemy;
import org.sodogynyba.entities.enemies.EnemyFactory;
import org.sodogynyba.entities.enemies.EnemyStats;
import org.sodogynyba.entities.towers.Tower;
import org.sodogynyba.entities.towers.TowerFactory;
import org.sodogynyba.game.Game;
import org.sodogynyba.paths.Path;
import org.sodogynyba.utils.GameConfig;

import java.awt.*;

public class TestUtils {
    // Reusable test enemy
    public static class TestEnemy extends Enemy {
        public TestEnemy(EnemyStats stats, Path path) {
            super(stats, path);
        }

        public void moveToEnd() {
            super.position = super.path.getWaypoint(super.path.getLength() - 1);
            super.pathIndex = super.path.getLength();
            super.health = 0;
            super.alive = false;
            if (super.listener != null) super.listener.onEnemyReachedEnd(this);
        }
    }
    // Returns a tower placed next to the first path tile
    public static Tower createTowerNextToFirstPath(Game game) {
        Point firstTile = game.getPaths().get(0).getWaypoint(0);
        Point towerPos = new Point(firstTile.x - GameConfig.BLOCK_SIZE, firstTile.y);
        Tower tower = TowerFactory.createTower(TowerFactory.REGULAR, towerPos);
        game.placeTower(tower);
        return tower;
    }
}