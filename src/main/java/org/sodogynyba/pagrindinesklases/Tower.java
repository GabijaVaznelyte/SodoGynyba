package org.sodogynyba.pagrindinesklases;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;

@Getter
@Setter

public class Tower {
    private int cost;
    private int range;
    private int damage;
    private Point position;
    private int attackCooldown;
    private int cooldownRemaining;

    public Tower(Point position, int cost, int range, int damage, int attackCooldown){
        this.position = position;
        this.cost = cost;
        this.range = range;
        this.damage = damage;
        this.attackCooldown = attackCooldown;
        this.cooldownRemaining = 0;
    }

    public boolean canAttack(){
        return cooldownRemaining <= 0;
    }
    public void updateCooldown(){
        if(cooldownRemaining > 0) {cooldownRemaining -= 1;}
    }

    public boolean isInRange(Enemy enemy){
        int dx = position.x - enemy.getPosition().x;
        int dy = position.y - enemy.getPosition().y;
        double distance = Math.sqrt(dx*dx + dy*dy);

        return distance <= range;
    }
    public void attack(Enemy enemy){
        enemy.takeDamage(damage);
        cooldownRemaining = attackCooldown;
    }
}
