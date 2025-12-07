package org.sodogynyba.game;

import lombok.Getter;
import org.sodogynyba.entities.*;
import org.sodogynyba.entities.enemies.Enemy;
import org.sodogynyba.entities.projectiles.Projectile;
import org.sodogynyba.entities.towers.Tower;
import org.sodogynyba.paths.Path;
import org.sodogynyba.paths.PathFactory;
import org.sodogynyba.utils.GameConfig;
import org.sodogynyba.waves.Wave;
import org.sodogynyba.waves.WaveFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    private final Player player;
    private final List<Path> paths;
    private final List<Wave> waves;
    @Getter
    private final int totalWaves;
    private int currentWave;
    private final List<Enemy> activeEnemies;
    private final List<Projectile> projectiles;
    @Getter
    private final Garden garden;
    @Getter
    private boolean waveActive;

    public Game(int pathType, int numWaves) {
        player = new Player(GameConfig.INITIAL_BUDGET);
        activeEnemies = new ArrayList<>();
        projectiles = new ArrayList<>();
        totalWaves = numWaves;
        currentWave = 0;
        garden = new Garden(GameConfig.GARDEN_HEALTH);

        paths = PathFactory.createPaths(pathType);
        waves = WaveFactory.createWaves(numWaves, paths);
    }

    // --- Update ---
    public void update() {
        if (currentWave >= waves.size() || !waveActive) return;

        updateWaveSpawn();
        moveEnemies();
        towersAttack();
        updateProjectiles();
        collectRewards();
        removeDeadEnemies();
        checkWaveCleared();
    }
    private void updateWaveSpawn() {
        waves.get(currentWave).updateSpawn();
    }
    private void moveEnemies() {
        for (Enemy enemy : activeEnemies) {
            if (enemy.isAlive()) enemy.move();
        }
    }
    private void towersAttack() {
        for (Tower tower : player.getTowers()) {
            Projectile p = tower.tryAttack(activeEnemies);
            if (p != null) projectiles.add(p);
        }
    }
    private void updateProjectiles() {
        for (Projectile projectile : projectiles) projectile.update();
        projectiles.removeIf(p -> !p.isActive());
    }
    private void collectRewards() {
        for (Enemy enemy : activeEnemies) {
            if (!enemy.isAlive()) player.addBudget(enemy.getReward());
        }
    }
    private void removeDeadEnemies() {
        activeEnemies.removeIf(enemy -> !enemy.isAlive());
    }
    private void checkWaveCleared() {
        if (waves.get(currentWave).isWaveCleared()) {
            System.out.println("Wave " + (currentWave + 1) + " cleared!");
            currentWave++;
            waveActive = false;
        }
    }
    // --- Place Tower ---
    public boolean placeTower(Tower tower) {
        if (!canPlaceTowerOnPaths(tower)) {
            return false;
        }
        if (!canPlaceTowerOnOtherTowers(tower)) {
            return false;
        }
        return player.placeTower(tower);
    }
    private boolean canPlaceTowerOnPaths(Tower tower) {
        for (Path p : paths) {
            for (Point point : p.getWaypointsCopy()) {
                if (point.equals(tower.getPositionCopy())) return false;
            }
        }
        return true;
    }
    private boolean canPlaceTowerOnOtherTowers(Tower tower) {
        for (Tower t : player.getTowers()) {
            if (t.getPositionCopy().equals(tower.getPositionCopy())) return false;
        }
        return true;
    }
    // --- Next Wave ---
    public void startNextWave() {
        if (currentWave >= waves.size()) return;

        waveActive = true;
        clearActiveObjects();
        setupCurrentWave();
    }
    private void clearActiveObjects() {
        activeEnemies.clear();
        projectiles.clear();
    }
    private void setupCurrentWave() {
        Wave current = waves.get(currentWave);
        current.reset();
        current.setListener(enemy -> {
            enemy.setListener(e -> garden.takeDamage(e.getDamage()));
            activeEnemies.add(enemy);
        });
    }
    // --- Getters ---
    public List<Tower> getTowers() {
        return Collections.unmodifiableList(player.getTowers());
    }
    public List<Path> getPaths() {
        return Collections.unmodifiableList(paths);
    }
    public List<Enemy> getActiveEnemies() {
        return Collections.unmodifiableList(activeEnemies);
    }
    public List<Projectile> getProjectiles() {
        return Collections.unmodifiableList(projectiles);
    }
    public int getBudget() {
        return player.getBudget();
    }
    public int getCurrentWave() {
        return currentWave + 1;
    }
    public void clearProjectiles() {
        projectiles.clear();
    }
    public boolean isGameOver() {
        return garden.isDestroyed();
    }
    public boolean isVictory() {
        return currentWave >= waves.size() && activeEnemies.isEmpty();
    }
}
