package com.uzerai.animalhusbandry.domestication.sensors;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.entity.UUIDComponent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.SensorBase;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
import com.uzerai.animalhusbandry.domestication.DomesticatedComponent;
import com.uzerai.animalhusbandry.domestication.sensors.builders.BuilderSensorTargetIsCustodian;
import javax.annotation.Nonnull;

public class SensorTargetIsCustodian extends SensorBase {
    private static final ComponentType<EntityStore, DomesticatedComponent> DOMESTICATED_COMPONENT_TYPE =
            DomesticatedComponent.getComponentType();

    public static final String TYPE = "TargetIsCustodian";

    private final int targetSlot;

    public SensorTargetIsCustodian(@Nonnull BuilderSensorTargetIsCustodian builder, @Nonnull BuilderSupport support) {
        super(builder);
        this.targetSlot = builder.getTargetSlot(support);
    }

    @Override
    public boolean matches(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, double dt, @Nonnull Store<EntityStore> store) {
        if (!super.matches(ref, role, dt, store)) {
            return false;
        }

        // Get the target entity - either from a target slot or from interaction target
        Ref<EntityStore> targetRef;
        if (this.targetSlot >= 0) {
            targetRef = role.getMarkedEntitySupport().getMarkedEntityRef(this.targetSlot);
        } else {
            // Fall back to interaction target if no target slot specified
            targetRef = role.getStateSupport().getInteractionIterationTarget();
        }

        if (targetRef == null || !targetRef.isValid()) {
            return false;
        }

        // Get the DomesticatedComponent from the NPC entity
        DomesticatedComponent domesticatedComponent = store.getComponent(ref, DOMESTICATED_COMPONENT_TYPE);
        if (domesticatedComponent == null) {
            return false;
        }

        UUIDComponent uuidComponent = store.getComponent(ref, UUIDComponent.getComponentType());
        if (uuidComponent == null) return false;

        // Check if the target entity is in the custodian players set
        return domesticatedComponent.getCustodians().contains(uuidComponent.getUuid());
    }

    @Override
    public InfoProvider getSensorInfo() {
        return null;
    }
}