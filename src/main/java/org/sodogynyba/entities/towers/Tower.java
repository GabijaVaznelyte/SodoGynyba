package org.sodogynyba.entities.towers;

import lombok.Getter;
import org.sodogynyba.entities.projectiles.Projectile;
import org.sodogynyba.entities.enemies.Enemy;

import java.awt.*;
import java.util.List;

public abstract class Tower {
    protected final TowerStats stats;
    protected final Point position;
    @Getter
    protected long lastAttackTime;

    public Tower(TowerStats stats, Point position) {
        this.stats = stats;
        this.position = position;
        this.lastAttackTime = 0;
    }

    public abstract Projectile tryAttack(List<Enemy> enemies);

    public boolean isInRange(Enemy enemy) {
        return enemy.isAlive() && position.distance(enemy.getPositionCopy()) <= stats.range();
    }

    public Point getPositionCopy() {
        return new Point(position);
    }

    public Color getColor() {
        return stats.color();
    }
    public int getCost() {
        return stats.cost();
    }
}
