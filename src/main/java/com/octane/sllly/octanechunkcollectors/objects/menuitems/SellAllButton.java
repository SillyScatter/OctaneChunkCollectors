package com.octane.sllly.octanechunkcollectors.objects.menuitems;

import com.octane.sllly.octanechunkcollectors.OctaneChunkCollectors;
import com.octane.sllly.octanechunkcollectors.objects.ChunkCollector;
import com.octane.sllly.octanechunkcollectors.utils.Util;
import com.octanemobdrops.HeadData;
import com.octanemobdrops.HeadsApi;
import com.octanemobdrops.OctaneMobDrops;
import com.octanepvp.splityosis.octaneeconomies.api.Economy;
import com.octanepvp.splityosis.octaneshop.objects.EcoPrice;
import dev.splityosis.menulib.MenuItem;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SellAllButton extends MenuItem {
    public SellAllButton(ChunkCollector chunkCollector) {
        super(getItem(chunkCollector), Sound.valueOf(OctaneChunkCollectors.guiConfig.sellButtonSound));
        this.executes((event, menu) -> {

            Player player = (Player) event.getWhoClicked();
            HashMap<Economy, Double> contentsWorth = getContentsWorth(chunkCollector);

            Util.sendMessage(player, OctaneChunkCollectors.languageConfig.sellMessage);
            for (Economy economy : contentsWorth.keySet()) {
                double economyWorth = contentsWorth.get(economy);
                String symbol = economy.getSymbol();
                String economyName = economy.getName();
                String format = OctaneChunkCollectors.languageConfig.sellFormat;
                format = format.replace("%symbol%", symbol).replace("%economy%",economyName).replace("%amount%",String.valueOf(economyWorth));
                Util.sendMessage(player, format);

                economy.deposit(player, economyWorth);
            }
            chunkCollector.setContents(new HashMap<>());
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
                double economyWorth = contentsWorth.get(economy);
                String symbol = economy.getSymbol();
                String economyName = economy.getName();
                String format = OctaneChunkCollectors.guiConfig.sellPriceFormat;
                format = format.replace("%symbol%", symbol).replace("%economy%",economyName).replace("%amount%",String.valueOf(economyWorth));

                itemLore.add(insertAt, Util.colorize(format));
            }
            itemMeta.setLore(itemLore);
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }

    public static HashMap<Economy, Double> getContentsWorth(ChunkCollector chunkCollector){
        HashMap<Economy, Double> contentsWorth = new HashMap<>();

        HashMap<ItemStack, Integer> contents = chunkCollector.getContents();
        for (ItemStack itemStack : contents.keySet()) {
            int amount = contents.get(itemStack);

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
