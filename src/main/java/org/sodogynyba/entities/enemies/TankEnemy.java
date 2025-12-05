package org.sodogynyba.entities.enemies;

import org.sodogynyba.path.Path;

public class TankEnemy extends Enemy {
    private static final int DEFAULT_HEALTH = 20;
    private static final int DEFAULT_DAMAGE = 1;
    private static final int DEFAULT_SPEED = 3;
    private static final int DEFAULT_REWARD = 5;

    public TankEnemy(Path path) {
        super(DEFAULT_HEALTH, DEFAULT_DAMAGE, DEFAULT_SPEED, DEFAULT_REWARD, path);
    }
}
