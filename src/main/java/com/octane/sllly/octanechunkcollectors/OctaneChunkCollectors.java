package com.octane.sllly.octanechunkcollectors;

import com.octane.sllly.octanechunkcollectors.commands.ChunkCollectorCommandSystem;
import com.octane.sllly.octanechunkcollectors.configs.*;
import com.octane.sllly.octanechunkcollectors.listeners.*;
import com.octane.sllly.octanechunkcollectors.objects.ChunkCollector;
import com.octane.sllly.octanechunkcollectors.utils.Util;
import com.octanepvp.splityosis.octaneeconomies.api.OctaneEconomiesAPI;
import com.octanepvp.splityosis.octaneshop.api.OctaneShopAPI;
import dev.splityosis.menulib.MenuLib;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class OctaneChunkCollectors extends JavaPlugin {

    public static OctaneShopAPI octaneShopAPI;

    public static OctaneEconomiesAPI octaneEconomiesAPI;

    public static CollectorConfig collectorConfig;

    public static UpgradesConfig upgradesConfig;

    public static GuiConfig guiConfig;

    public static LanguageConfig languageConfig;

    public static Map<Location, ChunkCollector> locationCollectorMap = new HashMap<>();

    public static Map<Chunk, ChunkCollector> chunkCollectorMap = new HashMap<>();

    public static BlockDataFiles blockDataFiles;
    public static File blockDataDirectory;

    public static OctaneChunkCollectors plugin;

    @Override
    public void onEnable() {
        plugin = this;

        MenuLib.setup(this);

        new UpgradeMapConfigLogic().register();

        octaneShopAPI = (OctaneShopAPI) getServer().getPluginManager().getPlugin("OctaneShop");
        octaneEconomiesAPI = (OctaneEconomiesAPI) getServer().getPluginManager().getPlugin("OctaneEconomies");

        collectorConfig = new CollectorConfig(getDataFolder(), "collector-config");
        collectorConfig.initialize();
        upgradesConfig = new UpgradesConfig(getDataFolder(), "upgrades-config");
        upgradesConfig.initialize();
        guiConfig = new GuiConfig(getDataFolder(), "gui-config");
        guiConfig.initialize();
        languageConfig = new LanguageConfig(getDataFolder(), "language-config");
        languageConfig.initialize();

        //Data
        blockDataDirectory = new File(getDataFolder(), "block-data");
        if (!blockDataDirectory.exists())
            blockDataDirectory.mkdirs();

        blockDataFiles = new BlockDataFiles(blockDataDirectory);
        blockDataFiles.initializeAutoSave(this, collectorConfig.autoSavePeriod);

        loadCollectors();


        new ChunkCollectorCommandSystem("occ", "octanechunkcollectors", "octanechunkcollector", "collectors").registerCommandBranch(this);
        getServer().getPluginManager().registerEvents(new PlaceCollector(), this);
        getServer().getPluginManager().registerEvents(new RightClickCollector(), this);
        getServer().getPluginManager().registerEvents(new MobDeathListener(), this);
        getServer().getPluginManager().registerEvents(new BreakCollector(), this);
        getServer().getPluginManager().registerEvents(new CloseMenu(), this);
    }

    @Override
    public void onDisable() {
        blockDataFiles.saveAll();
    }

    public void loadCollectors(){
        for (Map.Entry<Block, FileConfiguration> blockFileConfigurationEntry : blockDataFiles.getAllConfigs(false).entrySet()) {
            try {
                Block block = blockFileConfigurationEntry.getKey();
                FileConfiguration config = blockFileConfigurationEntry.getValue();

                int autosell = config.getInt("tiers.auto-sell");
                int efficiency = config.getInt("tiers.efficiency");
                int slotCapacity = config.getInt("tiers.slot-capacity");
                Map<ItemStack, Integer> items = new HashMap<>();

                long lastAutoSold = config.getLong("other.auto-sell.last");
                boolean autoSellEnabled = config.getBoolean("other.auto-sell.enabled");


                ConfigurationSection itemsSec = config.getConfigurationSection("items");
                if (itemsSec != null)
                    for (String key : itemsSec.getKeys(false)) {
                        Util.log(key);
                        ItemStack item = itemsSec.getItemStack(key + ".item");
                        int amount = itemsSec.getInt(key + ".amount");
                        items.put(item,amount);
                    }
                new ChunkCollector(block.getLocation(), items, autosell, slotCapacity, efficiency, lastAutoSold, autoSellEnabled);
            }catch (Exception e){
                blockDataFiles.deleteConfig(blockFileConfigurationEntry.getKey());
            }
        }
    }
}
