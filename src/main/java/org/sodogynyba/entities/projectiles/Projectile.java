package org.sodogynyba.entities.projectiles;

import lombok.Getter;
import org.sodogynyba.entities.enemies.Enemy;

import java.awt.*;

public abstract class Projectile {
    private Point position;
    private Enemy target;
    private int speed;
    private int damage;
    @Getter
    private boolean active;

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
            double stepX = calculateStep(dx, distance);
            double stepY = calculateStep(dy, distance);
            moveBy(stepX, stepY);
        }
    }
    public void hitTarget() {
        target.takeDamage(damage);
        onHitEffect();
        active = false;
    }
    protected abstract void onHitEffect();
    private void moveBy(double dx, double dy) {
        position.x += (int) dx;
        position.y += (int) dy;
    }
    private double calculateStep(double delta, double distance) {
        return delta / distance * speed;
    }
    protected Enemy getTarget() { return target; }
    public Point getPositionCopy() {
        return new Point(position);
    }
    public int getX() { return position.x; }
    public int getY() { return position.y; }
    public Color getColor() {
        if (this instanceof SlowProjectile) return new Color(255, 165, 0);
        else return Color.ORANGE;
    }
}
