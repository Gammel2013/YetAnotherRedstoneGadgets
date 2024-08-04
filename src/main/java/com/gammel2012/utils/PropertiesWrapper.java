package com.gammel2012.utils;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.phys.Vec3;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public class PropertiesWrapper {

    protected Function<BlockState, MapColor> mapColor = p_284884_ -> MapColor.NONE;
    protected boolean hasCollision = true;
    protected SoundType soundType = SoundType.STONE;
    protected ToIntFunction<BlockState> lightEmission = p_60929_ -> 0;
    protected float explosionResistance;
    protected float destroyTime;
    protected boolean requiresCorrectToolForDrops;
    protected boolean isRandomlyTicking;
    protected float friction = 0.6F;
    protected float speedFactor = 1.0F;
    protected float jumpFactor = 1.0F;
    /**
     * Sets loot table information
     */
    protected ResourceLocation drops;
    protected boolean canOcclude = true;
    protected boolean isAir;
    protected boolean ignitedByLava;
    protected boolean forceSolidOn;
    protected PushReaction pushReaction = PushReaction.NORMAL;
    protected boolean spawnTerrainParticles = true;
    protected NoteBlockInstrument instrument = NoteBlockInstrument.HARP;
    protected java.util.function.Supplier<ResourceLocation> lootTableSupplier;
    protected boolean replaceable;
    protected BlockBehaviour.StateArgumentPredicate<EntityType<?>> isValidSpawn = (p_284893_, p_284894_, p_284895_, p_284896_) -> p_284893_.isFaceSturdy(
            p_284894_, p_284895_, Direction.UP
    )
            && p_284893_.getLightEmission(p_284894_, p_284895_) < 14;
    protected BlockBehaviour.StatePredicate isRedstoneConductor = (p_284888_, p_284889_, p_284890_) -> p_284888_.isCollisionShapeFullBlock(p_284889_, p_284890_);
    protected BlockBehaviour.StatePredicate isSuffocating = (p_284885_, p_284886_, p_284887_) -> p_284885_.blocksMotion()
            && p_284885_.isCollisionShapeFullBlock(p_284886_, p_284887_);
    protected BlockBehaviour.StatePredicate isViewBlocking = this.isSuffocating;
    protected BlockBehaviour.StatePredicate hasPostProcess = (p_60963_, p_60964_, p_60965_) -> false;
    protected BlockBehaviour.StatePredicate emissiveRendering = (p_60931_, p_60932_, p_60933_) -> false;
    protected boolean dynamicShape;
    protected FeatureFlag[] requiredFeatures = new FeatureFlag[0];
    protected BlockBehaviour.OffsetType offsetType = BlockBehaviour.OffsetType.NONE;

    public PropertiesWrapper(){
    }

    public static PropertiesWrapper of() {
        return new PropertiesWrapper();
    }

    //
    public PropertiesWrapper mapColor(DyeColor pMapColor) {
        this.mapColor = p_284892_ -> pMapColor.getMapColor();
        return this;
    }

    //
    public PropertiesWrapper mapColor(MapColor pMapColor) {
        this.mapColor = p_222988_ -> pMapColor;
        return this;
    }

    //
    public PropertiesWrapper mapColor(Function<BlockState, MapColor> pMapColor) {
        this.mapColor = pMapColor;
        return this;
    }

    //
    public PropertiesWrapper noCollission() {
        this.hasCollision = false;
        this.canOcclude = false;
        return this;
    }

    //
    public PropertiesWrapper noOcclusion() {
        this.canOcclude = false;
        return this;
    }

    //
    public PropertiesWrapper friction(float pFriction) {
        this.friction = pFriction;
        return this;
    }

    //
    public PropertiesWrapper speedFactor(float pSpeedFactor) {
        this.speedFactor = pSpeedFactor;
        return this;
    }

    //
    public PropertiesWrapper jumpFactor(float pJumpFactor) {
        this.jumpFactor = pJumpFactor;
        return this;
    }

    //
    public PropertiesWrapper sound(SoundType pSoundType) {
        this.soundType = pSoundType;
        return this;
    }

    //
    public PropertiesWrapper lightLevel(ToIntFunction<BlockState> pLightEmission) {
        this.lightEmission = pLightEmission;
        return this;
    }

    //
    public PropertiesWrapper strength(float pDestroyTime, float pExplosionResistance) {
        return this.destroyTime(pDestroyTime).explosionResistance(pExplosionResistance);
    }

    //
    public PropertiesWrapper instabreak() {
        return this.strength(0.0F);
    }

    //
    public PropertiesWrapper strength(float pStrength) {
        this.strength(pStrength, pStrength);
        return this;
    }

    //
    public PropertiesWrapper randomTicks() {
        this.isRandomlyTicking = true;
        return this;
    }

    //
    public PropertiesWrapper dynamicShape() {
        this.dynamicShape = true;
        return this;
    }

    //
    public PropertiesWrapper ignitedByLava() {
        this.ignitedByLava = true;
        return this;
    }

    //
    public PropertiesWrapper forceSolidOn() {
        this.forceSolidOn = true;
        return this;
    }

    //
    public PropertiesWrapper pushReaction(PushReaction pPushReaction) {
        this.pushReaction = pPushReaction;
        return this;
    }

    //
    public PropertiesWrapper air() {
        this.isAir = true;
        return this;
    }

    //
    public PropertiesWrapper isValidSpawn(BlockBehaviour.StateArgumentPredicate<EntityType<?>> pIsValidSpawn) {
        this.isValidSpawn = pIsValidSpawn;
        return this;
    }

    //
    public PropertiesWrapper isRedstoneConductor(BlockBehaviour.StatePredicate pIsRedstoneConductor) {
        this.isRedstoneConductor = pIsRedstoneConductor;
        return this;
    }

    //
    public PropertiesWrapper isSuffocating(BlockBehaviour.StatePredicate pIsSuffocating) {
        this.isSuffocating = pIsSuffocating;
        return this;
    }

    //
    public PropertiesWrapper isViewBlocking(BlockBehaviour.StatePredicate pIsViewBlocking) {
        this.isViewBlocking = pIsViewBlocking;
        return this;
    }

    //
    public PropertiesWrapper hasPostProcess(BlockBehaviour.StatePredicate pHasPostProcess) {
        this.hasPostProcess = pHasPostProcess;
        return this;
    }

    //
    public PropertiesWrapper emissiveRendering(BlockBehaviour.StatePredicate pEmissiveRendering) {
        this.emissiveRendering = pEmissiveRendering;
        return this;
    }

    public PropertiesWrapper requiresCorrectToolForDrops() {
        this.requiresCorrectToolForDrops = true;
        return this;
    }

    //
    public PropertiesWrapper destroyTime(float pDestroyTime) {
        this.destroyTime = pDestroyTime;
        return this;
    }

    //
    public PropertiesWrapper explosionResistance(float pExplosionResistance) {
        this.explosionResistance = Math.max(0.0F, pExplosionResistance);
        return this;
    }

    //
    public PropertiesWrapper offsetType(BlockBehaviour.OffsetType pOffsetType) {
        this.offsetType = pOffsetType;
        return this;
    }

    //
    public PropertiesWrapper noTerrainParticles() {
        this.spawnTerrainParticles = false;
        return this;
    }

    //
    public PropertiesWrapper requiredFeatures(FeatureFlag... pRequiredFeatures) {
        this.requiredFeatures = pRequiredFeatures.clone();
        return this;
    }

    //
    public PropertiesWrapper instrument(NoteBlockInstrument pInstrument) {
        this.instrument = pInstrument;
        return this;
    }

    //
    public PropertiesWrapper replaceable() {
        this.replaceable = true;
        return this;
    }

    public BlockBehaviour.Properties toProperties() {

        BlockBehaviour.Properties props = BlockBehaviour.Properties.of()
                .mapColor(this.mapColor);

        if (!this.hasCollision) {
            props = props.noCollission();
        }
        if (!this.canOcclude) {
            props = props.noOcclusion();
        }

        props = props.friction(this.friction)
                .speedFactor(this.speedFactor)
                .jumpFactor(this.jumpFactor)
                .sound(this.soundType)
                .lightLevel(this.lightEmission)
                .destroyTime(this.destroyTime)
                .explosionResistance(this.explosionResistance);

        if (this.requiresCorrectToolForDrops) {
            props = props.requiresCorrectToolForDrops();
        }
        if (this.isRandomlyTicking) {
            props = props.randomTicks();
        }
        if (this.dynamicShape) {
            props = props.dynamicShape();
        }
        if (this.ignitedByLava) {
            props = props.ignitedByLava();
        }
        if (this.forceSolidOn) {
            props.forceSolidOn();
        }

        props = props.pushReaction(this.pushReaction);

        if (this.isAir) {
            props = props.air();
        }

        props = props.isValidSpawn(this.isValidSpawn)
                .isRedstoneConductor(this.isRedstoneConductor)
                .isSuffocating(this.isSuffocating)
                .isViewBlocking(this.isViewBlocking)
                .hasPostProcess(this.hasPostProcess)
                .emissiveRendering(this.emissiveRendering)
                .offsetType(this.offsetType);

        if (!this.spawnTerrainParticles) {
            props = props.noTerrainParticles();
        }
        if (this.requiredFeatures.length != 0) {
            props = props.requiredFeatures(this.requiredFeatures);
        }

        props = props.instrument(this.instrument);

        if (this.replaceable) {
            props = props.replaceable();
        }

        return props;
    }

    public PropertiesWrapper copy() {

        PropertiesWrapper props = PropertiesWrapper.of()
                .mapColor(this.mapColor);

        if (!this.hasCollision) {
            props = props.noCollission();
        }
        if (!this.canOcclude) {
            props = props.noOcclusion();
        }

        props = props.friction(this.friction)
                .speedFactor(this.speedFactor)
                .jumpFactor(this.jumpFactor)
                .sound(this.soundType)
                .lightLevel(this.lightEmission)
                .destroyTime(this.destroyTime)
                .explosionResistance(this.explosionResistance);

        if (this.isRandomlyTicking) {
            props = props.randomTicks();
        }
        if (this.dynamicShape) {
            props = props.dynamicShape();
        }
        if (this.ignitedByLava) {
            props = props.ignitedByLava();
        }
        if (this.forceSolidOn) {
            props.forceSolidOn();
        }

        props = props.pushReaction(this.pushReaction);

        if (this.isAir) {
            props = props.air();
        }

        props = props.isValidSpawn(this.isValidSpawn)
                .isRedstoneConductor(this.isRedstoneConductor)
                .isSuffocating(this.isSuffocating)
                .isViewBlocking(this.isViewBlocking)
                .hasPostProcess(this.hasPostProcess)
                .emissiveRendering(this.emissiveRendering)
                .offsetType(this.offsetType);

        if (!this.spawnTerrainParticles) {
            props = props.noTerrainParticles();
        }
        if (this.requiredFeatures.length != 0) {
            props = props.requiredFeatures(this.requiredFeatures);
        }

        props = props.instrument(this.instrument);

        if (this.replaceable) {
            props = props.replaceable();
        }

        return props;
    }
}
