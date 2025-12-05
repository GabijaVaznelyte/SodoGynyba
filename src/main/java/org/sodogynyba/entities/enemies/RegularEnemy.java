package org.sodogynyba.entities.enemies;

import org.sodogynyba.path.Path;

public class RegularEnemy extends Enemy {

    public RegularEnemy(Path path) {
        super(10, 1, 4, 2, path);
    }
}
