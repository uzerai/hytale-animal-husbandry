package com.uzerai.animalhusbandry.domestication.filters;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.entity.UUIDComponent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.EntityFilterBase;
import com.hypixel.hytale.server.npc.corecomponents.IEntityFilter;
import com.hypixel.hytale.server.npc.role.Role;
import com.uzerai.animalhusbandry.domestication.DomesticatedComponent;
import com.uzerai.animalhusbandry.domestication.filters.builders.BuilderEntityFilterIsCustodian;

import javax.annotation.Nonnull;

public class EntityFilterIsCustodian extends EntityFilterBase {
    public static final String TYPE = "IsCustodian";
    // I honestly don't know what cost ranges should be considered, I'm assuming this is a low cost filter as it only
    // checks for the existence of a component and a comparison of a Set<> within that component.
    public static final int COST = IEntityFilter.LOW_COST;

    private static final ComponentType<EntityStore, DomesticatedComponent> DOMESTICATED_COMPONENT_TYPE =
            DomesticatedComponent.getComponentType();

    @Override
    public boolean matchesEntity(@Nonnull Ref<EntityStore> ref, @Nonnull Ref<EntityStore> targetRef, @Nonnull Role role, @Nonnull Store<EntityStore> store) {
        DomesticatedComponent domesticatedComponent = store.getComponent(ref, DOMESTICATED_COMPONENT_TYPE);
        UUIDComponent uuidComponent = store.getComponent(targetRef, UUIDComponent.getComponentType());


        if (domesticatedComponent == null || uuidComponent == null) {
            return false;
        }

        return domesticatedComponent.getCustodians().contains(uuidComponent.getUuid());
    }

    @Override
    public int cost() {
        return COST;
    }
}