package neotran.plugins.enotgenerator.section;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class LevelChanceSection {

    private static final TreeMap<Integer, Map<String, Integer>> levelChances = new TreeMap<>();

    @SuppressWarnings("unchecked")
    public static void addEntry(int level, String type, int chance) {
        levelChances.computeIfAbsent(level, k -> new HashMap()).put(type, chance);
    }

    public static int getChance(int level, String type) {
        int chance = 0;
        Integer integer = levelChances.floorKey(level);
        if (integer != null) {
            if (levelChances.get(integer).containsKey(type)) {
                chance = levelChances.get(integer).get(type);
            }
        }
        return chance;
    }

}
