package org.sodogynyba.pagrindinesklases;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter

public class Enemy {
    private int health;
    private int speed;
    private int damage;
    private int pathIndex;
    private Point position;
    private boolean isAlive;

    public Enemy(int health, int speed, int damage){
        this.health = health;
        this.speed = speed;
        this.damage = damage;
        isAlive = true;
    }

    public void move(Path path){
        pathIndex++;
        position = path.getTile(pathIndex);
    }
    public void takeDamage(int damage){
        health = health - damage;
        if(health <= 0){
            isAlive = false;
        }
    }
    public boolean reachedEnd(Path path){
        if(pathIndex >= path.getLength() - 1){
            isAlive = false;
            return true;
        }
        return false;
    }
}
