package org.sodogynyba.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sodogynyba.entities.towers.Tower;
import org.sodogynyba.entities.towers.TowerFactory;
import org.sodogynyba.game.Game;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.sodogynyba.TestUtils.createTowerNextToFirstPath;

class TowerTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(1, 1);
    }

    @Test
    void testTowerCanBePlacedAtValidPoints() {
        Tower tower = createTowerNextToFirstPath(game);
        assertEquals(1, game.getTowers().size());
    }
    @Test
    void testTowerCannotBePlacedOnPath() {
        Point pathTile = game.getPaths().get(0).getWaypoint(0);
        Tower tower = TowerFactory.createTower(TowerFactory.REGULAR, pathTile);
        boolean placed = game.placeTower(tower);
        assertFalse(placed);
        assertTrue(game.getTowers().isEmpty());
    }
    @Test
    void testTowerCreatesProjectileWhenEnemyInRange() {
        createTowerNextToFirstPath(game);
        game.startNextWave();
        game.update();
        assertFalse(game.getProjectiles().isEmpty());
    }
}
