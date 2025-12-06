package org.sodogynyba.gameLogic;

import lombok.Getter;
import org.sodogynyba.entities.*;
import org.sodogynyba.entities.enemies.Enemy;
import org.sodogynyba.entities.projectiles.Projectile;
import org.sodogynyba.entities.towers.Tower;
import org.sodogynyba.paths.Path;
import org.sodogynyba.paths.PathFactory;
import org.sodogynyba.waves.Wave;
import org.sodogynyba.waves.WaveFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    private static final int BLOCK_SIZE = 16;
    private static final int PATH_HEIGHT = 20;
    private static final int START_X_PATH1 = 5;
    private static final int START_X_PATH2_1 = 4;
    private static final int START_X_PATH2_2 = 12;
    private static final int INITIAL_BUDGET = 100;
    private static final int GARDEN_HEALTH = 2;

    private final Player player;
    private final List<Path> paths;
    private final List<Wave> waves;
    private int currentWave;
    private final List<Enemy> activeEnemies;
    private final List<Projectile> projectiles;
    @Getter
    private final Garden garden;
    @Getter
    private boolean waveActive;

    public Game(int pathType, int numWaves) {
        player = new Player(INITIAL_BUDGET);
        activeEnemies = new ArrayList<>();
        projectiles = new ArrayList<>();
        currentWave = 0;
        garden = new Garden(GARDEN_HEALTH);

        paths = PathFactory.createPaths(pathType);
        waves = WaveFactory.createWaves(numWaves, paths);
    }

    public void startGame() {
        currentWave = 0;
        activeEnemies.clear();
        projectiles.clear();
        waveActive = false;
    }
    // --- Update and it's helpers ---
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
            System.out.println("Cannot place tower on the path!");
            return false;
        }
        if (!canPlaceTowerOnOtherTowers(tower)) {
            System.out.println("There's already a tower there!");
            return false;
        }
        if (player.placeTower(tower)) {
            System.out.println("Tower placed at " + tower.getPositionCopy() + ". Budget left: " + player.getBudget());
            return true;
        } else {
            System.out.println("Not enough budget to place tower!");
            return false;
        }
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
        if (currentWave >= waves.size()) {
            System.out.println("All waves completed");
            return;
        }

        waveActive = true;
        clearActiveObjects();
        setupCurrentWave();
        System.out.println("Wave " + (currentWave + 1) + " started!");
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
    public boolean isGameOver() {
        return garden.isDestroyed();
    }
    public boolean isVictory() {
        return currentWave >= waves.size() && activeEnemies.isEmpty();
    }
}
