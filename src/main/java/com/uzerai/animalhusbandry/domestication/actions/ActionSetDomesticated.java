package com.uzerai.animalhusbandry.domestication.actions;

import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.entity.UUIDComponent;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
import com.uzerai.animalhusbandry.AnimalHusbandryPlugin;
import com.uzerai.animalhusbandry.domestication.DomesticatedComponent;
import com.uzerai.animalhusbandry.domestication.actions.builders.BuilderActionSetDomesticated;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class ActionSetDomesticated extends ActionBase {
    private final ComponentType<EntityStore, DomesticatedComponent> DOMESTICATED_COMPONENT_TYPE =
            DomesticatedComponent.getComponentType();

    public static final String TYPE = "SetDomesticated";

    private final boolean addCustodianFromTarget; // Whether to add custodian from sensor target

    public ActionSetDomesticated(@Nonnull BuilderActionSetDomesticated builder) {
        super(builder);
        this.addCustodianFromTarget = builder.getAddCustodianFromTarget();
    }

    @Override
    public boolean canExecute(
            @Nonnull Ref<EntityStore> ref,
            @Nonnull Role role,
            @Nullable InfoProvider sensorInfo,
            double dt,
            @Nonnull Store<EntityStore> store
    ) {
        if (!super.canExecute(ref, role, sensorInfo, dt, store)) {
            return false;
        }

        return !addCustodianFromTarget || (sensorInfo != null && sensorInfo.hasPosition());
    }

    @Override
    public boolean execute(
            @Nonnull Ref<EntityStore> ref,
            @Nonnull Role role,
            @Nonnull InfoProvider sensorInfo,
            double dt,
            @Nonnull Store<EntityStore> store
    ) {
        super.execute(ref, role, sensorInfo, dt, store);

        if (addCustodianFromTarget && sensorInfo.hasPosition()) {
            DomesticatedComponent component = store.ensureAndGetComponent(ref, DOMESTICATED_COMPONENT_TYPE);

            assert sensorInfo.getPositionProvider() != null;

            Ref<EntityStore> targetRef = sensorInfo.getPositionProvider().getTarget();
            if (targetRef == null) return false;

            UUIDComponent uuidComp = store.getComponent(targetRef, UUIDComponent.getComponentType());
            if (uuidComp == null) return false;

            UUID targetUuid = uuidComp.getUuid();
            AnimalHusbandryPlugin.INSTANCE.getLogger().atInfo().log("Adding %s to custodians.", targetUuid);

            component.addCustodian(targetUuid);

            // Prevents despawning of domesticated animals.
            if (AnimalHusbandryPlugin.INSTANCE.getConfig().domesticatedCullingDisabled()){
                NPCEntity npcComponent = store.getComponent(ref, NPCEntity.getComponentType());
                npcComponent.setSpawnConfiguration(Integer.MIN_VALUE);
            }
        }

        return true;
    }
}