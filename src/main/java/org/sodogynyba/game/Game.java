package org.sodogynyba.game;

import lombok.Getter;
import org.sodogynyba.entities.*;
import org.sodogynyba.path.Path;
import org.sodogynyba.waves.Wave;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Getter

public class Game {
    private Player player;
    private List<Path> paths;
    //private Path path;
    private List<Wave> waves;
    private int currentWave;
    private List<Enemy> activeEnemies;
    private List<Projectile> projectiles;
    private Garden garden;
    private boolean waveActive;

    public Game(int pathType, int numWaves) {
        player = new Player(100);
        activeEnemies = new ArrayList<>();
        waves = new ArrayList<>();
        projectiles = new ArrayList<>();
        currentWave = 0;
        garden = new Garden(2);

        paths = createPath(pathType);
        for(int i = 0; i<numWaves; i++) {
            waves.add(new Wave(i + 1, paths));
        }
    }

    public void startGame() {
        currentWave = 0;
        activeEnemies.clear();
        projectiles.clear();
        waveActive = false;
    }
    public boolean placeTower(Tower tower) {
        for(Path p : paths) {
            for (Point pathPoint : p.getWaypoints()) {
                if (pathPoint.equals(tower.getPosition())) {
                    System.out.println("Cannot place tower on the path!");
                    return false;
                }
            }
        }
        for(Tower t : player.getTowers()) {
            if(t.getPosition().equals(tower.getPosition())) {
                System.out.println("There's already a tower there!");
                return false;
            }
        }
        if(player.canAfford(tower)) {
            player.placeTower(tower);
            System.out.println("Tower placed at " + tower.getPosition() + ". Budget left: " + player.getBudget());
            return true;
        } else {
            System.out.println("Not enough budget to place tower!");
        }
        return false;
    }
    public void startNextWave() {
        if(currentWave >= waves.size()) {
            System.out.println("All waves completed");
            return;
        }
        waveActive = true;
        activeEnemies.clear();
        projectiles.clear();
        waves.get(currentWave).reset();
        System.out.println("Wave " + (currentWave + 1) + " started!");
    }
    public void update() {
        if (currentWave >= waves.size()) return;
        if(!waveActive) {return;}
        Wave wave = waves.get(currentWave);
        wave.updateSpawn(activeEnemies);
        for(Enemy enemy : activeEnemies) {
            if(enemy.isAlive()) {
                enemy.move();
            }
            if(enemy.hasReachedEnd()){
                garden.takeDamage(enemy.getDamage());
                enemy.setAlive(false);
            }
        }
        for (Tower tower : player.getTowers()) {
            Projectile p = tower.tryAttack(activeEnemies);
            if(p != null) {
                projectiles.add(p);
            }
        }
        for(Projectile projectile : projectiles) {
            projectile.update();
        }
        projectiles.removeIf(p -> !p.isActive());
        for(Enemy enemy : activeEnemies) {
            if(!enemy.isAlive()) {
                player.addBudget(enemy.getReward());
            }
        }
        activeEnemies.removeIf(enemy -> !enemy.isAlive());
        if (wave.isWaveCleared(activeEnemies)) {
            System.out.println("Wave " + (currentWave + 1) + " cleared!");
            currentWave++;
            waveActive = false;
        }
    }
    private List<Path> createPath(int type){
        List<Path> paths = new ArrayList<>();
        int blockSize = 16;
        if(type == 1) {
            List<Point> waypoints = new ArrayList<>();
            int x = 5 * blockSize;
            for(int y = 0; y < 20; y++){
                if(y % 4 < 2) x += blockSize;
                else x -= blockSize;
                waypoints.add(new Point(x, y * blockSize));
            }
            paths.add(new Path(waypoints));
        }
        else if(type == 2) {
            List<Point> waypoints1 = new ArrayList<>();
            List<Point> waypoints2 = new ArrayList<>();
            int x1 = 4 * blockSize;
            int x2 = 12 * blockSize;
            for(int y = 0; y < 20; y++){
                if(y % 4 < 2){
                    x1 += blockSize;
                    x2 -= blockSize;
                }
                else{
                    x1 -= blockSize;
                    x2 += blockSize;
                }
                waypoints1.add(new Point(x1, y * blockSize));
                waypoints2.add(new Point(x2, y * blockSize));
            }
            paths.add(new Path(waypoints1));
            paths.add(new Path(waypoints2));
        }
        return paths;
    }
    public List<Tower> getTowers() {
        return player.getTowers();
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
