package com.uzerai.animalhusbandry.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

import javax.annotation.Nonnull;

public class AnimalHusbandry {
    public static final BuilderCodec<AnimalHusbandry> CODEC =
            BuilderCodec.builder(AnimalHusbandry.class, AnimalHusbandry::new)
                    .build();


    public AnimalHusbandry() {}


    @Nonnull
    @Override
    public String toString() {
        return String.format("AnimalHusbandryConfig {}");
    }
}