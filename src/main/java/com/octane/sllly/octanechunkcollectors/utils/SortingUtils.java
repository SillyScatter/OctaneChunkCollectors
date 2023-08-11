package com.octane.sllly.octanechunkcollectors.utils;

import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortingUtils {

    public static HashMap<ItemStack, Integer> convertListToHashMap(List<ItemStack> itemList) {
        HashMap<ItemStack, Integer> itemMap = new HashMap<>();

        for (ItemStack item : itemList) {
            if (item != null && !item.getType().isAir()) {
                int amount = item.getAmount();
                item.setAmount(1); // Set amount to 1 to ensure proper equality comparison
                itemMap.put(item.clone(), itemMap.getOrDefault(item, 0) + amount);
            }
        }

        return itemMap;
    }
}
