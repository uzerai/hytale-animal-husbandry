package com.uzerai.animalhusbandry.domestication.actions;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import com.hypixel.hytale.server.npc.corecomponents.lifecycle.ActionSpawn;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import com.uzerai.animalhusbandry.AnimalHusbandryPlugin;
import com.uzerai.animalhusbandry.domestication.DomesticatedComponent;
import com.uzerai.animalhusbandry.domestication.actions.builders.BuilderActionSpawnDomesticated;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class ActionSpawnDomesticated extends ActionSpawn {
    public static final String TYPE = "SpawnDomesticated";

    private static final ComponentType<EntityStore, DomesticatedComponent> DOMESTICATED_COMPONENT_TYPE =
            DomesticatedComponent.getComponentType();

    public ActionSpawnDomesticated(
            @Nonnull BuilderActionSpawnDomesticated builder,
            @Nonnull BuilderSupport builderSupport
    ) {
        super(builder, builderSupport);
    }

    @Override
    protected void postSpawn(
            @Nonnull NPCEntity npcComponent,
            @Nonnull Ref<EntityStore> ref,
            @Nonnull Store<EntityStore> store
    ) {
        super.postSpawn(npcComponent, ref, store);

        if (!ref.isValid()) {
            return;
        }

        if (AnimalHusbandryPlugin.INSTANCE.getConfig().domesticatedCullingDisabled()){
            npcComponent.setSpawnConfiguration(Integer.MIN_VALUE);
        }

        DomesticatedComponent parentDomesticated =
                store.getComponent(this.parent, DOMESTICATED_COMPONENT_TYPE);

        if (parentDomesticated != null) {
            Set<UUID> custodiansCopy = new HashSet<>(parentDomesticated.getCustodians());
            DomesticatedComponent childDomesticated = new DomesticatedComponent(custodiansCopy);
            store.addComponent(ref, DOMESTICATED_COMPONENT_TYPE, childDomesticated);
        }
    }
}