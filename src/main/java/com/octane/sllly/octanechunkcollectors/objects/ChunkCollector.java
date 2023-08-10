package com.octane.sllly.octanechunkcollectors.objects;

import com.octane.sllly.octanechunkcollectors.OctaneChunkCollectors;
import com.octanepvp.octanefactions.fobjects.Faction;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ChunkCollector {

    private Location location;

    private Faction faction;

    private boolean full;

    private HashMap<ItemStack, Integer> contents;

    //Upgrades people, Upgrades!
    //Auto Sell Upgrade
    private int autoSellTier;

    private Long lastAutoSold;

    private double autoSellTime;

    //Slot Capacity Upgrade
    private int slotCapacityTier;

    private int currentSlotCapacity;

    //Efficiency Upgrade
    private int efficiencyTier;

    private double efficiencyValue;

    public ChunkCollector(Location location, Faction faction) {
        this.location = location;
        this.faction = faction;

        full = false;
        contents = new HashMap<>();

        autoSellTier = 1;
        lastAutoSold = 0L;
        autoSellTime = OctaneChunkCollectors.upgradesConfig.upgradeMap.get("autosell").getTierValueMap().get(1);

        slotCapacityTier = 1;
        currentSlotCapacity = Integer.valueOf((int) Math.round(OctaneChunkCollectors.upgradesConfig.upgradeMap.get("capacity").getTierValueMap().get(1)));

        efficiencyTier = 1;
        efficiencyValue = OctaneChunkCollectors.upgradesConfig.upgradeMap.get("efficiency").getTierValueMap().get(1);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Faction getFaction() {
        return faction;
    }

    public void setFaction(Faction faction) {
        this.faction = faction;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public HashMap<ItemStack, Integer> getContents() {
        return contents;
    }

    public void setContents(HashMap<ItemStack, Integer> contents) {
        this.contents = contents;
    }

    public int getAutoSellTier() {
        return autoSellTier;
    }

    public void setAutoSellTier(int autoSellTier) {
        this.autoSellTier = autoSellTier;
    }

    public Long getLastAutoSold() {
        return lastAutoSold;
    }

    public void setLastAutoSold(Long lastAutoSold) {
        this.lastAutoSold = lastAutoSold;
    }

    public double getAutoSellTime() {
        return autoSellTime;
    }

    public void setAutoSellTime(double autoSellTime) {
        this.autoSellTime = autoSellTime;
    }

    public int getSlotCapacityTier() {
        return slotCapacityTier;
    }

    public void setSlotCapacityTier(int slotCapacityTier) {
        this.slotCapacityTier = slotCapacityTier;
    }

    public int getCurrentSlotCapacity() {
        return currentSlotCapacity;
    }

    public void setCurrentSlotCapacity(int currentSlotCapacity) {
        this.currentSlotCapacity = currentSlotCapacity;
    }

    public int getEfficiencyTier() {
        return efficiencyTier;
    }

    public void setEfficiencyTier(int efficiencyTier) {
        this.efficiencyTier = efficiencyTier;
    }

    public double getEfficiencyValue() {
        return efficiencyValue;
    }

    public void setEfficiencyValue(double efficiencyValue) {
        this.efficiencyValue = efficiencyValue;
    }
}
