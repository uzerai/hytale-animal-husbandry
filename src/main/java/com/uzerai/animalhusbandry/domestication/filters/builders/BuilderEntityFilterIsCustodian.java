package com.uzerai.animalhusbandry.domestication.filters.builders;

import com.google.gson.JsonElement;
import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.asset.builder.ComponentContext;
import com.hypixel.hytale.server.npc.asset.builder.InstructionType;
import com.hypixel.hytale.server.npc.corecomponents.IEntityFilter;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderEntityFilterBase;
import com.uzerai.animalhusbandry.domestication.filters.EntityFilterIsCustodian;
import javax.annotation.Nonnull;

public class BuilderEntityFilterIsCustodian extends BuilderEntityFilterBase {
    public BuilderEntityFilterIsCustodian() {}

    @Nonnull
    @Override
    public String getShortDescription() {
        return "Filters entities based on whether they are custodians of this NPC.";
    }

    @Nonnull
    @Override
    public String getLongDescription() {
        return "Returns true if the target entity is a custodian of this NPC (has DomesticatedComponent and target UUID is in custodian set).";
    }

    @Nonnull
    @Override
    public IEntityFilter build(@Nonnull BuilderSupport builderSupport) {
        return new EntityFilterIsCustodian();
    }

    @Nonnull
    @Override
    public BuilderDescriptorState getBuilderDescriptorState() {
        return BuilderDescriptorState.Stable;
    }

    @Nonnull
    @Override
    public BuilderEntityFilterIsCustodian readConfig(@Nonnull JsonElement data) {
        this.requireContext(InstructionType.Any, ComponentContext.NotSelfEntitySensor);
        return this;
    }
}