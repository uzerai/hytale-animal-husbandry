package com.uzerai.animalhusbandry.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

import javax.annotation.Nonnull;

public class AnimalHusbandryConfig {
    private boolean disableDomesticatedCulling = true;

    public static final BuilderCodec<AnimalHusbandryConfig> CODEC =
            BuilderCodec.builder(AnimalHusbandryConfig.class, AnimalHusbandryConfig::new)
                    .append(new KeyedCodec<>("DisableDomesticatedCulling", Codec.BOOLEAN),
                            (config, value) -> config.disableDomesticatedCulling = value,
                            config -> config.disableDomesticatedCulling)
                    .documentation("When enabled, ignores overpopulation checks for domesticated animals.")
                    .add()
                    .build();


    public AnimalHusbandryConfig() {}

    public boolean domesticatedCullingDisabled()
    {
        return this.disableDomesticatedCulling;
    }


    @Nonnull
    @Override
    public String toString() {
        return String.format("AnimalHusbandryConfig {}");
    }
}