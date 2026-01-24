package com.uzerai.animalhusbandry;

import javax.annotation.Nonnull;

import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.asset.HytaleAssetStore;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;
import com.uzerai.animalhusbandry.config.AnimalHusbandry;
import com.uzerai.animalhusbandry.domestication.*;

/**
 * This class serves as the entrypoint for your plugin. Use the setup method to register into game registries or add
 * event listeners.
 */
public class AnimalHusbandryPlugin extends JavaPlugin {
    public static AnimalHusbandryPlugin INSTANCE;
    public static AnimalHusbandryPlugin get() { return INSTANCE; }
    private final Config<AnimalHusbandry> config = this.withConfig("AnimalHusbandry", AnimalHusbandry.CODEC);
    private ComponentType<EntityStore, DomesticableComponent> domesticableComponentType;
    public AnimalHusbandryPlugin(@Nonnull JavaPluginInit init) {
        super(init);
        INSTANCE = this;
    }

    public AnimalHusbandry getConfig(){
        return this.config.get();
    }

    public ComponentType<EntityStore, DomesticableComponent> getDomesticableComponentType() { return this.domesticableComponentType; }

    @Override
    protected void setup() {
        // TODO: Add breeding system
        // TODO: Add interaction to feed animals based on the Role's "Loved" foods (?)
        // TODO: Add ownership / tameness system, to override avoidance for NPCs which are "tamed" by the player.
        // - When interacting with an animal, feeding it will add to its tameness for that player.
        //      When a threshold is hit, it will be considered tame and can be named by the player.
        // - This should remove the avoidance behaviour towards that player entity, and increase the
        //      follow behaviour chance of the animal such that if the tamer (player) is holding their loved
        //      food, they will always follow.
        // - Animals born of two tame animals (if breeding enabled), will also be tame against the same player.
        // TODO: Add feeding to accelerate growth (?)
        getAssetRegistry().register(
                HytaleAssetStore.builder(DomesticableAsset.class, new DefaultAssetMap<>())
                        .setPath("NPC/AnimalHusbandry/Domestication")
                        .setCodec(DomesticableAsset.CODEC)
                        .setKeyFunction(DomesticableAsset::getId)
                        .build()
        );

//        getEntityStoreRegistry().registerSystem(new GrowthRegisterSystem(NPCEntity.getComponentType(), getGrowthComponentType()));
//        getEntityStoreRegistry().registerSystem(new GrowthSystem(NPCEntity.getComponentType(), getGrowthComponentType()));
//
//        // Domestication system registration
        this.domesticableComponentType = getEntityStoreRegistry().registerComponent(DomesticableComponent.class,
                "Domesticable", DomesticableComponent.CODEC);
//
//        getEntityStoreRegistry().registerSystem(new DomesticableRegisterSystem(NPCEntity.getComponentType(), getDomesticableComponentType()));
//
//
//        // Feeding system registration
//        getEntityStoreRegistry().registerSystem(new InteractablePlayersUpdateSystem());
    }

    @Override
    protected void start() {
        this.config.load().thenAccept(config -> {
            getLogger().atInfo().log("AnimalHusbandry loaded with config: %s", config);
        });
    }
}