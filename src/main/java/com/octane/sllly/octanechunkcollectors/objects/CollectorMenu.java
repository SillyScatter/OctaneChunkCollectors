package com.octane.sllly.octanechunkcollectors.objects;

import com.octane.sllly.octanechunkcollectors.OctaneChunkCollectors;
import com.octane.sllly.octanechunkcollectors.configs.GuiConfig;
import com.octane.sllly.octanechunkcollectors.objects.menuitems.SellAllButton;
import dev.splityosis.menulib.Menu;
import dev.splityosis.menulib.MenuItem;


public class CollectorMenu extends Menu {
    public CollectorMenu(ChunkCollector chunkCollector) {
        super(OctaneChunkCollectors.guiConfig.pageSize);

        this.setTitle(OctaneChunkCollectors.guiConfig.guiTitle);

        for (int i = 0; i < OctaneChunkCollectors.guiConfig.pageSize; i++) {
            if (!OctaneChunkCollectors.guiConfig.contentIndexes.contains(i) && OctaneChunkCollectors.guiConfig.autoSellIndex!=i){
                if (OctaneChunkCollectors.guiConfig.sellButtonIndex != i && OctaneChunkCollectors.guiConfig.upgradeButtonIndex != i){
                    this.setStaticItem(i, new MenuItem(OctaneChunkCollectors.guiConfig.borderItem));
                }
            }
        }

        this.setStaticItem(OctaneChunkCollectors.guiConfig.sellButtonIndex, new SellAllButton(chunkCollector));


    }


}
