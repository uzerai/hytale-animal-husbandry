package com.uzerai.animalhusbandry.config;

import com.hypixel.hytale.assetstore.AssetExtraInfo;
import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;

import javax.annotation.Nonnull;

public class AnimalHusbandry implements JsonAssetWithMap<String, DefaultAssetMap<String, AnimalHusbandry>> {
    public static final AssetBuilderCodec<String, AnimalHusbandry> CODEC = AssetBuilderCodec.builder(
                    AnimalHusbandry.class,
                    AnimalHusbandry::new,
                    Codec.STRING,
                    (config, id) -> config.id = id,
                    AnimalHusbandry::getId,
                    (asset, data) -> asset.data = data,
                    asset -> asset.data)
            .append(new KeyedCodec<>("Enabled", Codec.BOOLEAN), (o, i) -> o.enabled = i, (o) -> o.enabled)
            .add()
            .append(new KeyedCodec<>("GrowthTickInterval", Codec.INTEGER), (o, i) -> o.growthTickInterval = i, (o) -> o.growthTickInterval)
            .add()
            .build();

    public String id;
    public AssetExtraInfo.Data data;
    public boolean enabled;
    public int growthTickInterval;

    public AnimalHusbandry() {}

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    @Nonnull
    public String toString() {
        return String.format("AnimalHusbandryConfig { enabled: %s, growthStageInterval: %s }", enabled, growthTickInterval);
    }
}