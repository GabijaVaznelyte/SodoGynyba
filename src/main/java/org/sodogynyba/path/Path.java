package org.sodogynyba.path;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path {
    private List<Point> waypoints;

    public Path(List<Point> waypoints) {
        this.waypoints = waypoints;
    }

    public Point getWaypoint(int index) {
        if (index < 0 || index >= waypoints.size()) {
            throw new IndexOutOfBoundsException("Invalid waypoint index: " + index);
        }
        return new Point(waypoints.get(index));
    }
    public List<Point> getWaypointsCopy() {
        List<Point> copy = new ArrayList<>();
        for (Point p : waypoints) {
            copy.add(new Point(p));
        }
        return Collections.unmodifiableList(copy);
    }
    public int getLength() {
        return waypoints.size();
    }
}
