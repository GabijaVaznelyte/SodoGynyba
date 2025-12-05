package org.sodogynyba.entities.enemies;

import org.sodogynyba.path.Path;

public class BasicEnemy extends Enemy {
    private static final int DEFAULT_HEALTH = 10;
    private static final int DEFAULT_DAMAGE = 1;
    private static final int DEFAULT_SPEED = 4;
    private static final int DEFAULT_REWARD = 2;

    public BasicEnemy(Path path) {
        super(DEFAULT_HEALTH, DEFAULT_DAMAGE, DEFAULT_SPEED, DEFAULT_REWARD, path);
    }

}
