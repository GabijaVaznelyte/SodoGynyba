package org.sodogynyba.pagrindinesklases;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

@Getter
@Setter

public class Path {
    private ArrayList<Point> tiles;
    private int width;
    private int height;
    private Point startPoint;
    private Point endPoint;

    public Path(int width, int height) {
        this.width = width;
        this.height = height;
        tiles = new ArrayList<>();
        generateRandomPath();
    }

    public void generateRandomPath(){
        Random rand = new Random();
        int startY = rand.nextInt(height);
        startPoint = new Point(0, startY);
        tiles.add(startPoint);
        Point currentPoint = startPoint;
        while(currentPoint.x < width- 1){
            int dir = rand.nextInt(3);
            Point nextPoint = null;
            if(dir == 0){
                nextPoint = new Point(currentPoint.x + 1, currentPoint.y);
            }else if(dir == 1 && currentPoint.y > 0){
                nextPoint = new Point(currentPoint.x, currentPoint.y - 1);
            }else if(dir == 2 && currentPoint.y < height - 1){
                nextPoint = new Point(currentPoint.x, currentPoint.y + 1);
            }else{
                nextPoint = new Point(currentPoint.x + 1, currentPoint.y);
            }
            if(!tiles.contains(nextPoint)){
                tiles.add(nextPoint);
                currentPoint = nextPoint;
            }
        }
        endPoint = currentPoint;
    }
    public Point getTile(int index){
        return tiles.get(index);
    }
    public int getLength(){
        return tiles.size();
    }
    public boolean isPathTile(Point p){
        return tiles.contains(p);
    }
}
