package org.sodogynyba.entities;

import lombok.Getter;
import org.sodogynyba.entities.towers.Tower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    @Getter
    private int budget;
    private List<Tower> towers;

    public Player(int initialBudget) {
        this.budget = initialBudget;
        this.towers = new ArrayList<>();
    }

    public boolean canAfford(Tower tower) {
        return budget >= tower.getCost();
    }
    public void placeTower(Tower tower) {
        if (canAfford(tower)) {
            towers.add(tower);
            budget -= tower.getCost();
        }
    }
    public void addBudget(int amount) {
        this.budget += amount;
    }
    public List<Tower> getTowers() {
        return Collections.unmodifiableList(towers);
    }
}
