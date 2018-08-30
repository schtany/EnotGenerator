package neotran.plugins.enotgenerator.listener;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import neotran.plugins.enotgenerator.BlockTypeSelector;
import neotran.plugins.enotgenerator.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.UUID;

public class CobblestoneGenerationListener implements Listener {

    @EventHandler
    void onBlockFromTo(BlockFromToEvent e) {
        if (!(e.getBlock().getType() == Material.STATIONARY_LAVA || e.getBlock().getType() == Material.LAVA)) {
            return;
        }
        Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
            System.out.println(e.getToBlock().getType());
            if (e.getToBlock().getType() == Material.COBBLESTONE) {
                return;
            }
            if (ASkyBlockAPI.getInstance().islandAtLocation(e.getToBlock().getLocation())) {
                UUID owner = ASkyBlockAPI.getInstance().getOwner(e.getToBlock().getLocation());
                long lvl = ASkyBlockAPI.getInstance().getLongIslandLevel(owner);
                e.getToBlock().setType(BlockTypeSelector.getType(((int) lvl), Bukkit.getOfflinePlayer(owner)));
            }
        });

    }
}
