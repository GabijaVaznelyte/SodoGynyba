package org.sodogynyba.pagrindinesklases;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Garden {
    private int currentHealth;
    private int maxHealth;

    public Garden(int maxHealth){
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
    }

    public boolean canTakeDamage(int damage){
        return currentHealth <= damage;
    }
    public void takeDamage(int damage) {
        if (canTakeDamage(damage)) {
            currentHealth -= damage;
        }
        else{
            currentHealth = 0;
        }
    }
    public boolean isAlive() {
        return currentHealth > 0;
    }
    public void reset() {
        currentHealth = maxHealth;
    }
}
