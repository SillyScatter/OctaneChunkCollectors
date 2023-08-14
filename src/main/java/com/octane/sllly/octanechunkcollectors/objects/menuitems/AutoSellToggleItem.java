package com.octane.sllly.octanechunkcollectors.objects.menuitems;

import com.octane.sllly.octanechunkcollectors.OctaneChunkCollectors;
import com.octane.sllly.octanechunkcollectors.objects.ChunkCollector;
import com.octane.sllly.octanechunkcollectors.objects.CollectorMenu;
import com.octane.sllly.octanechunkcollectors.utils.Util;
import dev.splityosis.menulib.MenuItem;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

public class AutoSellToggleItem extends MenuItem {
    public AutoSellToggleItem(ChunkCollector chunkCollector) {
        super(getDisplayItem(chunkCollector), Sound.valueOf(OctaneChunkCollectors.guiConfig.autoSellToggleSound));
        this.executes((event, menu) -> {
            boolean enabled = chunkCollector.isAutoSellEnabled();
            if (enabled){
                chunkCollector.setAutoSellEnabled(false);
            }else{
                chunkCollector.setAutoSellEnabled(true);
            }

            chunkCollector.sellToFBank(chunkCollector.getFaction());
            ((CollectorMenu) menu).update();
        });
    }


    public static ItemStack getDisplayItem(ChunkCollector chunkCollector){
        ItemStack itemStack = OctaneChunkCollectors.guiConfig.autoSellItem.clone();
        String enabledString = OctaneChunkCollectors.guiConfig.autoSellStringEnabled;
        String disabledString = OctaneChunkCollectors.guiConfig.autoSellStringDisabled;
        boolean enabled = chunkCollector.isAutoSellEnabled();
        String string = "";

        if (enabled){
            string = enabledString;
        }else{
            string = disabledString;
        }

        itemStack = Util.replaceTextInItem(itemStack, "%enabled%", string);

        itemStack = Util.colorize(itemStack);

        return itemStack;
    }
}
