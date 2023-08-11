package com.octane.sllly.octanechunkcollectors.objects.menuitems;

import com.octane.sllly.octanechunkcollectors.OctaneChunkCollectors;
import com.octane.sllly.octanechunkcollectors.objects.ChunkCollector;
import dev.splityosis.menulib.MenuItem;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

public class ContentItem extends MenuItem {

    private int amount;

    private ItemStack itemStack;

    private ChunkCollector chunkCollector;

    public ContentItem(ChunkCollector chunkCollector, ItemStack itemStack, int amount) {
        super(getDisplayItem(itemStack), Sound.valueOf(OctaneChunkCollectors.guiConfig.contentButtonSound));

        this.chunkCollector = chunkCollector;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ChunkCollector getChunkCollector() {
        return chunkCollector;
    }

    public void setChunkCollector(ChunkCollector chunkCollector) {
        this.chunkCollector = chunkCollector;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public boolean isFull(){
        if (amount >= chunkCollector.getCurrentSlotCapacity()){
            return true;
        }
        return false;
    }

    private static ItemStack getDisplayItem(ItemStack itemStack){
        //Todo



        return itemStack;
    }
}
