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

        for (ItemStack drop : drops) {
            if (drop == null || drop.getType() == Material.AIR){
                drops.remove(drop);
            }
        }

        HashMap<ItemStack, Integer> dropsMap = SortingUtils.convertListToHashMap(drops);

        //Debug
        //for (ItemStack itemStack : dropsMap.keySet()) {
        //    Util.broadcast("mob dropped "+dropsMap.get(itemStack)+" of "+itemStack.getType()+" of stack size "+itemStack.getAmount());
        //}

        chunkCollector.setContentItemList(SortingUtils.addIntoContentItems(dropsMap, chunkCollector));



        chunkCollector.setContents(SortingUtils.convertContentItemsToHashMap(chunkCollector.getContentItemList()));

        drops.clear();


    }
}
