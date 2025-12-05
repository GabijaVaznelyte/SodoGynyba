package org.sodogynyba.waves;

import lombok.Getter;
import org.sodogynyba.entities.enemies.Enemy;
import org.sodogynyba.entities.enemies.FastEnemy;
import org.sodogynyba.entities.enemies.BasicEnemy;
import org.sodogynyba.entities.enemies.TankEnemy;
import org.sodogynyba.path.Path;

import java.util.List;
import java.util.Random;

@Getter

public class Wave {
    private int waveNumber;
    private List<Path> paths;
    private int spawnInterval;
    private int spawnCooldown;
    private int totalEnemies;
    private int spawnedCount;
    private Random random;

    public Wave(int waveNumber, List<Path> paths) {
        this.waveNumber = waveNumber;
        this.paths = paths;
        this.spawnInterval = 20;
        this.spawnCooldown = 0;
        this.totalEnemies = waveNumber + 2;
        this.spawnedCount = 0;
        this.random = new Random();
    }

    public Enemy updateSpawn() {
        if(spawnedCount >= totalEnemies) return null;
        if (spawnCooldown > 0) {
            spawnCooldown--;
            return null;
        }
        Enemy enemy = createEnemyForWave();
        spawnedCount++;
        spawnCooldown = spawnInterval;
        return enemy;
    }
    private Enemy createEnemyForWave(){
        Path chosenPath = paths.get(random.nextInt(paths.size()));
        int typeChance = random.nextInt(100);
        if(waveNumber < 2){
            return new BasicEnemy(chosenPath);
        }else if(waveNumber < 4){
            if(typeChance < 70) return new BasicEnemy(chosenPath);
            else return new FastEnemy(chosenPath);
        }else {
            if(typeChance < 50) return new BasicEnemy(chosenPath);
            else if(typeChance < 80) return new FastEnemy(chosenPath);
            else return new TankEnemy(chosenPath);
        }
    }
    public boolean allSpawned() {
        return spawnedCount >= totalEnemies;
    }
    public boolean isWaveCleared(List<Enemy> activeEnemies) {
        return allSpawned() && activeEnemies.isEmpty();
    }
    public void reset() {
        this.spawnCooldown = 0;
        this.spawnedCount = 0;
    }
}
