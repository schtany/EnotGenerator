package neotran.plugins.enotgenerator;

import lombok.NonNull;
import neotran.plugins.enotgenerator.section.LevelChanceSection;
import neotran.plugins.enotgenerator.section.PermChanceSection;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BlockTypeSelector {

    private static final Random random = new Random();

    private static final Map<Material, Integer> types = new HashMap<>();

    public static void add(@NonNull String blockType, int chance) {
        Material type = Material.valueOf(blockType);
        types.put(type, chance);
    }

    public static Material getType(int level, @NonNull OfflinePlayer player) {
        int totalSum = 0;
        for (Integer integer : types.values()) {
            totalSum += integer;
        }
        double roll = totalSum * random.nextDouble();
        int currentSum = 0;
        for(Map.Entry<Material, Integer> entry : types.entrySet()) {
            int levelChance = LevelChanceSection.getChance(level, entry.getKey().toString());
            int permChance = PermChanceSection.getChance(player, entry.getKey().toString());
            int chance = entry.getValue() + levelChance + permChance;
            if (roll >= currentSum && roll < currentSum + chance) {
                return entry.getKey();
            }
            currentSum += entry.getValue();
        }
        throw new RuntimeException("currentSum = " + currentSum);
    }
}
