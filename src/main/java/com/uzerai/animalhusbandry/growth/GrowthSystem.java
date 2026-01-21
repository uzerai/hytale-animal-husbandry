package com.uzerai.animalhusbandry.growth;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.system.tick.TickingSystem;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.time.WorldTimeResource;
import com.hypixel.hytale.server.core.universe.world.SoundUtil;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.NPCPlugin;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.systems.RoleChangeSystem;

import javax.annotation.Nonnull;
import javax.sound.sampled.AudioSystem;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;

public final class GrowthSystem extends TickingSystem<EntityStore> {

    @Nonnull
    private final ComponentType<EntityStore, NPCEntity> npcComponentType;

    public GrowthSystem(@Nonnull ComponentType<EntityStore, NPCEntity> npcComponentType) {
        this.npcComponentType = npcComponentType;
    }

    @Override
    public void tick(float dt, int systemIndex, @Nonnull Store<EntityStore> store) {
        WorldTimeResource time = store.getResource(WorldTimeResource.getResourceType());
        Instant now = time.getGameTime();

        store.forEachChunk(NPCEntity.getComponentType(), (chunk, commandBuffer) -> {
            for (int i = 0; i < chunk.size(); i++) {
                NPCEntity npc = chunk.getComponent(i, npcComponentType);
                if (npc == null) continue;

                Role role = npc.getRole();
                String roleName = role.getRoleName();

                GrowthAsset asset =
                        GrowthAsset.getAssetMap().getAsset(roleName); // key by calf role name
                if (asset == null) continue;

                // Compute when this NPC should grow up
                TemporalAmount ageToGrow = asset.getAgeToGrow();
                LocalDateTime spawnTime =
                        LocalDateTime.ofInstant(npc.getSpawnInstant(), WorldTimeResource.ZONE_OFFSET);
                Instant growAt = spawnTime.plus(ageToGrow).toInstant(WorldTimeResource.ZONE_OFFSET);

                if (now.isBefore(growAt)) continue;

                int adultIndex = NPCPlugin.get().getIndex(asset.getAdultRoleName());
                if (adultIndex < 0 || role.isRoleChangeRequested()) {
                    continue;
                }

                Ref<EntityStore> ref = chunk.getReferenceTo(i);
                RoleChangeSystem.requestRoleChange(
                        ref,
                        role,
                        adultIndex,
                        asset.shouldChangeAppearance(),
                        null,
                        null,
                        store
                );

                int soundIndex = asset.getGrowSoundEventIndex();
                if (soundIndex != 0) {
                    TransformComponent transform = store.getComponent(ref, TransformComponent.getComponentType());
                    if (transform != null) {
                        Vector3d pos = transform.getPosition();
                        // Store implements ComponentAccessor<EntityStore>, so this matches ActionPlaySound:
                        SoundUtil.playSoundEvent3d(ref, soundIndex, pos.getX(), pos.getY(), pos.getZ(), false, store);
                    }
                }

                role.setReachedTerminalAction(true);
            }
        });
    }
}