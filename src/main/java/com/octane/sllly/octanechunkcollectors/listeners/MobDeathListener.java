package com.octane.sllly.octanechunkcollectors.listeners;

import com.octane.sllly.octanechunkcollectors.OctaneChunkCollectors;
import com.octane.sllly.octanechunkcollectors.objects.ChunkCollector;
import com.octane.sllly.octanechunkcollectors.objects.CollectorMenu;
import com.octane.sllly.octanechunkcollectors.utils.SortingUtils;
import com.octane.sllly.octanechunkcollectors.utils.Util;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MobDeathListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent event) {
        List<ItemStack> drops = event.getDrops();
        ChunkCollector chunkCollector = OctaneChunkCollectors.chunkCollectorMap.get(event.getEntity().getLocation().getChunk());

        if (chunkCollector == null){
            return;
        }

        double efficiency = chunkCollector.getEfficiencyValue();
        if (efficiency > 1)
            efficiency = 1;
        for (ItemStack drop : drops) {
            drop.setAmount((int) Math.ceil(drop.getAmount()*efficiency));
        }

        for (ItemStack drop : drops) {
            if (drop == null || drop.getType() == Material.AIR){
                drops.remove(drop);
            }
        }

        HashMap<ItemStack, Integer> dropsMap = SortingUtils.convertListToHashMap(drops);

        chunkCollector.setContentItemList(SortingUtils.addIntoContentItems(dropsMap, chunkCollector));

        chunkCollector.setContents(SortingUtils.convertContentItemsToHashMap(chunkCollector.getContentItemList()));

        drops.clear();
    }
}
