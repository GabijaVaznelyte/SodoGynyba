package org.sodogynyba.entities.towers;

import lombok.Getter;
import org.sodogynyba.entities.projectiles.BasicProjectile;
import org.sodogynyba.entities.projectiles.Projectile;
import org.sodogynyba.entities.enemies.Enemy;
import org.sodogynyba.entities.projectiles.SlowProjectile;

import java.awt.*;
import java.util.List;

public class Tower {
    @Getter
    private int cost;
    @Getter
    private int damage;
    private int range;
    private Point position;
    @Getter
    private long lastAttackTime;
    private long attackCooldown;

    public Tower(int cost, int damage, int range, int cooldown, Point position) {
        this.cost = cost;
        this.damage = damage;
        this.range = range;
        this.position = position;
        this.attackCooldown =  cooldown;
        lastAttackTime = 0;
    }

    public Projectile tryAttack(List<Enemy> enemies) {
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastAttackTime < attackCooldown){return null;}
        for (Enemy enemy : enemies) {
            if (enemy.isAlive() && isInRange(enemy)) {
                lastAttackTime = currentTime;
                return new BasicProjectile(new Point(position), enemy, 16, damage);
            }
        }
        return null;
    }
    public boolean isInRange(Enemy enemy) {
        if (!enemy.isAlive()) return false;
        return position.distance(enemy.getPositionCopy()) <= range;
    }
    protected void setLastAttackTime(long time){
        this.lastAttackTime = time;
    }
    protected long getAttackCooldown(){
        return attackCooldown;
    }
    public Point getPositionCopy() {
        return new Point(position);
    }
    public int getX() { return position.x; }
    public int getY() { return position.y; }
    public Color getColor() {
        if (this instanceof SlowTower) return new Color(102, 178, 255);
        else return Color.BLUE;
    }
}
