package com.octane.sllly.octanechunkcollectors.configs;

import com.octane.sllly.octanechunkcollectors.OctaneChunkCollectors;
import com.octane.sllly.octanechunkcollectors.utils.Util;
import de.tr7zw.nbtapi.NBTItem;
import dev.splityosis.configsystem.configsystem.AnnotatedConfig;
import dev.splityosis.configsystem.configsystem.ConfigField;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class CollectorConfig extends AnnotatedConfig {
    public CollectorConfig(File parentDirectory, String name) {
        super(parentDirectory, name);
    }

    @ConfigField(path = "item", comment = "this is the item that will be in their inventory")
    public ItemStack displayItem = getDisplayItem();

    private ItemStack getDisplayItem(){
        String name = "&e&lOmni-Collector";
        List<String> lore = Arrays.asList("&7Omni-collectors are awesome", "&7Omni-collectors are cool", "&7It's Henry's job to change this config", "&7Or else he's a fool");
        return Util.createItemStack(Material.BEACON, 1, name, lore);
    }

    public ItemStack getCollectorItem(){
        ItemStack displayItem = this.displayItem;
        NBTItem nbtItem = new NBTItem(displayItem);
        nbtItem.setString("octanechunkcollector", "true");
        displayItem = nbtItem.getItem();

        return displayItem;
    }
}
