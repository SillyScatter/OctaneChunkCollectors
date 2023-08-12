package com.octane.sllly.octanechunkcollectors.utils;

import com.octane.sllly.octanechunkcollectors.OctaneChunkCollectors;
import com.octanemobdrops.HeadData;
import com.octanemobdrops.HeadsApi;
import com.octanemobdrops.OctaneMobDrops;
import com.octanepvp.splityosis.octaneeconomies.api.Economy;
import com.octanepvp.splityosis.octaneeconomies.api.OctaneEconomiesAPI;
import com.octanepvp.splityosis.octaneshop.objects.EcoPrice;
import org.bukkit.inventory.ItemStack;

public class EconomyUtils {

    public static double getPricePerItem(ItemStack itemStack){
        ItemStack clone = itemStack.clone();
        clone.setAmount(1);

        if (HeadsApi.isHead(clone)){
            HeadData headData = OctaneMobDrops.headDataMap.get(HeadsApi.getHeadType(clone));
            return headData.getPrice();
        }else{
            EcoPrice ecoPrice = OctaneChunkCollectors.octaneShopAPI.getSellPrice(clone);
            if (ecoPrice != null){
                return ecoPrice.getPrice();
            }
        }
        return 0.0;
    }

    public static Economy getEconomy(ItemStack itemStack){
        ItemStack clone = itemStack.clone();
        clone.setAmount(1);

        if (HeadsApi.isHead(clone)){
            HeadData headData = OctaneMobDrops.headDataMap.get(HeadsApi.getHeadType(clone));
            return OctaneChunkCollectors.octaneEconomiesAPI.getEconomy(headData.getEconomy());
        }else{
            EcoPrice ecoPrice = OctaneChunkCollectors.octaneShopAPI.getSellPrice(clone);
            if (ecoPrice != null){
                return OctaneChunkCollectors.octaneEconomiesAPI.getEconomy(ecoPrice.getEconomy().getName());
            }
        }
        return OctaneChunkCollectors.octaneEconomiesAPI.getEconomy("vault");
    }
}
