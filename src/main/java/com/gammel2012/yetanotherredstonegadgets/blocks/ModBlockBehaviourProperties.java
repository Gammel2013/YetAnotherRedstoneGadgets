package com.gammel2012.yetanotherredstonegadgets.blocks;

import com.gammel2012.utils.PropertiesWrapper;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class ModBlockBehaviourProperties {

    public static final PropertiesWrapper LAMP_PROPERTIES = PropertiesWrapper.of()
            .sound(SoundType.GLASS)
            .lightLevel((blockState) -> {
                return blockState.getValue(PropagatingRedstoneLampBlock.SIGNAL_STRENGTH) > 0 ? 15 : 0;
            })
            .strength(0.3F)
            .isValidSpawn((state, getter, blockPos, entityType) -> true)
            .isRedstoneConductor((state, getter, blockpos) -> false);

    public static final PropertiesWrapper DIAL_LAMP_PROPERTIES = PropertiesWrapper.of()
            .mapColor(MapColor.COLOR_BROWN)
            .sound(SoundType.GLASS)
            .strength(0.3F)
            .isValidSpawn((state, getter, blockPos, entityType) -> true)
            .isRedstoneConductor((state, getter, blockpos) -> false)
            .lightLevel((blockState) -> {
                return blockState.getValue(ModBlockProperties.POWER);
            });

    public static final PropertiesWrapper SEVEN_SEGMENT_LAMP_PROPERTIES = DIAL_LAMP_PROPERTIES.copy()
            .lightLevel(
                    (blockstate) -> 10
            );

    public static final PropertiesWrapper REDSTONE_COMPONENT_PROPERTIES = PropertiesWrapper.of()
            .mapColor(MapColor.COLOR_LIGHT_GRAY)
            .strength(0.3f)
            .sound(SoundType.STONE)
            .noOcclusion();

    public static final PropertiesWrapper LONG_RANGE_OBSERVER_PROPERTIES = PropertiesWrapper.of()
            .mapColor(MapColor.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .strength(3.0F)
            .requiresCorrectToolForDrops()
            .isRedstoneConductor((state, getter, blockpos) -> false);

    public static final PropertiesWrapper AMETHYST_RESONATOR_PROPERTIES = PropertiesWrapper.of()
            .mapColor(MapColor.STONE)
            .strength(2.0F)
            .requiresCorrectToolForDrops()
            .isRedstoneConductor((state, getter, blockpos) -> false)
            .noOcclusion()
            .sound(SoundType.AMETHYST);
}
