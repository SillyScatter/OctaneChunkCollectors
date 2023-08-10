package com.octane.sllly.octanechunkcollectors.listeners;

import com.octane.sllly.octanechunkcollectors.OctaneChunkCollectors;
import com.octane.sllly.octanechunkcollectors.objects.ChunkCollector;
import com.octane.sllly.octanechunkcollectors.utils.Util;
import com.octanepvp.octanefactions.fobjects.Faction;
import com.octanepvp.splityosis.octanehomebases.objects.homebase.Homebase;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlaceCollector implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onBlockPlace(BlockPlaceEvent event) {

        EquipmentSlot equipmentSlot = event.getHand();
        ItemStack itemStack = event.getPlayer().getInventory().getItem(equipmentSlot);

        NBTItem nbtItem = new NBTItem(itemStack);
        if (!nbtItem.hasKey("octanechunkcollector")){
            return;
        }

        Chunk chunk = event.getBlockPlaced().getChunk();

        if (OctaneChunkCollectors.usedChunks.contains(chunk)){
            //Action - you cant place a chunk collector here as there is already one placed!
            event.setCancelled(true);
            return;
        }
        Homebase homebase = Homebase.getHomebase(event.getBlockPlaced().getWorld());
        Faction faction = homebase.getFaction();

        ChunkCollector chunkCollector = new ChunkCollector(event.getBlockPlaced().getLocation(), faction);
        OctaneChunkCollectors.locationCollectorMap.put(event.getBlockPlaced().getLocation(), chunkCollector);
    }
}
