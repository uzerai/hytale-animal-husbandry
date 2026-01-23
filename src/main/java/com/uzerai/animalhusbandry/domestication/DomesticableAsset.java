package com.uzerai.animalhusbandry.domestication;

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

public final class DomesticableAsset implements JsonAssetWithMap<String, DefaultAssetMap<String, DomesticableAsset>>, JsonAsset<String> {

    private static AssetStore<String, DomesticableAsset, DefaultAssetMap<String, DomesticableAsset>> ASSET_STORE;

    public static final AssetBuilderCodec<String, DomesticableAsset> CODEC =
            AssetBuilderCodec.builder(
                            DomesticableAsset.class,
                            DomesticableAsset::new,
                            Codec.STRING,
                            (o, v) -> o.id = v,
                            DomesticableAsset::getId,
                            (o, d) -> o.data = d,
                            o -> o.data
                    )
                    .appendInherited(new KeyedCodec<>("DomesticationThreshold", Codec.INTEGER),
                            (asset, v) -> asset.domesticationThreshold = v,
                            asset -> asset.domesticationThreshold,
                            (asset, parent) -> asset.domesticationThreshold = parent.domesticationThreshold)
                    .documentation("The threshold value that must be reached for an entity to become domesticated.")
                    .add()
                    .appendInherited(new KeyedCodec<>("DomesticationSoundEffectId", Codec.STRING),
                            (asset, v) -> asset.domesticationSoundEffectId = v,
                            asset -> asset.domesticationSoundEffectId,
                            (asset, parent) -> asset.domesticationSoundEffectId = parent.domesticationSoundEffectId)
                    .documentation("Sound effect to play when the entity becomes domesticated.")
                    .add()
                    .appendInherited(new KeyedCodec<>("DomesticationGainChance", Codec.DOUBLE),
                            (asset, v) -> asset.domesticationGainChance = v,
                            asset -> asset.domesticationGainChance,
                            (asset, parent) -> asset.domesticationGainChance = parent.domesticationGainChance)
                    .documentation("The chance (0.0 to 1.0) that feeding a loved item will increase domestication progress.")
                    .add()
                    .appendInherited(new KeyedCodec<>("DomesticationGainAmount", Codec.INTEGER),
                            (asset, v) -> asset.domesticationGainAmount = v,
                            asset -> asset.domesticationGainAmount,
                            (asset, parent) -> asset.domesticationGainAmount = parent.domesticationGainAmount)
                    .documentation("The amount of domestication progress gained when successfully feeding a loved item.")
                    .add()
                    .appendInherited(new KeyedCodec<>("LovedItems", Codec.STRING_ARRAY),
                            (asset, v) -> asset.lovedItems = v,
                            asset -> asset.lovedItems,
                            (asset, parent) -> {
                                if (asset.lovedItems == null && parent.lovedItems != null) {
                                    asset.lovedItems = parent.lovedItems;
                                }
                            })
                    .documentation("List of item identifiers that this entity loves. Can include wildcards (e.g., 'Item_Crop_*').")
                    .add()
                    .afterDecode(asset -> {
                        if (asset.domesticationSoundEffectId != null) {
                            asset.soundEventIndex = SoundEvent.getAssetMap().getIndex(asset.domesticationSoundEffectId);
                        }
                    })
                    .build();

    private AssetExtraInfo.Data data;
    private String id;
    private int domesticationThreshold;
    private String domesticationSoundEffectId;
    private double domesticationGainChance;
    private int domesticationGainAmount;
    private String[] lovedItems;

    private transient int soundEventIndex = 0;

    public DomesticableAsset() {}

    public static AssetStore<String, DomesticableAsset, DefaultAssetMap<String, DomesticableAsset>> getAssetStore() {
        if (ASSET_STORE == null) {
            ASSET_STORE = AssetRegistry.getAssetStore(DomesticableAsset.class);
        }
        return ASSET_STORE;
    }

    public static DefaultAssetMap<String, DomesticableAsset> getAssetMap() {
        return getAssetStore().getAssetMap();
    }

    public int getDomesticationThreshold() {
        return this.domesticationThreshold;
    }

    public int getDomesticationSoundEventIndex() {
        return this.soundEventIndex;
    }

    public double getDomesticationGainChance() {
        return this.domesticationGainChance;
    }

    public int getDomesticationGainAmount() {
        return this.domesticationGainAmount;
    }

    @Nonnull
    public String[] getLovedItems() {
        return this.lovedItems;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Nonnull
    @Override
    public String toString() {
        return "DomesticableAsset{" +
                "id='" + id + '\'' +
                ", domesticationThreshold=" + domesticationThreshold +
                ", domesticationGainChance=" + domesticationGainChance +
                ", domesticationGainAmount=" + domesticationGainAmount +
                ", lovedItems=" + String.join(", ", lovedItems)+
                '}';
    }
}