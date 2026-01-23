package com.uzerai.animalhusbandry.growth;

import javax.annotation.Nonnull;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

// Marker component for indicating that an entity is eligible for growth.
public final class GrowthComponent implements Component<EntityStore> {
    @Nonnull
    public static final BuilderCodec<GrowthComponent> CODEC = BuilderCodec.builder(
            GrowthComponent.class,
            GrowthComponent::new
    ).build();

    @Override
    public Component<EntityStore> clone() {
        return new GrowthComponent();
    }
}
