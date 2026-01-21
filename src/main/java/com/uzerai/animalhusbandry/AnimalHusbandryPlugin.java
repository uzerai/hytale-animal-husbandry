package com.uzerai.animalhusbandry;

import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.builtin.tagset.config.NPCGroup;
import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.util.Config;
import com.hypixel.hytale.server.npc.config.AttitudeGroup;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import com.uzerai.animalhusbandry.config.AnimalHusbandry;
import com.uzerai.animalhusbandry.growth.GrowthAsset;
import com.uzerai.animalhusbandry.growth.GrowthSystem;

import javax.annotation.Nonnull;

/**
 * This class serves as the entrypoint for your plugin. Use the setup method to register into game registries or add
 * event listeners.
 */
public class AnimalHusbandryPlugin extends JavaPlugin {
    public static AnimalHusbandryPlugin INSTANCE;
    public static AnimalHusbandryPlugin get() { return INSTANCE; }
    private final Config<AnimalHusbandry> config = this.withConfig("AnimalHusbandry", AnimalHusbandry.CODEC);
    public AnimalHusbandryPlugin(@Nonnull JavaPluginInit init) {
        super(init);
        INSTANCE = this;
    }

    @Override
    protected void setup() {
        // Register main configuration file for general stuffs.
        getAssetRegistry().register(
                HytaleAssetStore.builder(AnimalHusbandry.class, new DefaultAssetMap<>())
                        .setPath("NPC/AnimalHusbandry")
                        .setCodec(AnimalHusbandry.CODEC)
                        .setKeyFunction(AnimalHusbandry::getId)
                        .loadsAfter(NPCGroup.class, AttitudeGroup.class)
                        .build()
        );

        getAssetRegistry().register(
                HytaleAssetStore.builder(GrowthAsset.class, new DefaultAssetMap<>())
                        .setPath("NPC/AnimalHusbandry/Growth")
                        .setCodec(GrowthAsset.CODEC)
                        .setKeyFunction(GrowthAsset::getId)
                        .loadsAfter(AnimalHusbandry.class)
                        .build()
        );

        getEntityStoreRegistry().registerSystem(new GrowthSystem(NPCEntity.getComponentType()));

        // Need to listen to spawn events, and add a GrowthComponent from the GrowthComponentBuilder with the correct type
        // As a first-time setup,  maybe also just add the entity to any NPCEntity with a role that has a defined GrowthConfig.
        // Need to listen to ticks, and when the delta time between ticks is > config.GrowthTickInterval, increment all GrowthComponent currentGrowth instances
        // whose lastGrowthInstant is also longer than config.GrowthTickInterval ago.
        // Then need to send out events for "growing up" the ones which have tipped past their Role's "GrowthMax"
        // And then replace them with ONE of the "TargetRole"s in the "TargetRole" array.

        // Register growth section of plugin.
    }

    @Override
    protected void start() {
        this.config.load().thenAccept(config -> {
            if(config.enabled)
            {
                getLogger().atInfo().log("Animal husbandry is enabled: %s", config);
            }
        });
    }
}