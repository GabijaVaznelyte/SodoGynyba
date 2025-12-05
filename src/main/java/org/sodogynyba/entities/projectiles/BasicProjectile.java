package org.sodogynyba.entities.projectiles;

import org.sodogynyba.entities.enemies.Enemy;

import java.awt.*;

public class BasicProjectile extends Projectile {

    public BasicProjectile(Point startPosition, Enemy target, int speed, int damage) {
        super(startPosition, target, speed, damage);
    }

    @Override
    protected void onHitEffect() {
        // No status effects for basic projectile
    }
}
