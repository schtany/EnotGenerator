package neotran.plugins.enotgenerator.section;

import neotran.plugins.enotgenerator.Main;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PermChanceSection {

    private static final Map<String, Map<String, Integer>> permChances = new LinkedHashMap<>();

    @SuppressWarnings("unchecked")
    public static void addEntry(String perm, String type, int chance) {
        permChances.computeIfAbsent(perm, k -> new HashMap()).put(type, chance);
    }

    public static int getChance(OfflinePlayer player, String type) {
        int chance = 0;
        for (String perm : permChances.keySet()) {
            if (Main.getPerms().playerHas(null, player, perm)) {
                if (permChances.get(perm).containsKey(type)) {
                    chance = permChances.get(perm).get(type);
                }
            }
        }
        return chance;
    }
}
