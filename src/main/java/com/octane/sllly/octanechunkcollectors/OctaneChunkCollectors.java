package com.octane.sllly.octanechunkcollectors;

import com.octane.sllly.octanechunkcollectors.commands.ChunkCollectorCommandSystem;
import com.octane.sllly.octanechunkcollectors.configs.*;
import com.octane.sllly.octanechunkcollectors.listeners.PlaceCollector;
import com.octane.sllly.octanechunkcollectors.listeners.RightClickCollector;
import com.octane.sllly.octanechunkcollectors.objects.ChunkCollector;
import com.octanepvp.splityosis.octaneeconomies.api.OctaneEconomiesAPI;
import com.octanepvp.splityosis.octaneshop.api.OctaneShopAPI;
import dev.splityosis.menulib.MenuLib;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class OctaneChunkCollectors extends JavaPlugin {

    public static OctaneShopAPI octaneShopAPI;

    public static OctaneEconomiesAPI octaneEconomiesAPI;

    public static CollectorConfig collectorConfig;

    public static UpgradesConfig upgradesConfig;

    public static GuiConfig guiConfig;

    public static LanguageConfig languageConfig;

    public static Map<Location, ChunkCollector> locationCollectorMap = new HashMap<>();

    public static List<Chunk> usedChunks = new ArrayList<>();

    @Override
    public void onEnable() {

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


        new ChunkCollectorCommandSystem("occ", "octanechunkcollectors", "octanechunkcollector", "collectors").registerCommandBranch(this);
        getServer().getPluginManager().registerEvents(new PlaceCollector(), this);
        getServer().getPluginManager().registerEvents(new RightClickCollector(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
