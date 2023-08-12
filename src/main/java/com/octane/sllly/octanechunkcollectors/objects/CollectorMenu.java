package com.octane.sllly.octanechunkcollectors.objects;

import com.octane.sllly.octanechunkcollectors.OctaneChunkCollectors;
import com.octane.sllly.octanechunkcollectors.configs.GuiConfig;
import com.octane.sllly.octanechunkcollectors.objects.menuitems.ContentItem;
import com.octane.sllly.octanechunkcollectors.objects.menuitems.SellAllButton;
import dev.splityosis.menulib.Menu;
import dev.splityosis.menulib.MenuItem;
import org.bukkit.entity.Player;


public class CollectorMenu extends Menu {

    private ChunkCollector chunkCollector;
    public CollectorMenu(ChunkCollector chunkCollector) {
        super(OctaneChunkCollectors.guiConfig.pageSize);
        this.chunkCollector = chunkCollector;

        this.setTitle(OctaneChunkCollectors.guiConfig.guiTitle);

        for (int i = 0; i < OctaneChunkCollectors.guiConfig.pageSize; i++) {
            if (!OctaneChunkCollectors.guiConfig.contentIndexes.contains(i) && OctaneChunkCollectors.guiConfig.autoSellIndex!=i){
                if (OctaneChunkCollectors.guiConfig.sellButtonIndex != i && OctaneChunkCollectors.guiConfig.upgradeButtonIndex != i){
                    this.setStaticItem(i, new MenuItem(OctaneChunkCollectors.guiConfig.borderItem));
                }
            }
        }

        update();
    }

    @Override
    public void open(Player player) {
        update();
        super.open(player);
    }

    public ChunkCollector getChunkCollector() {
        return chunkCollector;
    }

    public void update(){
        this.setStaticItem(OctaneChunkCollectors.guiConfig.sellButtonIndex, new SellAllButton(chunkCollector));

        for (ContentItem contentItem : chunkCollector.getContentItemList()) {
            this.addListedItem(contentItem);
        }
        this.refresh();
    }
}
