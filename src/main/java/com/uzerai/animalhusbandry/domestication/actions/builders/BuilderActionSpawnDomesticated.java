package com.uzerai.animalhusbandry.domestication.actions.builders;

import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.lifecycle.builders.BuilderActionSpawn;
import com.uzerai.animalhusbandry.domestication.actions.ActionSpawnDomesticated;

import javax.annotation.Nonnull;

public class BuilderActionSpawnDomesticated extends BuilderActionSpawn {

    public BuilderActionSpawnDomesticated() {}

    @Nonnull
    @Override
    public String getShortDescription() {
        return "Spawn an NPC (like Spawn) but automatically domesticated and non-despawning.";
    }

    @Nonnull
    @Override
    public String getLongDescription() {
        return "Uses the same options as the default Spawn action, but post-processes spawned NPCs to be domesticated " +
               "and not subject to culling if disabled.";
    }

    @Nonnull
    @Override
    public BuilderDescriptorState getBuilderDescriptorState() {
        return BuilderDescriptorState.Experimental;
    }

    @Nonnull
    @Override
    public ActionSpawnDomesticated build(@Nonnull BuilderSupport builderSupport) {
        return new ActionSpawnDomesticated(this, builderSupport);
    }
}