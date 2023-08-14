package com.octane.sllly.octanechunkcollectors.configs;

import com.octane.sllly.octanechunkcollectors.OctaneChunkCollectors;
import com.octane.sllly.octanechunkcollectors.objects.ChunkCollector;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockDataFiles {

    private File parentDirectory;

    private Map<File, FileConfiguration> toSave = new HashMap<>();
    private List<File> toDelete = new ArrayList<>();

    public BlockDataFiles(File parentDirectory) {
        this.parentDirectory = parentDirectory;
        if (!parentDirectory.exists()){
            parentDirectory.mkdirs();
        }
    }

    public FileConfiguration getConfig(Block block){
        String name = locationToString(block.getLocation())+".yml";
        File file = new File(parentDirectory, name);

        FileConfiguration config = toSave.get(file);
        if (config == null){
            if (file.exists())
                config = YamlConfiguration.loadConfiguration(file);
            else
                config = new YamlConfiguration();
        }

        toSave.put(file, config);
        return config;
    }

    public Map<Block, FileConfiguration> getAllConfigs(boolean directModify){
        File[] files = parentDirectory.listFiles();
        Map<Block, FileConfiguration> map = new HashMap<>();
        if (files == null) return map;
        for (File file : files) {
            if (file.getName().endsWith(".yml")){
                try {
                    Block block = locationFromString(file.getName().substring(0, file.getName().length() - 3)).getBlock();
                    FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                    map.put(block, config);
                    if (directModify)
                        toSave.put(file, config);
                }catch (Exception e){
                    // TODO silly decide what you want to do if this throws error (block cant be constructed probably cuz world was deleted)
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    public void deleteConfig(Block block){
        String name = locationToString(block.getLocation())+".yml";
        File file = new File(parentDirectory, name);
        toSave.remove(file);
        toDelete.add(file);
    }

    public void saveAll(){
        for (File file : new ArrayList<>(toDelete)) {
            file.delete();
        }
        toDelete.clear();

        for (ChunkCollector chunkCollector : OctaneChunkCollectors.chunkCollectorMap.values()) {
            FileConfiguration config = getConfig(chunkCollector.getLocation().getBlock());
            //config.set("", null);

            config.set("tiers", null);
            config.set("other", null);
            config.set("items", null);

            config.set("tiers.auto-sell", chunkCollector.getAutoSellTier());
            config.set("tiers.efficiency", chunkCollector.getEfficiencyTier());
            config.set("tiers.slot-capacity", chunkCollector.getSlotCapacityTier());
            config.set("other.auto-sell.enabled", chunkCollector.isAutoSellEnabled());
            config.set("other.auto-sell.last", chunkCollector.getLastAutoSold());

            int i = 0;

            for (Map.Entry<ItemStack, Integer> itemStackIntegerEntry : new HashMap<>(chunkCollector.getContents()).entrySet()) {
                ItemStack itemStack = itemStackIntegerEntry.getKey();
                int amount = itemStackIntegerEntry.getValue();
                i++;

                config.set("items."+i+".item", itemStack);
                config.set("items."+i+".amount", amount);
            }
        }

        for (Map.Entry<File, FileConfiguration> entry : new HashMap<>(toSave).entrySet()) {
            File file = entry.getKey();
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                entry.getValue().save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        toSave.clear();
    }

    public void initializeAutoSave(JavaPlugin plugin, int seconds){
        new BukkitRunnable(){
            @Override
            public void run() {
                saveAll();
            }
        }.runTaskTimerAsynchronously(plugin, seconds*20L, seconds*20L);
    }

    private static String locationToString(Location location){
        String world = location.getWorld().getName();
        String x = String.valueOf(location.getBlockX());
        String y = String.valueOf(location.getBlockY());
        String z = String.valueOf(location.getBlockZ());
        return world + "_-_" + x + "_-_" + y + "_-_" + z;
    }

    private static Location locationFromString(String str){
        String[] arr = str.split("_-_");
        World world = Bukkit.getWorld(arr[0]);
        double x = Double.parseDouble(arr[1]);
        double y = Double.parseDouble(arr[2]);
        double z = Double.parseDouble(arr[3]);
        return new Location(world, x, y, z);
    }
}