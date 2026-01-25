package com.uzerai.animalhusbandry.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

import javax.annotation.Nonnull;

public class AnimalHusbandry {
    public static final BuilderCodec<AnimalHusbandry> CODEC =
            BuilderCodec.builder(AnimalHusbandry.class, AnimalHusbandry::new)
                    .append(new KeyedCodec<>("BreedingEnabled", Codec.BOOLEAN),
                            (cfg, v) -> cfg.breedingEnabled = v,
                            cfg -> cfg.breedingEnabled)
                    .add()
                    .build();

    public boolean breedingEnabled = true;

    public AnimalHusbandry() {}

    public boolean isBreedingEnabled(){
        return breedingEnabled;
    }

    @Nonnull
    @Override
    public String toString() {
        return String.format(
                "AnimalHusbandryConfig { breedingEnabled: %s }",
                breedingEnabled
        );
    }
}