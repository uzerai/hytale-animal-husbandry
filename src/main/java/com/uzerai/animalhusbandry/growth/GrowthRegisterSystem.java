package com.uzerai.animalhusbandry.growth;

import com.hypixel.hytale.component.*;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.RefSystem;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.entities.NPCEntity;

import javax.annotation.Nonnull;

public final class GrowthRegisterSystem extends RefSystem<EntityStore> {
    @Nonnull
    private final ComponentType<EntityStore, NPCEntity> npcComponentType;
    @Nonnull
    private final ComponentType<EntityStore, GrowthComponent> growthComponentType;

    public GrowthRegisterSystem(@Nonnull ComponentType<EntityStore, NPCEntity> npcComponentType,
                                @Nonnull ComponentType<EntityStore, GrowthComponent> growthComponentType) {
        this.npcComponentType = npcComponentType;
        this.growthComponentType = growthComponentType;
    }

    @Override
    public void onEntityAdded(@Nonnull Ref<EntityStore> ref,
                              @Nonnull AddReason reason,
                              @Nonnull Store<EntityStore> store,
                              @Nonnull CommandBuffer<EntityStore> commandBuffer) {

        NPCEntity npc = store.getComponent(ref, npcComponentType);
        if (npc == null) return;

        String roleName = npc.getRoleName();
        if (GrowthAsset.getAssetMap().getAsset(roleName) == null) {
            return; // no growth config for this role
        }

        if (store.getComponent(ref, growthComponentType) == null) {
            commandBuffer.putComponent(ref, growthComponentType, new GrowthComponent());
        }
    }

    @Override
    public void onEntityRemove(@Nonnull Ref<EntityStore> ref, @Nonnull RemoveReason removeReason, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {

    }

    @Nonnull
    @Override
    public Query<EntityStore> getQuery() {
        return npcComponentType; // fire for all NPCEntities being added
    }
}
