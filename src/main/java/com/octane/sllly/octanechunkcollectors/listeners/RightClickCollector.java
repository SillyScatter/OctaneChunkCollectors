package com.octane.sllly.octanechunkcollectors.listeners;

import com.octane.sllly.octanechunkcollectors.OctaneChunkCollectors;
import com.octane.sllly.octanechunkcollectors.objects.ChunkCollector;
import com.octane.sllly.octanechunkcollectors.objects.CollectorMenu;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class RightClickCollector implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Location location = event.getClickedBlock().getLocation();
        ChunkCollector chunkCollector = OctaneChunkCollectors.locationCollectorMap.get(location);

        if (chunkCollector == null){
            return;
        }

        Action action = event.getAction();
        if (!action.name().contains("RIGHT_CLICK")){
            return;
        }

        event.setCancelled(true);

        Player player = event.getPlayer();
        new CollectorMenu(chunkCollector).open(player);
    }
}
