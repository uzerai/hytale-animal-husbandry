package com.uzerai.animalhusbandry.domestication;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.dependency.Dependency;
import com.hypixel.hytale.component.dependency.Order;
import com.hypixel.hytale.component.dependency.SystemDependency;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.math.vector.Vector3d;
import com.hypixel.hytale.protocol.ComponentUpdate;
import com.hypixel.hytale.protocol.ComponentUpdateType;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.inventory.ItemStack;
import com.hypixel.hytale.server.core.modules.entity.component.Interactable;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.modules.entity.player.PlayerSystems;
import com.hypixel.hytale.server.core.modules.entity.tracker.EntityTrackerSystems;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.TargetUtil;
import com.uzerai.animalhusbandry.AnimalHusbandryPlugin;

/***
 * I didn't want to provide any kind of override for the Assets in the default packs, as interactions there might
 * change in upcoming changes -- so this system effectively only exists to register possible interacting players for
 * entities with a Domesticable component when the user is holding a Domesticable lovedFood item.
 */
public class InteractablePlayersUpdateSystem extends EntityTickingSystem<EntityStore> {

    private final Query<EntityStore> query = Query.and(
            AnimalHusbandryPlugin.INSTANCE.getDomesticableComponentType(),
            TransformComponent.getComponentType());

    private final Set<Dependency<EntityStore>> dependencies = Set.of(new SystemDependency<>(Order.AFTER, PlayerSystems.ProcessPlayerInput.class));

    @Override
    public void tick(float dt, int index, @Nonnull ArchetypeChunk<EntityStore> archetypeChunk, @Nonnull Store<EntityStore> store, @Nonnull CommandBuffer<EntityStore> commandBuffer) {
        Ref<EntityStore> entityRef = archetypeChunk.getReferenceTo(index);
        DomesticableComponent domesticable = archetypeChunk.getComponent(index, AnimalHusbandryPlugin.INSTANCE.getDomesticableComponentType());
        if (domesticable == null) return;

        TransformComponent transform = archetypeChunk.getComponent(index, TransformComponent.getComponentType());
        if (transform == null) return;

        Vector3d domesticablePosition = transform.getPosition();
        List<Ref<EntityStore>> nearbyPlayerRefs = TargetUtil.getAllEntitiesInSphere(domesticablePosition, 3.0, store)
                .stream()
                .filter(ref -> store.getComponent(ref, Player.getComponentType()) != null)
                .toList();

        boolean needsComponentUpdate = false;
        if (nearbyPlayerRefs.isEmpty() && domesticable.hasInteractablePlayers()){
            domesticable.clearInteractablePlayers();
            needsComponentUpdate = true;
        }

        String[] lovedItemIds = domesticable.getLovedItemsIds();
        for (Ref<EntityStore> playerRef : nearbyPlayerRefs) {
            Player player = store.getComponent(playerRef, Player.getComponentType());
            if (player == null) continue;

            ItemStack mainHandItem = player.getInventory().getItemInHand();
            if (mainHandItem != null && Arrays.stream(lovedItemIds).anyMatch(itemId -> itemId.equals(mainHandItem.getItemId()))) {
                needsComponentUpdate = domesticable.addInteractablePlayer(playerRef);
            } else {
                needsComponentUpdate =  domesticable.removeInteractablePlayer(playerRef);
            }
        }

        if (needsComponentUpdate){
            commandBuffer.putComponent(entityRef, AnimalHusbandryPlugin.INSTANCE.getDomesticableComponentType(), domesticable);

            if (domesticable.hasInteractablePlayers()){
                commandBuffer.ensureComponent(entityRef, Interactable.getComponentType());
            } else {
                // If there's no interactablePlayers but there's an Interactable component
                // we should remove the interactable from the entity.
                if (store.getArchetype(entityRef).contains(Interactable.getComponentType())) {
                    commandBuffer.removeComponent(entityRef, Interactable.getComponentType());
                }
            }
        }
    }

    private void sendInteractionHintToPlayers(
            @Nonnull Ref<EntityStore> entityRef,
            @Nonnull DomesticableComponent domesticable,
            @Nonnull Store<EntityStore> store
    ) {
        EntityTrackerSystems.Visible visible = store.getComponent(entityRef, EntityTrackerSystems.Visible.getComponentType());
        if (visible == null) return;

        String hintTranslationKey = "interactionHints.feedAnimal"; // Your custom hint

        for (Ref<EntityStore> playerRef : domesticable.getInteractablePlayers()) {
            if (visible.visibleTo.containsKey(playerRef)) {
                sendInteractionHint(entityRef, playerRef, hintTranslationKey, store);
            }
        }
//
//        for (Ref<EntityStore> playerRef : visible.newlyVisibleTo.keySet()) {
//            if (domesticable.canInteractWith(playerRef)) {
//                sendInteractionHint(entityRef, playerRef, hintTranslationKey, store);
//            }
//        }
    }

    private void sendInteractionHint(
            @Nonnull Ref<EntityStore> entityRef,
            @Nonnull Ref<EntityStore> playerRef,
            @Nonnull String hintTranslationKey,
            @Nonnull Store<EntityStore> store
    ) {
        EntityTrackerSystems.EntityViewer viewer = store.getComponent(playerRef, EntityTrackerSystems.EntityViewer.getComponentType());
        if (viewer != null && viewer.visible.contains(entityRef)) {
            ComponentUpdate update = new ComponentUpdate();
            update.type = ComponentUpdateType.Interactable;
            update.interactionHint = hintTranslationKey;
            viewer.queueUpdate(entityRef, update);
        }
    }

    @Nullable
    @Override
    public Query<EntityStore> getQuery() { return this.query; }

    @Nonnull
    @Override
    public Set<Dependency<EntityStore>> getDependencies() { return this.dependencies; }
}
