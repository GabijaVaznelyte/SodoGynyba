package org.sodogynyba.entities;

import lombok.Getter;
import org.sodogynyba.entities.towers.Tower;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Player {
    @Getter
    private int budget;
    private final List<Tower> towers = new ArrayList<>();

    public Player(int initialBudget) {
        this.budget = initialBudget;
    }

    private boolean spend(int amount) {
        if (amount > budget) return false;
        budget -= amount;
        return true;
    }
    public boolean placeTower(Tower tower) {
        int cost = tower.getCost();

        if (!spend(cost)) {
            return false;
        }

        towers.add(tower);
        return true;
    }
    public void addBudget(int amount) {
        this.budget += amount;
    }
    public List<Tower> getTowers() {
        return Collections.unmodifiableList(towers);
    }
}
