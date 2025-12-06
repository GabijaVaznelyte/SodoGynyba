package org.sodogynyba.entities.enemies;

import org.sodogynyba.path.Path;
import org.sodogynyba.utils.colors.EnemyColors;

public final class EnemyFactory {

    public static final EnemyStats BASIC = new EnemyStats(10, 1, 4, 2, EnemyColors.BASIC);
    public static final EnemyStats FAST = new EnemyStats(7, 1, 6, 3, EnemyColors.FAST);
    public static final EnemyStats TANK = new EnemyStats(20, 1, 2, 5, EnemyColors.TANK);

    private EnemyFactory() {} // prevent instantiation

    public static Enemy createEnemy(EnemyStats stats, Path path) {
        return new Enemy(stats, path);
    }
}
