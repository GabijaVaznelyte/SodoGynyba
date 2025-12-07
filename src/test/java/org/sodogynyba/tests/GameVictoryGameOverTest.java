package org.sodogynyba.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sodogynyba.entities.enemies.Enemy;
import org.sodogynyba.game.Game;
import org.sodogynyba.utils.GameConfig;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameVictoryGameOverTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(1, 2);
    }

    @Test
    void testGameVictoryWhenAllWavesClearedWithoutTestEnemy() {
        for (int i = 0; i < game.getTotalWaves(); i++) {
            game.startNextWave();

            while (!game.getActiveEnemies().isEmpty() || game.getCurrentWave() <= i + 1) {
                for (Enemy enemy : game.getActiveEnemies()) {
                    enemy.takeDamage(enemy.getHealth());
                }
                game.update();
            }
        }
        assertTrue(game.isVictory());
    }
    @Test
    void testGameOverWhenGardenDestroyed() {
        game.getGarden().takeDamage(GameConfig.GARDEN_HEALTH);
        assertTrue(game.isGameOver());
    }
    @Test
    void testWaveCounterDoesNotExceedTotalWaves() {
        int totalWaves = game.getTotalWaves();

        for (int i = 0; i < totalWaves + 5; i++) {
            game.startNextWave();
        }

        assertEquals(totalWaves, game.getCurrentWave() + 1);
    }
}
