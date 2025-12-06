package org.sodogynyba.entities.towers;

import org.sodogynyba.utils.colors.TowerColors;

import java.awt.*;

public final class TowerFactory {

    public static final TowerStats REGULAR = new TowerStats(20, 5, 50, 1000, TowerColors.BASIC);
    public static final TowerStats SLOW = new TowerStats(30, 3, 50, 1200, TowerColors.SLOW);

    private TowerFactory() {} // prevent instantiation

    public static Tower createTower(TowerStats stats, Point position) {
        if (stats == REGULAR) return new BasicTower(stats, position);
        else if (stats == SLOW) return new SlowTower(stats, position);
        else throw new IllegalArgumentException("Unknown tower stats");
    }
}
