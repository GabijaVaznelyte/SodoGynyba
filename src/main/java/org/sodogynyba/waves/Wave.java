package org.sodogynyba.waves;

import lombok.Setter;
import org.sodogynyba.entities.enemies.*;
import org.sodogynyba.path.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Wave {
    private int waveNumber;
    private List<Path> paths;
    private int spawnInterval;
    private int spawnCooldown;
    private int totalEnemies;
    private int spawnedCount;
    private Random random;
    private List<Enemy> spawnedEnemies = new ArrayList<>();

    public interface WaveListener {
        void onEnemySpawned(Enemy enemy);
    }

    @Setter
    private WaveListener listener;

    public Wave(int waveNumber, List<Path> paths) {
        this.waveNumber = waveNumber;
        this.paths = paths;
        this.spawnInterval = 20;
        this.spawnCooldown = 0;
        this.totalEnemies = waveNumber + 2;
        this.spawnedCount = 0;
        this.random = new Random();
    }

    public void updateSpawn() {
        if (spawnedCount >= totalEnemies) return;
        if (spawnCooldown > 0) {
            spawnCooldown--;
            return;
        }

        Enemy enemy = createEnemyForWave();
        spawnedEnemies.add(enemy);
        spawnedCount++;
        spawnCooldown = spawnInterval;

        if (listener != null) {
            listener.onEnemySpawned(enemy);
        }
    }
    private Enemy createEnemyForWave() {
        Path chosenPath = paths.get(random.nextInt(paths.size()));
        int typeChance = random.nextInt(100);

        EnemyStats stats;
        if (waveNumber < 2) {
            stats = EnemyFactory.BASIC;
        } else if (waveNumber < 4) {
            stats = typeChance < 70 ? EnemyFactory.BASIC : EnemyFactory.FAST;
        } else {
            if (typeChance < 50) stats = EnemyFactory.BASIC;
            else if (typeChance < 80) stats = EnemyFactory.FAST;
            else stats = EnemyFactory.TANK;
        }

        return EnemyFactory.createEnemy(stats, chosenPath);
    }
    public boolean allSpawned() {
        return spawnedCount >= totalEnemies;
    }
    public boolean isWaveCleared(List<Enemy> activeEnemies) {
        return allSpawned() && spawnedEnemies.stream().noneMatch(Enemy::isAlive);
    }
    public void reset() {
        this.spawnCooldown = 0;
        this.spawnedCount = 0;
        this.spawnedEnemies.clear();
    }
}
