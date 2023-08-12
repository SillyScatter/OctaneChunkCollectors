package com.octane.sllly.octanechunkcollectors.configs;

import com.octane.sllly.octanechunkcollectors.utils.Util;
import dev.splityosis.configsystem.configsystem.AnnotatedConfig;
import dev.splityosis.configsystem.configsystem.ConfigField;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class GuiConfig extends AnnotatedConfig {
    public GuiConfig(File parentDirectory, String name) {
        super(parentDirectory, name);
    }

    @ConfigField(path = "gui.title")
    public String guiTitle = "&8Omni-Gatherer";

    @ConfigField(path = "content.indexes", comment = "the slots in the gui where the items go")
    public List<Integer> contentIndexes = Arrays.asList(11,12,13,14,15);

    @ConfigField(path = "autosell.index")
    public Integer autoSellIndex = 21;

    @ConfigField(path = "autosell.item")
    public ItemStack autoSellItem = Util.createItemStack(Material.OAK_SIGN, 1, "&dAuto Sell", Arrays.asList("&%enabledcolorid%%isenabled", "&3click to toggle"));

    @ConfigField(path = "autosell.colors.enabled")
    public String autoSellColorEnabled = "a";

    @ConfigField(path = "autosell.colors.disabled")
    public String autoSellColorDisabled = "c";

    @ConfigField(path = "sellbutton.sound")
    public String sellButtonSound = "BLOCK_AMETHYST_BLOCK_BREAK";

    @ConfigField(path = "sellbutton.index")
    public Integer sellButtonIndex = 22;

    @ConfigField(path = "sellbutton.item")
    public ItemStack sellButtonItem = Util.createItemStack(Material.EMERALD, 1, "&dSell All", Arrays.asList("&aClick to sell all the stuff","line2"));

    @ConfigField(path = "sellbutton.sellprice.format")
    public String sellPriceFormat = "&4* &f%economy%: &c%symbol%%amount%";

    @ConfigField(path = "sellbutton.sellprice.line", comment = "the line of the lore to insert the economies at")
    public int sellPriceLine = 2;

    @ConfigField(path = "sellbutton.sellprice.enabled")
    public boolean sellPriceEnabled = true;

    @ConfigField(path = "upgradebutton.index")
    public Integer upgradeButtonIndex = 23;

    @ConfigField(path = "upgradebutton.item")
    public ItemStack upgradeButtonItem = Util.createItemStack(Material.SPECTRAL_ARROW, 1, "&dUpgrades", Arrays.asList("line1", "line2"));

    @ConfigField(path = "border.item")
    public ItemStack borderItem = Util.createItemStack(Material.GRAY_STAINED_GLASS_PANE, 1, "&7");

    @ConfigField(path = "pagesize")
    public int pageSize = 27;

    @ConfigField(path = "content.sound")
    public String contentButtonSound = "ENTITY_ENDERMAN_TELEPORT";

    @ConfigField(path = "content.titleformat")
    public String contentTitleFormat = "&c%amount%x %f%oldtitle%";

    @ConfigField(path = "content.extralore")
    public List<String> contentExtraLore = Arrays.asList("", "&feach item: %sellprice-solo%", "&fall together: %sellprice-all%");
}
