package org.sodogynyba.entities.projectiles;

import org.sodogynyba.entities.enemies.Enemy;
import org.sodogynyba.utils.colors.ProjectileColors;

import java.awt.*;

public final class BasicProjectile extends Projectile {

    public BasicProjectile(Point startPosition, Enemy target, int speed, int damage) {
        super(startPosition, target, speed, damage, ProjectileColors.BASIC);
    }

    @Override
    protected void onHitEffect() {
        // Basic projectile has no additional effects
    }
}
