package org.sodogynyba.entities.towers;

import java.awt.*;

public record TowerStats(int cost, int damage, int range, long attackCooldown, Color color) {}