package org.sodogynyba.entities.projectiles;

import org.sodogynyba.entities.enemies.Enemy;

import java.awt.*;

public class SlowProjectile extends Projectile {
    private double slowAmount;
    private long slowDuration;

    public SlowProjectile(Point startPosition, Enemy target, int speed, int damage,
                          double slowAmount, long slowDuration) {
        super(startPosition, target, speed, damage);
        this.slowAmount = slowAmount;
        this.slowDuration = slowDuration;
    }

    @Override
    protected void onHitEffect() {
        target.applySlow(slowAmount, slowDuration);
    }
}
