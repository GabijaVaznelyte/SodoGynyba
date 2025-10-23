package org.sodogynyba.pagrindinesklases;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter

public class Wave {
    private ArrayList<Enemy> enemies;
    private int spawnInterval;
    private int timeSinceSpawn;
    private int spawnedCount;
    private boolean isFinished;

    public Wave(ArrayList<Enemy> enemies, int spawnInterval){
        this.enemies = enemies;
        this.spawnInterval = spawnInterval;
        this.timeSinceSpawn = 0;
        this.spawnedCount = 0;
    }
    public Enemy spawnNextEnemy(){
        if(spawnedCount <= enemies.size() - 1 && timeSinceSpawn <= 0){
            spawnedCount++;
            timeSinceSpawn = spawnedCount;
            return enemies.get(spawnedCount-1);
        }
        return null;
    }
    public void updateCooldown(){
        if(timeSinceSpawn > 0){
            timeSinceSpawn -= 1;
        }
    }
    public boolean allEnemiesDefeated(){
        for(Enemy enemy :  enemies){
            if(enemy.isAlive()){
                return false;
            }
        }
        return true;
    }
    public boolean isFinished(){
        if(spawnedCount >= enemies.size()){
            isFinished = true;
            return isFinished;
        }
        return false;
    }
}
