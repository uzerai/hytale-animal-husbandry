package com.uzerai.animalhusbandry.domestication;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.ComponentType;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.uzerai.animalhusbandry.AnimalHusbandryPlugin;

import javax.swing.text.html.parser.Entity;

public final class DomesticatedComponent implements Component<EntityStore> {
    public static final BuilderCodec<DomesticatedComponent> CODEC =
            BuilderCodec.builder(DomesticatedComponent.class, DomesticatedComponent::new)
                    .append(new KeyedCodec<>("Custodians", Codec.STRING_ARRAY),
                            (comp, value) -> {}, // Don't set from codec.
                            comp -> comp.custodianPlayers.stream().map(UUID::toString).toArray(String[]::new))
                    .add().build();

    public static ComponentType<EntityStore, DomesticatedComponent> getComponentType() {
        return AnimalHusbandryPlugin.INSTANCE.getDomesticatedComponentComponentType();
    }

    public DomesticatedComponent() {
        custodianPlayers = new HashSet<>();
    }

    public DomesticatedComponent(Set<UUID> custodianPlayers){
        this.custodianPlayers = custodianPlayers;
    }

    private final Set<UUID> custodianPlayers;

    public void addCustodian(UUID player) {
        if (player != null){
            custodianPlayers.add(player);
        }
    }

    public void removeCustodian(UUID player){
        custodianPlayers.remove(player);
    }

    public void clearCustodians(){
        custodianPlayers.clear();
    }

    public Set<UUID> getCustodians(){
        return custodianPlayers;
    }

    @Override
    public Component<EntityStore> clone() {
        return new DomesticatedComponent(this.custodianPlayers);
    }
}
