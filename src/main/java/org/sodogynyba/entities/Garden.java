package org.sodogynyba.entities;

import lombok.Getter;

@Getter

public class Garden {
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
