package com.uzerai.animalhusbandry;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;

import javax.annotation.Nonnull;

/**
 * This class serves as the entrypoint for your plugin. Use the setup method to register into game registries or add
 * event listeners.
 */
public class AnimalHusbandryPlugin extends JavaPlugin {

    public AnimalHusbandryPlugin(@Nonnull JavaPluginInit init) {
        super(init);
        getLogger().atInfo().log("Hello from " + this.getName() + " version " + this.getManifest().getVersion().toString());
    }

    @Override
    protected void setup() {
        getLogger().atInfo().log("Setting up plugin " + this.getName());
    }
}