package neotran.plugins.enotgenerator;

import lombok.Getter;
import neotran.plugins.enotgenerator.listener.CobblestoneGenerationListener;
import neotran.plugins.enotgenerator.section.LevelChanceSection;
import neotran.plugins.enotgenerator.section.PermChanceSection;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Getter
    private static Main instance;
    @Getter
    private static Permission perms;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        loadConfig();
        setupPermissions();

        Bukkit.getPluginManager().registerEvents(new CobblestoneGenerationListener(), instance);
    }

    private void loadConfig(){
        ConfigurationSection blockTypes = getConfig().getConfigurationSection("blockTypes");
        for (String s : blockTypes.getKeys(false)) {
            BlockTypeSelector.add(s, blockTypes.getInt(s));
        }

        ConfigurationSection levelChances = getConfig().getConfigurationSection("levelChances");
        for (String lvl : levelChances.getKeys(false)) {
            int level = Integer.parseInt(lvl);
            ConfigurationSection types = levelChances.getConfigurationSection(lvl);
            for (String type : types.getKeys(false)) {
                LevelChanceSection.addEntry(level, type, types.getInt(type));
            }
        }

        ConfigurationSection permChances = getConfig().getConfigurationSection("permChances");
        for (String perm : permChances.getKeys(false)) {
            ConfigurationSection types = permChances.getConfigurationSection(perm);
            for (String type : types.getKeys(false)) {
                PermChanceSection.addEntry(perm, type, types.getInt(type));
            }
        }
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }
}
