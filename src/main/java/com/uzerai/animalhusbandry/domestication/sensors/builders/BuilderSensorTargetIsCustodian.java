package com.uzerai.animalhusbandry.domestication.sensors.builders;

import com.google.gson.JsonElement;
import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.asset.builder.holder.StringHolder;
import com.hypixel.hytale.server.npc.asset.builder.validators.StringNullOrNotEmptyValidator;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderSensorBase;
import com.hypixel.hytale.server.npc.instructions.Sensor;
import com.uzerai.animalhusbandry.domestication.sensors.SensorTargetIsCustodian;
import javax.annotation.Nonnull;

public class BuilderSensorTargetIsCustodian extends BuilderSensorBase {
    protected final StringHolder targetSlot = new StringHolder();

    public BuilderSensorTargetIsCustodian() {}

    @Nonnull
    @Override
    public String getShortDescription() {
        return "Checks if the target entity is a custodian of this NPC.";
    }

    @Nonnull
    @Override
    public String getLongDescription() {
        return "Returns true if the NPC has a DomesticatedComponent and the target entity (from TargetSlot or interaction target) " +
                "is in the custodian players set.";
    }

    @Nonnull
    @Override
    public Sensor build(@Nonnull BuilderSupport builderSupport) {
        return new SensorTargetIsCustodian(this, builderSupport);
    }

    @Nonnull
    @Override
    public BuilderDescriptorState getBuilderDescriptorState() {
        return BuilderDescriptorState.Stable;
    }

    @Nonnull
    @Override
    public BuilderSensorTargetIsCustodian readConfig(@Nonnull JsonElement data) {
        this.getString(
                data,
                "TargetSlot",
                this.targetSlot,
                null,
                StringNullOrNotEmptyValidator.get(),
                BuilderDescriptorState.Stable,
                "Optional target slot name to check. If not specified, uses the interaction target.",
                null
        );
        return this;
    }

    public int getTargetSlot(@Nonnull BuilderSupport support) {
        String slot = this.targetSlot.get(support.getExecutionContext());
        return slot == null ? Integer.MIN_VALUE : support.getTargetSlot(slot);
    }
}