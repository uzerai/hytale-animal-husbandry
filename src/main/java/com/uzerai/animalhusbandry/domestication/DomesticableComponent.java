package com.uzerai.animalhusbandry.domestication;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.swing.text.html.parser.Entity;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Component;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

/**
 * Component to indicate that an animal is domesticable; interactions around feeding are handled here.
 */
public final class DomesticableComponent implements Component<EntityStore> {

    @Nonnull
    public static final BuilderCodec<DomesticableComponent> CODEC = BuilderCodec.builder(
            DomesticableComponent.class,
            DomesticableComponent::new
    )
        .append(new KeyedCodec<>("DomesticationProgress", Codec.DOUBLE),
                (component, v) -> component.domesticationProgress = v,
                component -> component.domesticationProgress)
        .add()
        .append(new KeyedCodec<>("DomesticationThreshold", Codec.DOUBLE),
                (component, v) -> component.domesticationThreshold = v,
                component -> component.domesticationThreshold)
        .add()
        .append(new KeyedCodec<>("LovedItemsIndexes", Codec.STRING_ARRAY),
                (component, v) -> component.lovedItemsIds = v,
                component -> component.lovedItemsIds)
        .add()
        .append(new KeyedCodec<>("InteractablePlayers", Codec.STRING),
                (component, v) -> {}, // Don't instantiate this on world load or any other time, let the interaciton system detect.
                component -> String.format("%s", component.interactablePlayers))
        .add()
        .build();

    public DomesticableComponent(double domesticationProgress,
                                 double domesticationThreshold,
                                 String[] lovedItemsIds,
                                 Instant lastInteraction, Set<Ref<EntityStore>> interactablePlayers) {
        this.domesticationProgress = domesticationProgress;
        this.domesticationThreshold = domesticationThreshold;
        this.lovedItemsIds = lovedItemsIds;
        this.lastInteraction = lastInteraction;
        this.interactablePlayers = interactablePlayers;
    }

    public DomesticableComponent() {}

    private double domesticationProgress = 0d;
    private double domesticationThreshold;
    private String[] lovedItemsIds;
    private Instant lastInteraction;
    @Nonnull
    private Set<Ref<EntityStore>> interactablePlayers = new HashSet<>();

    public boolean thresholdReached(){
        return domesticationThreshold < domesticationProgress;
    }

    public void increaseDomesticationProgress(double increase) {
        this.domesticationProgress += increase;
    }
    public double getDomesticationProgress(){
        return domesticationProgress;
    }
    public double getDomesticationThreshold(){
        return domesticationThreshold;
    }
    public String[] getLovedItemsIds() { return lovedItemsIds; }

    public boolean canInteractWith(Ref<EntityStore> playerRef) {
        return interactablePlayers.contains(playerRef);
    }
    public boolean hasInteractablePlayers() {
        return !interactablePlayers.isEmpty();
    }
    public void clearInteractablePlayers() {
        interactablePlayers.clear();
    }
    public Set<Ref<EntityStore>> getInteractablePlayers(){
        return this.interactablePlayers;
    }
    public boolean addInteractablePlayer(Ref<EntityStore> playerRef) {
        return this.interactablePlayers.add(playerRef);
    }

    @Override
    public DomesticableComponent clone() {
        return new DomesticableComponent(this.domesticationProgress,
                this.domesticationThreshold,
                this.lovedItemsIds,
                this.lastInteraction,
                this.interactablePlayers);
    }

    public boolean removeInteractablePlayer(Ref<EntityStore> playerRef) {
        return this.interactablePlayers.remove(playerRef);
    }
}
