package com.octane.sllly.octanechunkcollectors.listeners;

import com.octane.sllly.octanechunkcollectors.objects.ChunkCollector;
import com.octane.sllly.octanechunkcollectors.objects.CollectorMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.List;

public class CloseMenu implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {

        if (event.getInventory() == null){
            return;
        }
        if (event.getInventory().getHolder()==null){
            return;
        }
        if (!(event.getInventory().getHolder() instanceof CollectorMenu)){
            return;
        }
        CollectorMenu menu = (CollectorMenu) event.getInventory().getHolder();
    }
}
