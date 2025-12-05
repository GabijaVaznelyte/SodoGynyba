package org.sodogynyba.entities.towers;

import org.sodogynyba.entities.projectiles.Projectile;
import org.sodogynyba.entities.enemies.Enemy;

import java.awt.*;
import java.util.List;

public class SlowTower extends Tower {
    private final double slowAmount;
    private final long slowDuration;

    public SlowTower(Point position) {
        super(30, 3, 50, 1200, position);
        this.slowAmount = 0.5;
        this.slowDuration = 2000;
    }

    @Override
    public Projectile tryAttack(List<Enemy> enemies){
        long currentTime = System.currentTimeMillis();
        if(currentTime - getLastAttackTime() < getAttackCooldown()){
            return null;
        }
        for(Enemy enemy : enemies){
            if(enemy.isAlive() && isInRange(enemy)){
                setLastAttackTime(currentTime);
                return new Projectile(new Point(getPosition()), enemy, 16, getDamage(), slowAmount, slowDuration);
            }
        }
        return null;
    }
}
