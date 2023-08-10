package com.octane.sllly.octanechunkcollectors.objects;

import com.octane.sllly.octanechunkcollectors.utils.Util;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UpgradeMap extends HashMap<String, ChunkCollectorUpgrade> {

    public static UpgradeMap getDefault(){
        UpgradeMap upgradeMap = new UpgradeMap();
        ItemStack itemStack = Util.createItemStack(Material.OAK_SIGN, 1, "name", Arrays.asList("line1", "line2"));

        Map<Integer, Double> tierValueMap = new HashMap<>();
        Map<Integer, Integer> tierCostMap = new HashMap<>();

        tierValueMap.put(1, 5.0);
        tierCostMap.put(1, 100);

        ChunkCollectorUpgrade chunkCollectorUpgrade = new ChunkCollectorUpgrade("autosell", itemStack, tierValueMap, tierCostMap);

        upgradeMap.put("autosell", chunkCollectorUpgrade);

        return upgradeMap;
    }


}
