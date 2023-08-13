package com.octane.sllly.octanechunkcollectors.objects;

import com.octane.sllly.octanechunkcollectors.OctaneChunkCollectors;
import com.octanepvp.octanefactions.fobjects.Faction;
import com.octanepvp.splityosis.octaneeconomies.api.Economy;
import com.octanepvp.splityosis.octanehomebases.objects.homebase.Homebase;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class AutoSellTask extends BukkitRunnable {

    private ChunkCollector chunkCollector;

    public AutoSellTask(ChunkCollector chunkCollector) {
        this.chunkCollector = chunkCollector;
    }

    public void start(){
        this.runTaskLater(OctaneChunkCollectors.plugin, 10*20);
    }

    @Override
    public void run() {
        boolean enabled = chunkCollector.isAutoSellEnabled();
        if (enabled){
            Faction faction = Homebase.getHomebase(chunkCollector.getLocation().getWorld()).getFaction();
            chunkCollector.sellToFBank(faction);

            chunkCollector.setLastAutoSold(System.currentTimeMillis());
        }
    }
}
