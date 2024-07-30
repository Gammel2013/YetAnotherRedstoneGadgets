package com.gammel2012.yetanotherredstonegadgets.blocks;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class ModBlockBehaviourProperties {

    public static final BlockBehaviour.Properties LAMP_PROPERTIES = BlockBehaviour.Properties.of()
            .sound(SoundType.GLASS)
            .lightLevel((blockState) -> {
                return blockState.getValue(PropagatingRedstoneLampBlock.SIGNAL_STRENGTH) > 0 ? 15 : 0;
            })
            .strength(0.3F)
            .isValidSpawn((state, getter, blockPos, entityType) -> true)
            .isRedstoneConductor((state, getter, blockpos) -> false);

    public static final BlockBehaviour.Properties DIAL_LAMP_PROPERTIES = BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_BROWN)
            .sound(SoundType.GLASS)
            .lightLevel((blockState) -> {
                return blockState.getValue(ModBlockProperties.POWER);
            })
            .strength(0.3F)
            .isValidSpawn((state, getter, blockPos, entityType) -> true)
            .isRedstoneConductor((state, getter, blockpos) -> false);

    public static final BlockBehaviour.Properties REDSTONE_COMPONENT_PROPERTIES = BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_LIGHT_GRAY)
            .strength(0.3f)
            .sound(SoundType.STONE)
            .noOcclusion();
}
