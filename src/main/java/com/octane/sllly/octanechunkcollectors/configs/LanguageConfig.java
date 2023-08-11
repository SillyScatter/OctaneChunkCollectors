package com.octane.sllly.octanechunkcollectors.configs;

import dev.splityosis.configsystem.configsystem.AnnotatedConfig;
import dev.splityosis.configsystem.configsystem.ConfigField;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class LanguageConfig extends AnnotatedConfig {
    public LanguageConfig(File parentDirectory, String name) {
        super(parentDirectory, name);
    }

    @ConfigField(path = "sell.message")
    public List<String> sellMessage = Arrays.asList("&dYou have sold the collection chest for the following:");

    @ConfigField(path = "sell.format")
    public String sellFormat = "&4* &7%economy%: &c%symbol%&c&l%amount%";
}
