package org.sodogynyba.entities.towers;

import org.sodogynyba.entities.enemies.Enemy;
import org.sodogynyba.entities.projectiles.BasicProjectile;
import org.sodogynyba.entities.projectiles.Projectile;

import java.awt.*;
import java.util.List;

public class BasicTower extends Tower {
    public BasicTower(TowerStats stats, Point position) {
        super(stats, position);
    }

    @Override
    public Projectile tryAttack(List<Enemy> enemies) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAttackTime < stats.attackCooldown()) return null;

        for (Enemy enemy : enemies) {
            if (isInRange(enemy)) {
                lastAttackTime = currentTime;
                return new BasicProjectile(new Point(position), enemy, Projectile.DEFAULT_SPEED, stats.damage());
            }
        }
        return null;
    }
}
