package org.sodogynyba.paths;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class PathFactory {
    private static final int BLOCK_SIZE = 16;
    private static final int PATH_HEIGHT = 20;
    private static final int START_X_PATH1 = 5;
    private static final int START_X_PATH2_1 = 4;
    private static final int START_X_PATH2_2 = 12;

    private PathFactory() {}

    public static List<Path> createPaths(int type) {
        return type == 1 ? createSinglePath() : createDoublePath();
    }

    private static List<Path> createSinglePath() {
        List<Point> waypoints = new ArrayList<>();
        int x = START_X_PATH1 * BLOCK_SIZE;
        for (int y = 0; y < PATH_HEIGHT; y++) {
            x += (y % 4 < 2 ? BLOCK_SIZE : -BLOCK_SIZE);
            waypoints.add(new Point(x, y * BLOCK_SIZE));
        }
        return List.of(new Path(waypoints));
    }

    private static List<Path> createDoublePath() {
        List<Point> waypoints1 = new ArrayList<>();
        List<Point> waypoints2 = new ArrayList<>();
        int x1 = START_X_PATH2_1 * BLOCK_SIZE;
        int x2 = START_X_PATH2_2 * BLOCK_SIZE;

        for (int y = 0; y < PATH_HEIGHT; y++) {
            x1 += (y % 4 < 2 ? BLOCK_SIZE : -BLOCK_SIZE);
            x2 += (y % 4 < 2 ? -BLOCK_SIZE : BLOCK_SIZE);
            waypoints1.add(new Point(x1, y * BLOCK_SIZE));
            waypoints2.add(new Point(x2, y * BLOCK_SIZE));
        }
        return List.of(new Path(waypoints1), new Path(waypoints2));
    }
}
