package org.sodogynyba.pagrindinesklases;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    private Player player;
    private Garden garden;
    private Path path;
    private ArrayList<Tower> towers;
    private ArrayList<Enemy> activeEnemies;
    private Wave currentWave;
    private boolean isWaveActive;
    private int waveNumber;

    public Game(){
        player = new Player(500);
        garden = new Garden(500);
        path = new Path(20, 20);
        towers = new ArrayList<>();
        isWaveActive = false;
        waveNumber = 0;
    }

    public void startWave()
    {
        waveNumber++;
        isWaveActive = true;
        Random rand = new Random();
        int randomNumber = rand.nextInt(10, 20);
        activeEnemies = new ArrayList<>();
        for(int i = 0; i < randomNumber; i++){
            activeEnemies.add(new Enemy(3,1,1));
        }
        currentWave = new Wave(activeEnemies, 1);

        update();
    }
    public void update(){
        while(isWaveActive){
            updateEnemies();
            updateTowers();
            if(currentWave.isFinished()) isWaveActive = false;
        }
        player.addMoney(500);
    }
    public void updateEnemies(){
        ArrayList<Enemy> toRemove = new ArrayList<>();
        for(Enemy enemy :  activeEnemies){
            if(!enemy.isAlive()) {
                toRemove.add(enemy);
                continue;
            }
            enemy.move(path);
            if(enemy.getPosition().equals(path.getEndPoint())){
                garden.takeDamage(enemy.getDamage());
                toRemove.add(enemy);
            }
        }
        activeEnemies.removeAll(toRemove);
    }
    public void updateTowers(){
        for(Tower tower : towers){
            tower.updateCooldown();
            for(Enemy enemy : activeEnemies){
                if(tower.isInRange(enemy) && tower.canAttack()){
                    tower.attack(enemy);
                    if(!enemy.isAlive()){
                        player.addMoney(10);
                    }
                    break;
                }
            }
        }
    }
    public void placeTower(Point position){
        if(player.canAfford(100) && !path.isPathTile(position)){
            towers.add(new Tower(position, 100, 20, 1, 1));
        }
    }
    public boolean isGameOver() {
        return !garden.isAlive();
    }
    public boolean isGameWon(){
        return !isWaveActive && waveNumber == 1;
    }
    boolean isPathTile(int row, int col) {
        return path.isPathTile(new Point(col, row));
    }
}
