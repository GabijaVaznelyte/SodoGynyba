package org.sodogynyba.entities.projectiles;

import org.sodogynyba.entities.enemies.Enemy;
import org.sodogynyba.utils.colors.ProjectileColors;

import java.awt.*;

public final class SlowProjectile extends Projectile {
    private final double slowAmount;
    private final long slowDuration;

    public SlowProjectile(Point startPosition, Enemy target, int speed, int damage,
                          double slowAmount, long slowDuration) {
        super(startPosition, target, speed, damage, ProjectileColors.SLOW);
        this.slowAmount = slowAmount;
        this.slowDuration = slowDuration;
    }

    @Override
    protected void onHitEffect() {
        getTarget().applySlow(slowAmount, slowDuration);
    }
}
