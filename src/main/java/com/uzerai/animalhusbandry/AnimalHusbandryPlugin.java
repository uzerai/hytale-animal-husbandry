package com.uzerai.animalhusbandry;

import javax.annotation.Nonnull;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.Config;
import com.hypixel.hytale.server.npc.NPCPlugin;
import com.uzerai.animalhusbandry.config.AnimalHusbandry;
import com.uzerai.animalhusbandry.domestication.DomesticatedComponent;
import com.uzerai.animalhusbandry.domestication.actions.ActionSetDomesticated;
import com.uzerai.animalhusbandry.domestication.actions.builders.BuilderActionSetDomesticated;
import com.uzerai.animalhusbandry.domestication.filters.EntityFilterIsCustodian;
import com.uzerai.animalhusbandry.domestication.filters.builders.BuilderEntityFilterIsCustodian;
import com.uzerai.animalhusbandry.domestication.sensors.SensorTargetIsCustodian;
import com.uzerai.animalhusbandry.domestication.sensors.builders.BuilderSensorTargetIsCustodian;

/**
 * This class serves as the entrypoint for your plugin. Use the setup method to register into game registries or add
 * event listeners.
 */
public class AnimalHusbandryPlugin extends JavaPlugin {
    public static AnimalHusbandryPlugin INSTANCE;
    private final Config<AnimalHusbandry> config = this.withConfig("AnimalHusbandry", AnimalHusbandry.CODEC);
    private ComponentType<EntityStore, DomesticatedComponent> domesticatedComponentComponentType;
    public AnimalHusbandryPlugin(@Nonnull JavaPluginInit init) {
        super(init);
        INSTANCE = this;
    }

    public AnimalHusbandry getConfig(){
        return this.config.get();
    }


    @Override
    protected void setup() {
        // TODO: Add breeding system
        // TODO: Add ownership / tameness system, to override avoidance for NPCs which are "tamed" by the player.
        // - When interacting with an animal, feeding it will add to its tameness for that player.
        //      When a threshold is hit, it will be considered tame and can be named by the player.
        // - This should remove the avoidance behaviour towards that player entity, and increase the
        //      follow behaviour chance of the animal such that if the tamer (player) is holding their loved
        //      food, they will always follow.
        // - Animals born of two tame animals (if breeding enabled), will also be tame against the same player.
        // TODO: Add feeding to accelerate growth (?)

        domesticatedComponentComponentType = getEntityStoreRegistry()
                .registerComponent(DomesticatedComponent.class, "Domesticated", DomesticatedComponent.CODEC);


        NPCPlugin.get().registerCoreComponentType(
                ActionSetDomesticated.TYPE,
                BuilderActionSetDomesticated::new
        );

        NPCPlugin.get().registerCoreComponentType(
                SensorTargetIsCustodian.TYPE,
                BuilderSensorTargetIsCustodian::new
        );

        NPCPlugin.get().registerCoreComponentType(
                EntityFilterIsCustodian.TYPE,
                BuilderEntityFilterIsCustodian::new
        );
    }

    @Override
    protected void start() {
        this.config.load().thenAccept(config -> {
            getLogger().atInfo().log("AnimalHusbandry loaded with config: %s", config);
        });
    }

    public ComponentType<EntityStore, DomesticatedComponent> getDomesticatedComponentComponentType(){
        return this.domesticatedComponentComponentType;
    }
}