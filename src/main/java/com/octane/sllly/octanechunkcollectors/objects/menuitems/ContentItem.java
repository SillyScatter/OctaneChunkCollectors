package com.octane.sllly.octanechunkcollectors.objects.menuitems;

import com.octane.sllly.octanechunkcollectors.OctaneChunkCollectors;
import com.octane.sllly.octanechunkcollectors.objects.ChunkCollector;
import com.octane.sllly.octanechunkcollectors.objects.CollectorMenu;
import com.octane.sllly.octanechunkcollectors.utils.EconomyUtils;
import com.octane.sllly.octanechunkcollectors.utils.MathUtils;
import com.octane.sllly.octanechunkcollectors.utils.SortingUtils;
import com.octane.sllly.octanechunkcollectors.utils.Util;
import com.octanemobdrops.HeadData;
import com.octanemobdrops.HeadsApi;
import com.octanemobdrops.OctaneMobDrops;
import com.octanepvp.splityosis.octaneeconomies.api.Economy;
import dev.splityosis.menulib.MenuItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class ContentItem extends MenuItem {

    private int amount;

    private ItemStack itemStack;

    private ChunkCollector chunkCollector;

    public ContentItem(ChunkCollector chunkCollector, ItemStack itemStack, int amount) {
        super(null, Sound.valueOf(OctaneChunkCollectors.guiConfig.contentButtonSound));

        this.chunkCollector = chunkCollector;
        this.amount = amount;
        this.itemStack = itemStack;

        this.executes((event, menu) -> {
            Player player = (Player) event.getWhoClicked();

            if (event.isShiftClick()){

            }

            CollectorMenu collectorMenu = (CollectorMenu) menu;
            collectorMenu.update();
        });
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

    @Override
    public ItemStack getDisplayItem() {
        String format = OctaneChunkCollectors.guiConfig.contentTitleFormat;
        String oldTitle = Util.getItemName(itemStack);
        int amount = getAmount();
        String newTitle = format.replace("%oldtitle%",oldTitle).replace("%amount%",String.valueOf(amount));
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> itemLore = Util.getLore(itemStack);
        List<String> extraLore = OctaneChunkCollectors.guiConfig.contentExtraLore;
        for (String s : extraLore) {
            itemLore.add(s);
        }
        itemMeta.setLore(Util.colorize(itemLore));
        itemMeta.setDisplayName(Util.colorize(newTitle));
        ItemStack displayItem = itemStack.clone();
        displayItem.setItemMeta(itemMeta);

        Economy economy = EconomyUtils.getEconomy(itemStack);
        String economyName = economy.getName();
        String symbol = economy.getSymbol();
        String pricePerItem = MathUtils.formatDouble(EconomyUtils.getPricePerItem(itemStack));
        String totalPrice = MathUtils.formatDouble(EconomyUtils.getPricePerItem(itemStack)*amount);

        displayItem = Util.replaceTextInItem(displayItem, "%sellprice-solo%",pricePerItem+"");
        displayItem = Util.replaceTextInItem(displayItem, "%sellprice-all%", totalPrice+"");
        displayItem = Util.replaceTextInItem(displayItem, "%economy%",economyName);
        displayItem = Util.replaceTextInItem(displayItem, "%symbol%", symbol);

        return displayItem;
    }

    public boolean sellEntireContentItem(Player player){
        ItemStack itemStack = getItemStack();
        int amount = getAmount();
        if (HeadsApi.isHead(itemStack)){
            EntityType entityType = HeadsApi.getHeadType(itemStack);
            if (!HeadsApi.canSell(player, entityType)){
                return false;
            }
            HeadsApi.sell(player,entityType,amount);
            chunkCollector.getContentItemList().remove(this);
            chunkCollector.setContents(SortingUtils.convertContentItemsToHashMap(chunkCollector.getContentItemList()));
            return true;
        }
        Economy economy = EconomyUtils.getEconomy(itemStack);
        double value = EconomyUtils.getPricePerItem(itemStack);

        if (economy == null) {
            return true;
        }

        economy.deposit(player, value*amount);
        chunkCollector.getContentItemList().remove(this);
        chunkCollector.setContents(SortingUtils.convertContentItemsToHashMap(chunkCollector.getContentItemList()));
        return true;
    }
}
