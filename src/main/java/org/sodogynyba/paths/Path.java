package org.sodogynyba.paths;

import java.awt.*;
import java.util.List;

public record Path(List<Point> waypoints) {

    public Path {
        waypoints = List.copyOf(
                waypoints.stream()
                        .map(Point::new)
                        .toList()
        );
    }

    public Point getWaypoint(int index) {
        return new Point(waypoints.get(index));
    }
    public List<Point> getWaypointsCopy() {
        return waypoints.stream()
                .map(Point::new)
                .toList();
    }
    public int getLength() {
        return waypoints.size();
    }
}
