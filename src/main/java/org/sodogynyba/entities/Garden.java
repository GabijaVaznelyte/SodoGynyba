package org.sodogynyba.entities;

import lombok.Getter;

public class Garden {
    @Getter
    private int health;

    public Garden(int health) {
        this.health = health;
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) { health = 0; }
    }
    public boolean isDestroyed() {
        return health <= 0;
    }
}
