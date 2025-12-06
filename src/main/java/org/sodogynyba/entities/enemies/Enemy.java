package org.sodogynyba.entities.enemies;

import lombok.Getter;
import lombok.Setter;
import org.sodogynyba.path.Path;

import java.awt.*;

public class Enemy {
    // --- Core Stats ---
    private final EnemyStats stats;
    @Getter
    private final int damage;
    @Getter
    private final int reward;
    private final int baseSpeed;
    // --- State ---
    @Getter
    private int speed;
    private int health;
    @Getter
    private boolean alive = true;
    // --- Position & Path ---
    private final Point position;
    private final Path path;
    private int pathIndex = 0;
    // --- Slow Effect ---
    private double slowAmount = 0;
    private long slowEndTime = 0;
    // --- Observer Pattern ---
    @Setter
    private EnemyListener listener;

    public Enemy(EnemyStats stats, Path path) {
        this.stats = stats;
        this.path = path;
        this.health = stats.health();
        this.damage = stats.damage();
        this.baseSpeed = stats.speed();
        this.speed = stats.speed();
        this.reward = stats.reward();
        Point start = path.getWaypoint(0);
        this.position = new Point(start);
    }

    public void move() {
        if (!alive) return;

        updateSlowStatus();

        Point target = getCurrentTarget();
        if (target == null) return;

        double dx = target.x - position.x;
        double dy = target.y - position.y;
        double distance = Math.hypot(dx, dy);

        if (distance <= speed) {
            snapToTarget(target);
            processWaypointReached();
        } else {
            moveToward(dx, dy, distance);
        }
    }
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            alive = false;
        }
    }
    public void applySlow(double amount, long duration) {
        if (amount > slowAmount) {
            slowAmount = amount;
            slowEndTime = System.currentTimeMillis() + duration;
            speed = (int) (baseSpeed * (1 - slowAmount));
        }
    }
    public boolean hasReachedEnd() {
        return pathIndex >= path.getLength();
    }
    public Point getPositionCopy() {
        return new Point(position);
    }
    public int getX() { return position.x; }
    public int getY() { return position.y; }
    public Color getColor() {
        return stats.color();
    }
    // --- Private helpers ---
    private void updateSlowStatus() {
        if (slowAmount > 0 && System.currentTimeMillis() > slowEndTime) {
            slowAmount = 0;
            speed = baseSpeed;
        }
    }
    private Point getCurrentTarget() {
        return path.getWaypoint(pathIndex);
    }
    private void snapToTarget(Point target) {
        position.x = target.x;
        position.y = target.y;
    }
    private void processWaypointReached() {
        pathIndex++;
        if (hasReachedEnd()) reachEnd();
    }
    private void moveToward(double dx, double dy, double distance) {
        position.x += (int) (speed * dx / distance);
        position.y += (int) (speed * dy / distance);
    }
    private void reachEnd() {
        alive = false;
        if (listener != null) listener.onEnemyReachedEnd(this);
    }
}