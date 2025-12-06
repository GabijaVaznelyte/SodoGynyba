package org.sodogynyba.entities.projectiles;

import lombok.Getter;
import org.sodogynyba.entities.enemies.Enemy;

import java.awt.*;

public abstract class Projectile {
    private final Point position;
    private final Enemy target;
    private final int speed;
    private final int damage;
    @Getter
    private boolean active;
    @Getter
    private final Color color;

    public Projectile(Point startPosition, Enemy target, int speed, int damage, Color color) {
        this.position = new Point(startPosition);
        this.target = target;
        this.speed = speed;
        this.damage = damage;
        this.color = color;
        this.active = true;
    }

    public void update() {
        if (!isTargetValid()) return;

        double dx = target.getX() - position.x;
        double dy = target.getY() - position.y;
        double distance = Math.hypot(dx, dy);

        if (isCloseToTarget(distance)) hitTarget();
        else moveToward(dx, dy, distance);
    }
    public void hitTarget() {
        target.takeDamage(damage);
        onHitEffect();
        active = false;
    }
    public Point getPositionCopy() { return new Point(position); }
    // --- Private Helpers ---
    private boolean isTargetValid() {
        if (target == null || !target.isAlive()) {
            active = false;
            return false;
        }
        return true;
    }
    private boolean isCloseToTarget(double distance) {
        return distance < speed;
    }
    private void moveToward(double dx, double dy, double distance) {
        position.x += (int) calculateStep(dx, distance);
        position.y += (int) calculateStep(dy, distance);
    }
    private double calculateStep(double delta, double distance) {
        return delta / distance * speed;
    }
    // --- Protected Helpers ---
    protected abstract void onHitEffect();
    protected Enemy getTarget() { return target; }
}
