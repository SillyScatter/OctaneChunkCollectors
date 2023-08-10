package com.octane.sllly.octanechunkcollectors.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;

public class InventoryUtils {

    public static void giveItemsToPlayer(Player player, ItemStack itemStack, int amount) {
        int maxStackSize = itemStack.getMaxStackSize();
        int remainingAmount = amount;

        while (remainingAmount > 0) {
            int giveAmount = Math.min(remainingAmount, maxStackSize);
            ItemStack giveItem = new ItemStack(itemStack);
            giveItem.setAmount(giveAmount);

            HashMap<Integer, ItemStack> leftovers = player.getInventory().addItem(giveItem);
            if (!leftovers.isEmpty()) {
                // If there are leftovers, drop them on the floor
                World world = player.getWorld();
                Location playerLocation = player.getLocation();
                for (ItemStack leftover : leftovers.values()) {
                    world.dropItemNaturally(playerLocation, leftover);
                }
            }

            remainingAmount -= giveAmount;
        }
    }
}
