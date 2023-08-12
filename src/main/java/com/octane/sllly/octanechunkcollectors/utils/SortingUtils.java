package com.octane.sllly.octanechunkcollectors.utils;

import com.octane.sllly.octanechunkcollectors.OctaneChunkCollectors;
import com.octane.sllly.octanechunkcollectors.objects.ChunkCollector;
import com.octane.sllly.octanechunkcollectors.objects.menuitems.ContentItem;
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

    public static List<ContentItem> addIntoContentItems(HashMap<ItemStack,Integer> contents, ChunkCollector chunkCollector){

        int currentSlotCapacity = chunkCollector.getCurrentSlotCapacity();
        List<ContentItem> contentItemList = chunkCollector.getContentItemList();

        for (ItemStack newItemStack : contents.keySet()) {
            int newAmount = contents.get(newItemStack);

            while (newAmount > 0){
                for (ContentItem contentItem : contentItemList) {
                    if (!contentItem.isFull() && newAmount > 0){
                        if (contentItem.getItemStack().isSimilar(newItemStack)){
                            int amount = contentItem.getAmount();
                            int space = currentSlotCapacity - amount;
                            if (newAmount > space){
                                contentItem.setAmount(currentSlotCapacity);
                                newAmount = newAmount-space;
                            }else{
                                contentItem.setAmount(amount+newAmount);
                                newAmount = 0;
                            }
                        }
                    }
                }
                int slotsAvailable = OctaneChunkCollectors.guiConfig.contentIndexes.size();
                int slotsUsed = contentItemList.size();
                while (newAmount > 0){
                    if (slotsUsed < slotsAvailable){
                        if (newAmount >= currentSlotCapacity){
                            contentItemList.add(new ContentItem(chunkCollector, newItemStack, currentSlotCapacity));
                            newAmount = newAmount-currentSlotCapacity;
                        }else{
                            contentItemList.add(new ContentItem(chunkCollector, newItemStack, newAmount));
                            newAmount = 0;
                        }
                    }else{
                        newAmount = 0;
                    }
                }
            }
        }
        return  contentItemList;
    }

    public static HashMap<ItemStack, Integer> convertContentItemsToHashMap(List<ContentItem> contentItems) {
        HashMap<ItemStack, Integer> result = new HashMap<>();

        for (ContentItem contentItem : contentItems) {
            ItemStack itemStack = contentItem.getItemStack();
            int amount = contentItem.getAmount();

            // If the ItemStack already exists in the result, add the amount to the existing value
            if (result.containsKey(itemStack)) {
                int currentAmount = result.get(itemStack);
                result.put(itemStack, currentAmount + amount);
            } else {
                result.put(itemStack, amount);
            }
        }

        return result;
    }
}
