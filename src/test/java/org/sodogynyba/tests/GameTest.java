package org.sodogynyba.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sodogynyba.game.Game;
import org.sodogynyba.utils.GameConfig;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.sodogynyba.TestUtils.createTowerNextToFirstPath;

class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(1, 3);
    }

    @Test
    void testGameInitialization() {
        assertEquals(GameConfig.INITIAL_BUDGET, game.getBudget());
        assertEquals(GameConfig.GARDEN_HEALTH, game.getGarden().getHealth());
        assertTrue(game.getActiveEnemies().isEmpty());
        assertTrue(game.getProjectiles().isEmpty());
        assertEquals(3, game.getTotalWaves());
        assertFalse(game.isWaveActive());
    }
    @Test
    void testWaveActivation() {
        assertFalse(game.isWaveActive());
        game.startNextWave();
        assertTrue(game.isWaveActive());
    }
    @Test
    void testUpdateMovesEnemiesAndHandlesProjectiles() {
        createTowerNextToFirstPath(game);
        game.startNextWave();
        game.update();
        assertFalse(game.getActiveEnemies().isEmpty());
        assertFalse(game.getProjectiles().isEmpty());
        Point posBefore = game.getActiveEnemies().get(0).getPositionCopy();
        game.update();
        Point posAfter = game.getActiveEnemies().get(0).getPositionCopy();
        assertNotEquals(posBefore, posAfter);
    }
}
