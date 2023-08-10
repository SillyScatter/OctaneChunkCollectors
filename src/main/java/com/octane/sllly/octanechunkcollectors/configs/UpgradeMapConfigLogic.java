package com.octane.sllly.octanechunkcollectors.configs;

import com.octane.sllly.octanechunkcollectors.objects.ChunkCollectorUpgrade;
import com.octane.sllly.octanechunkcollectors.objects.UpgradeMap;
import dev.splityosis.configsystem.configsystem.ConfigTypeLogic;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class UpgradeMapConfigLogic extends ConfigTypeLogic<UpgradeMap> {
    @Override
    public UpgradeMap getFromConfig(ConfigurationSection config, String path) {

        ConfigTypeLogic<ItemStack> itemStackConfigTypeLogic = (ConfigTypeLogic<ItemStack>) ConfigTypeLogic.getConfigTypeLogic(ItemStack.class,"");

        UpgradeMap upgradeMap = new UpgradeMap();

        ConfigurationSection configurationSection = config.getConfigurationSection(path);
        if (configurationSection!=null){
            for (String upgradeID : configurationSection.getKeys(false)) {
                ItemStack itemStack = itemStackConfigTypeLogic.getFromConfig(configurationSection, upgradeID +".item");

                Map<Integer, Double> tierValueMap = new HashMap<>();
                Map<Integer, Integer> tierCostMap = new HashMap<>();

                ConfigurationSection tierSection = configurationSection.getConfigurationSection(upgradeID+".tiers");
                if (tierSection != null){
                    for (String tier : tierSection.getKeys(false)) {
                        tierValueMap.put(Integer.valueOf(tier), tierSection.getDouble(tier+".value"));
                        tierCostMap.put(Integer.valueOf(tier), tierSection.getInt(tier+".price"));
                    }
                }
                ChunkCollectorUpgrade chunkCollectorUpgrade = new ChunkCollectorUpgrade(upgradeID, itemStack, tierValueMap, tierCostMap);
                upgradeMap.put(upgradeID, chunkCollectorUpgrade);
            }
        }
        return upgradeMap;
    }

    @Override
    public void setInConfig(UpgradeMap instance, ConfigurationSection config, String path) {

        ConfigTypeLogic<ItemStack> itemStackConfigTypeLogic = (ConfigTypeLogic<ItemStack>) ConfigTypeLogic.getConfigTypeLogic(ItemStack.class,"");

        for (String upgradeID : instance.keySet()) {
            ChunkCollectorUpgrade chunkCollectorUpgrade = instance.get(upgradeID);
            ItemStack itemStack = chunkCollectorUpgrade.getDisplayItem();
            itemStackConfigTypeLogic.setInConfig(itemStack, config, path +"."+upgradeID+".item");

            Map<Integer, Double> tierValueMap = chunkCollectorUpgrade.getTierValueMap();
            Map<Integer, Integer> tierPriceMap = chunkCollectorUpgrade.getTierPriceMap();

            for (int i = 1; i < tierValueMap.size()+1; i++) {
                Double value = tierValueMap.get(i);
                Integer price = tierPriceMap.get(i);
                config.set(path + "." +upgradeID+".tiers."+i+"" +".value", value);
                config.set(path + "." +upgradeID+".tiers."+i+"" +".price", price);
            }
        }
    }
}
