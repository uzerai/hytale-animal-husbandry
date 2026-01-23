package com.uzerai.animalhusbandry.domestication;

import javax.annotation.Nonnull;

import com.hypixel.hytale.component.AddReason;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.RemoveReason;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.RefSystem;
import com.hypixel.hytale.server.core.asset.type.item.config.Item;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.config.ItemAttitudeGroup;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.role.support.WorldSupport;
import com.uzerai.animalhusbandry.AnimalHusbandryPlugin;
import com.uzerai.animalhusbandry.growth.GrowthAsset;
import com.uzerai.animalhusbandry.growth.GrowthComponent;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;

public final class DomesticableRegisterSystem extends RefSystem<EntityStore> {
    @Nonnull
    private final ComponentType<EntityStore, NPCEntity> npcComponentType;
    @Nonnull
    private final ComponentType<EntityStore, DomesticableComponent> domesticableType;

    public DomesticableRegisterSystem(@Nonnull ComponentType<EntityStore, NPCEntity> npcComponentType,
                                      @Nonnull ComponentType<EntityStore, DomesticableComponent> domesticableComponentType) {
        this.npcComponentType = npcComponentType;
        this.domesticableType = domesticableComponentType;
    }

    @Override
    public void onEntityAdded(@Nonnull Ref<EntityStore> ref,
                              @Nonnull AddReason reason,
                              @Nonnull Store<EntityStore> store,
                              @Nonnull CommandBuffer<EntityStore> commandBuffer) {
        NPCEntity npc = store.getComponent(ref, npcComponentType);
        if (npc == null) return;

        String roleName = npc.getRoleName();
        DomesticableAsset asset = DomesticableAsset.getAssetMap().getAsset(roleName);
        if (asset == null) {
            return;
        }

        if (store.getComponent(ref, domesticableType) == null) {
            commandBuffer.putComponent(ref, domesticableType,
                    new DomesticableComponent(0d,
                            asset.getDomesticationThreshold(),
                            asset.getLovedItems(),
                            Instant.now(),
                            new HashSet<>())
            );
        }
    }

    @Override
    public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason removeReason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {

    }

    @Nonnull
    @Override
    public Query<EntityStore> getQuery() {
        return npcComponentType;
    }
}
