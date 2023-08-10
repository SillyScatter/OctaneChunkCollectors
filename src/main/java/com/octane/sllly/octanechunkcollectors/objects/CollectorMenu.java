package com.octane.sllly.octanechunkcollectors.objects;

import com.octane.sllly.octanechunkcollectors.OctaneChunkCollectors;
import dev.splityosis.menulib.Menu;


public class CollectorMenu extends Menu {
    public CollectorMenu(ChunkCollector chunkCollector) {
        super(27);

        this.setTitle(OctaneChunkCollectors.guiConfig.guiTitle);


    }


}
