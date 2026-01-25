package com.uzerai.animalhusbandry.domestication.actions.builders;

import com.google.gson.JsonElement;
import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;
import com.hypixel.hytale.server.npc.instructions.Action;
import com.uzerai.animalhusbandry.domestication.actions.ActionSetDomesticated;
import javax.annotation.Nonnull;

public class BuilderActionSetDomesticated extends BuilderActionBase {
    protected boolean addCustodianFromTarget;

    public BuilderActionSetDomesticated() {}

    @Nonnull
    @Override
    public String getShortDescription() {
        return "Ensures the NPC has a DomesticatedComponent, optionally adding a custodian from the sensor target.";
    }

    @Nonnull
    @Override
    public String getLongDescription() {
        return "Adds or ensures the DomesticatedComponent exists on the NPC. If AddCustodianFromTarget is true, " +
                "adds the entity from the sensor's target as a custodian.";
    }

    @Nonnull
    @Override
    public Action build(@Nonnull BuilderSupport builderSupport) {
        return new ActionSetDomesticated(this);
    }

    @Nonnull
    @Override
    public BuilderDescriptorState getBuilderDescriptorState() {
        return BuilderDescriptorState.Stable;
    }

    @Nonnull
    @Override
    public BuilderActionSetDomesticated readConfig(@Nonnull JsonElement data) {
        this.getBoolean(
                data,
                "AddCustodianFromTarget",
                aBoolean -> this.addCustodianFromTarget = aBoolean,
                false, // Default: just ensure component exists
                BuilderDescriptorState.Stable,
                "If true, adds the sensor's target entity as a custodian. Requires sensor to provide a target.",
                null
        );
        return this;
    }

    public boolean getAddCustodianFromTarget() {
        return this.addCustodianFromTarget;
    }
}