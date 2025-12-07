package org.sodogynyba.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sodogynyba.TestUtils;
import org.sodogynyba.entities.enemies.Enemy;
import org.sodogynyba.entities.projectiles.Projectile;
import org.sodogynyba.entities.towers.Tower;
import org.sodogynyba.game.Game;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.sodogynyba.TestUtils.createTowerNextToFirstPath;

class ProjectileTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(1, 1);
    }

    @Test
    void testProjectileMovesAndHitsTarget() {
        Tower tower = TestUtils.createTowerNextToFirstPath(game);

        game.startNextWave();

        while (!game.getActiveEnemies().isEmpty()) {
            game.update();
        }
        game.update();

        assertFalse(game.getProjectiles().isEmpty());
        Projectile proj = game.getProjectiles().get(0);

        Point posBefore = proj.getPositionCopy();
        game.update();
        Point posAfter = proj.getPositionCopy();

        double dx = proj.getTarget().getX() - posBefore.x;
        double dy = proj.getTarget().getY() - posBefore.y;
        double distanceBefore = Math.hypot(dx, dy);

        dx = proj.getTarget().getX() - posAfter.x;
        dy = proj.getTarget().getY() - posAfter.y;
        double distanceAfter = Math.hypot(dx, dy);

        assertTrue(distanceAfter < distanceBefore);

        Enemy target = proj.getTarget();
        int healthBefore = target.getHealth();

        while (proj.isActive()) {
            game.update();
        }

        assertFalse(proj.isActive());

        int healthAfter = target.getHealth();
        assertTrue(healthAfter < healthBefore);
    }
    @Test
    void testProjectileListClearedAfterWaveEnd() {
        createTowerNextToFirstPath(game);
        game.startNextWave();
        game.update();
        assertFalse(game.getProjectiles().isEmpty());

        game.clearProjectiles();
        assertTrue(game.getProjectiles().isEmpty());
    }
}