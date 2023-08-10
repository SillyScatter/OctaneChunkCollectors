package com.octane.sllly.octanechunkcollectors.configs;

import com.octane.sllly.octanechunkcollectors.objects.UpgradeMap;
import dev.splityosis.configsystem.configsystem.AnnotatedConfig;
import dev.splityosis.configsystem.configsystem.ConfigField;

import java.io.File;

public class UpgradesConfig extends AnnotatedConfig {
    public UpgradesConfig(File parentDirectory, String name) {
        super(parentDirectory, name);
    }

    @ConfigField(path = "upgrades")
    public UpgradeMap upgradeMap = UpgradeMap.getDefault();
}
