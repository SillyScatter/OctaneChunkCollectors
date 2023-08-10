package com.octane.sllly.octanechunkcollectors.objects;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ChunkCollectorUpgrade {

    private String upgradeID;

    private ItemStack displayItem;

    private Map<Integer, Double> tierValueMap;

    private Map<Integer, Integer> tierPriceMap;

    public ChunkCollectorUpgrade(String upgradeID, ItemStack displayItem, Map<Integer, Double> tierValueMap, Map<Integer, Integer> tierPriceMap) {
        this.upgradeID = upgradeID;
        this.displayItem = displayItem;
        this.tierValueMap = tierValueMap;
        this.tierPriceMap = tierPriceMap;
    }

    public String getUpgradeID() {
        return upgradeID;
    }

    public ItemStack getDisplayItem() {
        return displayItem;
    }

    public Map<Integer, Double> getTierValueMap() {
        return tierValueMap;
    }

    public Map<Integer, Integer> getTierPriceMap() {
        return tierPriceMap;
    }
}
