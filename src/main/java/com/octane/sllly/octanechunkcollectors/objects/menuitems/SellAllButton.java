package com.octane.sllly.octanechunkcollectors.objects.menuitems;

import com.octane.sllly.octanechunkcollectors.OctaneChunkCollectors;
import com.octane.sllly.octanechunkcollectors.objects.ChunkCollector;
import com.octane.sllly.octanechunkcollectors.objects.CollectorMenu;
import com.octane.sllly.octanechunkcollectors.utils.MathUtils;
import com.octane.sllly.octanechunkcollectors.utils.Util;
import com.octanemobdrops.HeadData;
import com.octanemobdrops.HeadsApi;
import com.octanemobdrops.OctaneMobDrops;
import com.octanepvp.splityosis.octaneeconomies.api.Economy;
import com.octanepvp.splityosis.octaneshop.objects.EcoPrice;
import dev.splityosis.configsystem.configsystem.actionsystem.Actions;
import dev.splityosis.menulib.MenuItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellAllButton extends MenuItem {
    public SellAllButton(ChunkCollector chunkCollector) {
        super(getItem(chunkCollector), Sound.valueOf(OctaneChunkCollectors.guiConfig.sellButtonSound));
        this.executes((event, menu) -> {

            Player player = (Player) event.getWhoClicked();
            HashMap<Economy, Double> contentsWorth = getContentsWorth(chunkCollector);

            OctaneChunkCollectors.actionsConfig.onSellAllPrimary.perform(player, null);

            for (Economy economy : contentsWorth.keySet()) {
                double economyWorth = contentsWorth.get(economy);
                String symbol = economy.getSymbol();
                String economyName = economy.getName();

                Map<String,String> placeHolders = new HashMap<>();
                placeHolders.put("%symbol%", symbol);
                placeHolders.put("%economy%",economyName);
                placeHolders.put("%amount%",MathUtils.formatDouble(economyWorth));

                OctaneChunkCollectors.actionsConfig.onSellAllPerEconomy.perform(player, placeHolders);
            }

            for (ContentItem contentItem : new ArrayList<>(chunkCollector.getContentItemList())) {
                contentItem.sellEntireContentItem(player);
            }

            ((CollectorMenu) menu).update();
        });
    }

    private static ItemStack getItem(ChunkCollector chunkCollector){
        ItemStack itemStack = OctaneChunkCollectors.guiConfig.sellButtonItem.clone();

        if (OctaneChunkCollectors.guiConfig.sellPriceEnabled){
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (!itemMeta.hasLore()) {
                List<String> itemLore = new ArrayList<>();
            }
            List<String> itemLore = itemMeta.getLore();
            HashMap<Economy, Double> contentsWorth = getContentsWorth(chunkCollector);

            int insertAt = OctaneChunkCollectors.guiConfig.sellPriceLine;
            if (OctaneChunkCollectors.guiConfig.sellPriceLine >= itemLore.size()){
                insertAt = itemLore.size();
            }

            for (Economy economy : contentsWorth.keySet()) {
                String economyWorth = MathUtils.formatDouble(contentsWorth.get(economy));
                String symbol = economy.getSymbol();
                String economyName = economy.getName();
                String format = OctaneChunkCollectors.guiConfig.sellPriceFormat;
                format = format.replace("%symbol%", symbol).replace("%economy%",economyName).replace("%amount%",economyWorth);

                itemLore.add(insertAt, Util.colorize(format));
            }
            itemMeta.setLore(itemLore);
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    public static HashMap<Economy, Double> getContentsWorth(ChunkCollector chunkCollector){
        HashMap<Economy, Double> contentsWorth = new HashMap<>();

        Map<ItemStack, Integer> contents = chunkCollector.getContents();
        for (ItemStack itemStack : contents.keySet()) {
            int amount = contents.get(itemStack);

            if (itemStack == null || itemStack.getType()== Material.AIR){
                continue;
            }

            if (HeadsApi.isHead(itemStack)){
                HeadData headData = OctaneMobDrops.headDataMap.get(HeadsApi.getHeadType(itemStack));
                Economy economy = OctaneChunkCollectors.octaneEconomiesAPI.getEconomy(headData.getEconomy());

                Double economyWorthSoFar = contentsWorth.get(economy);
                if (economyWorthSoFar == null){
                    economyWorthSoFar = 0.0;
                }
                double itemStackWorth = headData.getPrice()*amount+economyWorthSoFar;
                contentsWorth.put(economy, itemStackWorth);
            }else{
                EcoPrice ecoPrice = OctaneChunkCollectors.octaneShopAPI.getSellPrice(itemStack);
                if (ecoPrice != null){
                    Economy economy = OctaneChunkCollectors.octaneEconomiesAPI.getEconomy(ecoPrice.getEconomy().getName());
                    Double economyWorthSoFar = contentsWorth.get(economy);
                    if (economyWorthSoFar == null){
                        economyWorthSoFar = 0.0;
                    }
                    double itemStackWorth = ecoPrice.getPrice()*amount+economyWorthSoFar;
                    contentsWorth.put(economy, itemStackWorth);
                }
            }
        }
        if (contentsWorth.size() == 0){
            contentsWorth.put(OctaneChunkCollectors.octaneEconomiesAPI.getEconomy("vault"), 0.0);
        }

        return contentsWorth;
    }
}
