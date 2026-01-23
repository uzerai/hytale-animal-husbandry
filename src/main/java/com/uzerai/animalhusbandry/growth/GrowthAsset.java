package com.uzerai.animalhusbandry.growth;

import java.time.Duration;
import java.time.temporal.TemporalAmount;

import javax.annotation.Nonnull;

import com.hypixel.hytale.assetstore.AssetExtraInfo;
import com.hypixel.hytale.assetstore.AssetRegistry;
import com.hypixel.hytale.assetstore.AssetStore;
import com.hypixel.hytale.assetstore.JsonAsset;
import com.hypixel.hytale.assetstore.codec.AssetBuilderCodec;
import com.hypixel.hytale.assetstore.map.DefaultAssetMap;
import com.hypixel.hytale.assetstore.map.JsonAssetWithMap;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.server.core.asset.type.soundevent.config.SoundEvent;

public final class GrowthAsset implements JsonAssetWithMap<String, DefaultAssetMap<String, GrowthAsset>>, JsonAsset<String> {

    private static AssetStore<String, GrowthAsset, DefaultAssetMap<String, GrowthAsset>> ASSET_STORE;

    public static final AssetBuilderCodec<String, GrowthAsset> CODEC =
            AssetBuilderCodec.builder(
                            GrowthAsset.class,
                            GrowthAsset::new,
                            Codec.STRING,
                            (o, v) -> o.id = v,
                            GrowthAsset::getId,
                            (o, d) -> o.data = d,
                            o -> o.data
                    )
                    .append(new KeyedCodec<>("AdultRole", Codec.STRING),
                            (asset, v) -> asset.adultRole = v,
                            asset -> asset.adultRole)
                    .documentation("The adult Role into which the growing NPCEntity will transform.")
                    .add()
                    .appendInherited(new KeyedCodec<>("AgeToGrow", Codec.STRING),
                            (asset, v) -> asset.ageToGrowRaw = v,
                            asset -> asset.ageToGrowRaw,
                            (asset, parent) -> asset.ageToGrowRaw = parent.ageToGrowRaw)
                    .documentation("In-game time until growth from initial spawn time of the entity.")
                    .add()
                    .appendInherited(new KeyedCodec<>("ChangeAppearance", Codec.BOOLEAN),
                            (asset, v) -> asset.changeAppearance = v,
                            asset -> asset.changeAppearance,
                            (asset, parent) -> asset.changeAppearance = parent.changeAppearance)
                    .documentation("Whether the Role change should invoke an appearance change. Useful if we ever want to support growth for non-calf entities which grow into bigger roles.")
                    .add()
                    .appendInherited(new KeyedCodec<>("GrowthSoundEffectId", Codec.STRING),
                            (asset, v) -> asset.growthSoundEffectId = v,
                            asset -> asset.growthSoundEffectId,
                            (asset, parent) -> asset.growthSoundEffectId = parent.growthSoundEffectId)
                    .documentation("Sound effect to play when the entity transitions into its AdultRole")
                    .add()
                    .afterDecode(asset -> {
                        if (asset.growthSoundEffectId != null) {
                            asset.soundEventIndex = SoundEvent.getAssetMap().getIndex(asset.growthSoundEffectId);
                        }
                    })
                    .build();

    private AssetExtraInfo.Data data;
    private String id;
    private String adultRole;
    private String ageToGrowRaw;
    private boolean changeAppearance;
    private String growthSoundEffectId;

    private transient TemporalAmount ageToGrow;
    private transient int soundEventIndex = 0;

    public GrowthAsset() {}

    public static AssetStore<String, GrowthAsset, DefaultAssetMap<String, GrowthAsset>> getAssetStore() {
        if (ASSET_STORE == null) {
            ASSET_STORE = AssetRegistry.getAssetStore(GrowthAsset.class);
        }
        return ASSET_STORE;
    }

    public static DefaultAssetMap<String, GrowthAsset> getAssetMap() {
        return getAssetStore().getAssetMap();
    }

    public String getAdultRoleName() {
        return this.adultRole;
    }

    public TemporalAmount getAgeToGrow() {
        if (ageToGrow == null) {
            ageToGrow = Duration.parse(ageToGrowRaw.toUpperCase());
        }
        return ageToGrow;
    }

    public int getGrowSoundEventIndex() {
        return this.soundEventIndex;
    }

    public boolean shouldChangeAppearance() {
        return changeAppearance;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Nonnull
    @Override
    public String toString() {
        return "HusbandryGrowthAsset{" +
                "id='" + id + '\'' +
                ", adultRole='" + adultRole + '\'' +
                ", ageToGrow='" + ageToGrowRaw + '\'' +
                '}';
    }
}
