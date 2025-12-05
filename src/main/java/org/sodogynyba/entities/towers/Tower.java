package org.sodogynyba.entities.towers;

import lombok.Getter;
import lombok.Setter;
import org.sodogynyba.entities.projectiles.Projectile;
import org.sodogynyba.entities.enemies.Enemy;

import java.awt.*;
import java.util.List;

@Getter
@Setter

public class Tower {
    private int cost;
    private int damage;
    private int range;
    private Point position;
    private long lastAttackTime;
    private long attackCooldown;

    public Tower(int cost, int damage, int range, int cooldown, Point position) {
        this.cost = cost;
        this.damage = damage;
        this.range = range;
        this.position = position;
        this.attackCooldown =  cooldown;
        lastAttackTime = 0;
    }

    public Projectile tryAttack(List<Enemy> enemies) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastAttackTime < attackCooldown){return null;}
        for (Enemy enemy : enemies) {
            if (enemy.isAlive() && isInRange(enemy)) {
                lastAttackTime = currentTime;
                return new Projectile(new Point(position), enemy, 16, damage);
            }
        }
        return null;
    }
    public boolean isInRange(Enemy enemy) {
        if (!enemy.isAlive()) return false;
        return position.distance(enemy.getPositionCopy()) <= range;
    }
}
