package org.sodogynyba.waves;

import org.sodogynyba.paths.Path;

import java.util.ArrayList;
import java.util.List;

public final class WaveFactory {
    private WaveFactory() {}

    public static List<Wave> createWaves(int numWaves, List<Path> paths) {
        List<Wave> waves = new ArrayList<>();
        for (int i = 0; i < numWaves; i++) {
            waves.add(new Wave(i + 1, paths));
        }
        return waves;
    }
}
