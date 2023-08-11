package com.octane.sllly.octanechunkcollectors.listeners;

import com.octane.sllly.octanechunkcollectors.utils.SortingUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class MobDeathListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeath(EntityDeathEvent event) {
        List<ItemStack> drops = event.getDrops();
        HashMap<ItemStack, Integer> alsoDrops = SortingUtils.convertListToHashMap(drops);



    }
}
