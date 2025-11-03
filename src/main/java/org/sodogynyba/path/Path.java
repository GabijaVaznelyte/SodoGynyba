package org.sodogynyba.path;

import lombok.Getter;

import java.awt.*;
import java.util.List;

@Getter

public class Path {
    private List<Point> waypoints;

    public Path(List<Point> waypoints) {
        this.waypoints = waypoints;
    }

    public Point getWaypoint(int index) {
        if (index >= 0 && index < waypoints.size()) {
            return waypoints.get(index);
        }
        return null;
    }

    public int getLength() {
        return waypoints.size();
    }
}
