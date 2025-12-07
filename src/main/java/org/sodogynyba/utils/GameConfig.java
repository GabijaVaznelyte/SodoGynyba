package org.sodogynyba.utils;

public final class GameConfig {
    public static final int BLOCK_SIZE = 16;
    public static final int GRID_ROWS = 20;
    public static final int GRID_COLS = 20;
    public static final int BOARD_WIDTH = GRID_COLS * BLOCK_SIZE;
    public static final int BOARD_HEIGHT = GRID_ROWS * BLOCK_SIZE;
    public static final int INITIAL_BUDGET = 100;
    public static final int GARDEN_HEALTH = 2;

    private GameConfig() {} // prevent instantiation
}
