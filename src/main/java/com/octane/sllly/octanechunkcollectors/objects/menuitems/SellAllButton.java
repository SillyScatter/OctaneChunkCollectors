package com.octane.sllly.octanechunkcollectors.objects.menuitems;

import com.octane.sllly.octanechunkcollectors.OctaneChunkCollectors;
import com.octane.sllly.octanechunkcollectors.objects.ChunkCollector;
import com.octanemobdrops.HeadData;
import com.octanemobdrops.HeadsApi;
import com.octanemobdrops.OctaneMobDrops;
import com.octanepvp.splityosis.octaneeconomies.api.Economy;
import com.octanepvp.splityosis.octaneshop.objects.EcoPrice;
import dev.splityosis.menulib.MenuItem;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class SellAllButton extends MenuItem {
    public SellAllButton(ChunkCollector chunkCollector) {
        super(getItem(chunkCollector), Sound.valueOf(OctaneChunkCollectors.guiConfig.sellButtonSound));
        this.executes((event, menu) -> {

        });
    }

    private static ItemStack getItem(ChunkCollector chunkCollector){

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
                    com.octanepvp.splityosis.octaneshop.economy.Economy economy = ecoPrice.getEconomy();
                    Double economyWorthSoFar = contentsWorth.get(economy);
                    double itemStackWorth = ecoPrice.getPrice()*amount+economyWorthSoFar;
                    contentsWorth.put(economy, itemStackWorth);
                }
            }
        }
    }
}
