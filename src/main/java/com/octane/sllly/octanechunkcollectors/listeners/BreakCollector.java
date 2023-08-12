package com.octane.sllly.octanechunkcollectors.listeners;

import com.octane.sllly.octanechunkcollectors.OctaneChunkCollectors;
import com.octane.sllly.octanechunkcollectors.objects.ChunkCollector;
import com.octane.sllly.octanechunkcollectors.utils.Util;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BreakCollector implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        ChunkCollector chunkCollector = OctaneChunkCollectors.locationCollectorMap.get(event.getBlock().getLocation());
        if (chunkCollector == null){
            return;
        }
        Location location = event.getBlock().getLocation();
        OctaneChunkCollectors.locationCollectorMap.remove(location);
        OctaneChunkCollectors.chunkCollectorMap.remove(location.getChunk());

        ItemStack itemStack = OctaneChunkCollectors.collectorConfig.getCollectorItem();
        location.getWorld().dropItem(location, itemStack);
    }
}
