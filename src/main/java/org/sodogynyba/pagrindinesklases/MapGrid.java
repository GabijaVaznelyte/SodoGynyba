package org.sodogynyba.pagrindinesklases;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MapGrid {
    private static final int ROWS = 20;
    private static final int COLS = 20;
    private static final int TILE_SIZE = 16;

    static final Color PATH_COLOR = new Color(160, 120, 60);
    static final Color GRASS_COLOR = new Color(34, 139, 34);
    static final Color GARDEN_COLOR = new Color(200, 50, 50);
    static final Color TOWER_COLOR = new Color(80, 80, 200);

    private Tile[][] grid;
    private Path path;
    private Point gardenLocation;

    MapGrid(Path path) {
        this.path = path;
        this.grid = new Tile[ROWS][COLS];
        initializeGrid();
    }

    private void initializeGrid() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                grid[i][j] = new Tile(i, j);
            }
            for (Point p : path.getTiles()) {
                grid[p.x][p.y].setPath(true);
            }

            Point end = path.getEndPoint();
            grid[end.x][end.y].setGarden(true);
            gardenLocation = end;
        }
    }
    public boolean canPlaceTower(int row, int col){
        return !(grid[row][col].isGarden() || grid[row][col].isPath());
    }
    public void placeTower(int row, int col, Tower tower){
        grid[row][col].placeTower(tower);
    }
    public Tower getTower(int row, int col){
        return grid[row][col].getTower();
    }
    public Tile getTileAt(int row, int col){
        return grid[row][col];
    }
    //public ArrayList<Tower> getAllTowers(){
    //    return Arrays.asList(grid[gardenLocation.x][gardenLocation.y]);
    //}
}
