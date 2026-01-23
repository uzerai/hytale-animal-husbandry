package com.uzerai.animalhusbandry.domestication;

import java.util.HashSet;
import java.util.Set;

import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

public class DomesticatedComponent implements Component<EntityStore> {
    public DomesticatedComponent() {
        custodianPlayers = new HashSet<>();
    }

    private final Set<Ref<EntityStore>> custodianPlayers;

    public void addCustodian(Ref<EntityStore> player) {
        custodianPlayers.add(player);
    }

    public Set<Ref<EntityStore>> getCustodians(){
        return custodianPlayers;
    }

    @Override
    public Component<EntityStore> clone() {
        return new DomesticatedComponent();
    }
}
