package com.octane.sllly.octanechunkcollectors.configs;

import dev.splityosis.configsystem.configsystem.AnnotatedConfig;
import dev.splityosis.configsystem.configsystem.ConfigField;
import dev.splityosis.configsystem.configsystem.actionsystem.ActionData;
import dev.splityosis.configsystem.configsystem.actionsystem.Actions;

import java.io.File;
import java.util.Arrays;

public class ActionsConfig extends AnnotatedConfig {
    public ActionsConfig(File parentDirectory, String name) {
        super(parentDirectory, name);
    }

    @ConfigField(path = "actions.on-sell-all.primary")
    public Actions onSellAllPrimary = new Actions(Arrays.asList(new ActionData("MESSAGE", Arrays.asList("&6&lWelcome !!!!!!"))));

    @ConfigField(path = "actions.on-sell-all.per-economy")
    public Actions onSellAllPerEconomy = new Actions(Arrays.asList(new ActionData("MESSAGE", Arrays.asList("&6&lWelcome !!!!!!"))));
}
