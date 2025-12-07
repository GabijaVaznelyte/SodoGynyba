package org.sodogynyba.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sodogynyba.TestUtils;
import org.sodogynyba.entities.enemies.Enemy;
import org.sodogynyba.entities.enemies.EnemyFactory;
import org.sodogynyba.game.Game;
import org.sodogynyba.paths.Path;
import org.sodogynyba.utils.GameConfig;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EnemyTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(1, 3);
    }

    @Test
    void testEnemyTakesDamage() {
        Path path = game.getPaths().get(0);
        TestUtils.TestEnemy enemy = new TestUtils.TestEnemy(EnemyFactory.BASIC, path);

        int initialHealth = enemy.getHealth();
        enemy.takeDamage(1);

        assertEquals(initialHealth - 1, enemy.getHealth());
    }
    @Test
    void testEnemyDiesWhenHealthZero() {
        Path path = game.getPaths().get(0);

        Enemy enemy = EnemyFactory.createEnemy(EnemyFactory.BASIC, path);
        enemy.takeDamage(enemy.getHealth());

        assertFalse(enemy.isAlive());
    }
    @Test
    void testEnemyReachesGardenReducesHealth() {
        game.startNextWave();

        Path path = game.getPaths().get(0);
        TestUtils.TestEnemy enemy = new TestUtils.TestEnemy(EnemyFactory.BASIC, path);

        enemy.setListener(e -> game.getGarden().takeDamage(e.getDamage()));
        enemy.moveToEnd();

        assertEquals(GameConfig.GARDEN_HEALTH - enemy.getDamage(), game.getGarden().getHealth());
    }
}

