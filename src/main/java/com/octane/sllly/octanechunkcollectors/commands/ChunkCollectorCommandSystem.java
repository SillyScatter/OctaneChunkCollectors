package com.octane.sllly.octanechunkcollectors.commands;

import com.octane.sllly.octanechunkcollectors.OctaneChunkCollectors;
import com.octane.sllly.octanechunkcollectors.utils.InventoryUtils;
import com.octane.sllly.octanechunkcollectors.utils.Util;
import de.tr7zw.nbtapi.NBTItem;
import dev.splityosis.commandsystem.SYSCommand;
import dev.splityosis.commandsystem.SYSCommandBranch;
import dev.splityosis.commandsystem.arguments.IntegerArgument;
import dev.splityosis.commandsystem.arguments.PlayerArgument;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ChunkCollectorCommandSystem extends SYSCommandBranch {

    public ChunkCollectorCommandSystem(String... names) {
        super(names);
        setPermission("octanechunkcollectors.admin");

        addCommand(new SYSCommand("give")
                .setArguments(new PlayerArgument(), new IntegerArgument())
                .setUsage("/octanechunkcollectors give player amount")
                .executes((sender, args) -> {
                    ItemStack displayItem = OctaneChunkCollectors.collectorConfig.getCollectorItem();
                    Player player = Bukkit.getPlayer(args[0]);
                    int amount = Integer.valueOf(args[1]);
                    InventoryUtils.giveItemsToPlayer(player,displayItem,amount);
                }));

        addCommand(new SYSCommand("reload")
                .executes((sender, args) -> {
                    OctaneChunkCollectors.collectorConfig.reload();
                    OctaneChunkCollectors.upgradesConfig.reload();
                    Util.sendMessage(sender, "&aCollector Configs Reloaded");
                }));
    }
}
