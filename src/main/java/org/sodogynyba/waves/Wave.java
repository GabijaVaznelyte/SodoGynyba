package org.sodogynyba.waves;

import lombok.Setter;
import org.sodogynyba.entities.enemies.*;
import org.sodogynyba.paths.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Wave {
    private static final int BASE_SPAWN_INTERVAL = 20;
    private static final int BASE_ENEMY_COUNT = 2;
    private static final int BASIC_ENEMY_CHANCE_WAVE_2 = 70;
    private static final int BASIC_ENEMY_CHANCE_WAVE_4 = 50;
    private static final int FAST_ENEMY_CHANCE_WAVE_4 = 80;

    private final int waveNumber;
    private final List<Path> paths;
    private final Random random = new Random();
    private final List<Enemy> spawnedEnemies = new ArrayList<>();

    private final int spawnInterval;
    private int spawnCooldown;
    private final int totalEnemies;
    private int spawnedCount;

    public interface WaveListener { void onEnemySpawned(Enemy enemy); }

    @Setter
    private WaveListener listener;

    public Wave(int waveNumber, List<Path> paths) {
        this.waveNumber = waveNumber;
        this.paths = paths;
        this.spawnInterval = BASE_SPAWN_INTERVAL;
        this.spawnCooldown = 0;
        this.totalEnemies = waveNumber + BASE_ENEMY_COUNT;
        this.spawnedCount = 0;
    }

    public void updateSpawn() {
        if (allSpawned()) return;
        if (spawnCooldown > 0) { spawnCooldown--; return; }

        Enemy enemy = createEnemyForWave();
        spawnedEnemies.add(enemy);
        spawnedCount++;
        spawnCooldown = spawnInterval;

        if (listener != null) listener.onEnemySpawned(enemy);
    }
    private Enemy createEnemyForWave() {
        Path path = chooseRandomPath();
        EnemyStats stats = chooseEnemyStats();
        return EnemyFactory.createEnemy(stats, path);
    }
    private Path chooseRandomPath() {
        return paths.get(random.nextInt(paths.size()));
    }
    private EnemyStats chooseEnemyStats() {
        int chance = random.nextInt(100);
        if (waveNumber < 2) return EnemyFactory.BASIC;
        if (waveNumber < 4) return chance < BASIC_ENEMY_CHANCE_WAVE_2 ? EnemyFactory.BASIC : EnemyFactory.FAST;
        if (chance < BASIC_ENEMY_CHANCE_WAVE_4) return EnemyFactory.BASIC;
        if (chance < FAST_ENEMY_CHANCE_WAVE_4) return EnemyFactory.FAST;
        return EnemyFactory.TANK;
    }
    public boolean allSpawned() {
        return spawnedCount >= totalEnemies;
    }
    public boolean isWaveCleared() {
        return allSpawned() && spawnedEnemies.stream().noneMatch(Enemy::isAlive);
    }
    public void reset() {
        spawnCooldown = 0;
        spawnedCount = 0;
        spawnedEnemies.clear();
    }
}
