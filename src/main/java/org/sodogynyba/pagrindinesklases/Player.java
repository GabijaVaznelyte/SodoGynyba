package org.sodogynyba.pagrindinesklases;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Player {
    private int money;

    public Player(int startingMoney){
        this.money = startingMoney;
    }

    public boolean canAfford(int cost) {
        return money >= cost;
    }
    public void spendMoney(int amount) {
        money = money - amount;
    }
    public void addMoney(int amount){
        money = money + amount;
    }
}
