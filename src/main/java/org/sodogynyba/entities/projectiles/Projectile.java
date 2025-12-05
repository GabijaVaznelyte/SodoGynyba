package org.sodogynyba.entities.projectiles;

import lombok.Getter;
import org.sodogynyba.entities.enemies.Enemy;

import java.awt.*;

@Getter

public abstract class Projectile {
    protected Point position;
    protected Enemy target;
    protected int speed;
    protected int damage;
    protected boolean active;

    public Projectile(Point startPosition, Enemy target, int speed, int damage) {
        this.position = new Point(startPosition);
        this.target = target;
        this.speed = speed;
        this.damage = damage;
        this.active = true;
    }

    public void update() {
        if (target == null || !target.isAlive()) {
            active = false;
            return;
        }

        double dx = target.getX() - position.x;
        double dy = target.getY() - position.y;
        double distance = Math.hypot(dx, dy);

        if (distance < speed) {
            hitTarget();
        } else {
            position.x += (int) (dx / distance * speed);
            position.y += (int) (dy / distance * speed);
        }
    }

    public void hitTarget() {
        target.takeDamage(damage);
        onHitEffect();     // << This is new
        active = false;
    }

    protected abstract void onHitEffect();  // << Subclasses override this

    public boolean isActive() {
        return active;
    }
}
