package org.sodogynyba.entities.enemies;

import lombok.Getter;
import lombok.Setter;
import org.sodogynyba.path.Path;

import java.awt.*;

public class Enemy {
    private int health;
    @Getter
    private int damage;
    private int baseSpeed;
    private int speed;
    @Getter
    private int reward;
    private Point position;
    @Getter
    private boolean alive;
    private Path path;
    private int pathIndex;
    private double slowAmount = 0;
    private double slowEndTime = 0;
    @Setter
    private EnemyListener listener;

    public Enemy(int health, int damage, int speed, int reward, Path path) {
        this.health = health;
        this.damage = damage;
        this.baseSpeed = speed;
        this.speed = speed;
        this.reward = reward;
        this.path = path;
        this.pathIndex = 0;

        Point start = path.getWaypoint(pathIndex);
        this.position = new Point(start);
        this.alive = true;
    }

    public void move() {
        if (!alive) return;

        if (slowAmount > 0 && System.currentTimeMillis() > slowEndTime) {
            slowAmount = 0;
            speed = baseSpeed;
        }

        Point target = path.getWaypoint(pathIndex);
        if (target == null) return;

        double dx = target.x - position.x;
        double dy = target.y - position.y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance <= speed) {
            position.x = target.x;
            position.y = target.y;
            pathIndex++;
            if (hasReachedEnd()) {
                reachEnd();
            }
        } else {
            position.x += (int) (speed * dx / distance);
            position.y += (int) (speed * dy / distance);
        }
    }
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0){
            health = 0;
            alive = false;
        }
    }
    public void applySlow(double amount, long duration){
        if(amount > slowAmount){
            slowAmount = amount;
            slowEndTime = System.currentTimeMillis() + duration;
            speed = (int)(baseSpeed * (1 - slowAmount));
        }
    }
    public boolean hasReachedEnd(){
        return pathIndex >= path.getLength();
    }
    private void reachEnd() {
        alive = false;
        if (listener != null) listener.onEnemyReachedEnd(this);
    }
    public Point getPositionCopy() {
        return new Point(position);
    }
    public int getX() { return position.x; }
    public int getY() { return position.y; }
    public Color getColor() {
        if (this instanceof FastEnemy) return new Color(255, 102, 102);
        else if (this instanceof TankEnemy) return new Color(128, 0, 0);
        else return Color.RED;
    }
}
