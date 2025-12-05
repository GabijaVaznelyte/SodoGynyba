package org.sodogynyba.entities.projectiles;

import lombok.Getter;
import org.sodogynyba.entities.enemies.Enemy;

import java.awt.*;

@Getter

public class Projectile {
    private Point position;
    private Enemy target;
    private int speed;
    private int damage;
    private boolean active;
    private double slowAmount = 0;
    private long slowDuration = 0;

    public Projectile(Point startPosition, Enemy target,  int speed, int damage) {
        this.position = new Point(startPosition);
        this.target = target;
        this.speed = speed;
        this.damage = damage;
        this.active = true;
    }

    public Projectile(Point startPosition, Enemy target, int speed, int damage, double slowAmount, long slowDuration) {
        this(startPosition, target, speed, damage);
        this.slowAmount = slowAmount;
        this.slowDuration = slowDuration;
    }

    public void update(){
        if(target == null || !target.isAlive()){
            active = false;
            return;
        }
        double dx = target.getX() - position.x;
        double dy = target.getY() - position.y;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if(distance < speed){
            hitTarget();
        }
        else{
            position.x += (int) (dx / distance * speed);
            position.y += (int) (dy / distance * speed);
        }
    }
    public void hitTarget(){
        target.takeDamage(damage);
        if(slowAmount > 0 && slowDuration > 0){
            target.applySlow(slowAmount, slowDuration);
        }
        active = false;
    }
}
