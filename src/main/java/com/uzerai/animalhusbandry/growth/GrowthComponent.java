package com.uzerai.animalhusbandry.growth;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nullable;

// Marker component for indicating that an entity is eligible for growth.
public final class GrowthComponent implements Component<EntityStore> {
    @Override
    public Component<EntityStore> clone() {
        return new GrowthComponent();
    }
}
