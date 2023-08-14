package com.octane.sllly.octanechunkcollectors.objects;

import com.octane.sllly.octanechunkcollectors.OctaneChunkCollectors;
import com.octanepvp.octanefactions.fobjects.Faction;
import com.octanepvp.splityosis.octanehomebases.objects.homebase.Homebase;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoSellTask extends BukkitRunnable {

    public void start(){
        this.runTaskTimerAsynchronously(OctaneChunkCollectors.plugin, 0, 20*OctaneChunkCollectors.collectorConfig.autoSellCheckPeriod);
    }

    @Override
    public void run() {
        for (ChunkCollector chunkCollector : OctaneChunkCollectors.chunkCollectorMap.values()) {
            boolean enabled = chunkCollector.isAutoSellEnabled();
            if (enabled){

                Long lastSold = chunkCollector.getLastAutoSold();
                Long currentTime = System.currentTimeMillis();
                Long milliSecondsSince = currentTime-lastSold;
                double minutesSince = milliSecondsSince*1000*60;

                if (minutesSince > chunkCollector.getAutoSellTime()){
                    Faction faction = Homebase.getHomebase(chunkCollector.getLocation().getWorld()).getFaction();
                    chunkCollector.sellToFBank(faction);

                    chunkCollector.setLastAutoSold(System.currentTimeMillis());
                }
            }
        }
    }
}
