package com.gammel2012.yetanotherredstonegadgetsmod.blocks;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlockBehaviourProperties {

    public static final BlockBehaviour.Properties LAMP_PROPERTIES = BlockBehaviour.Properties.of()
            .sound(SoundType.GLASS)
            .lightLevel((blockState) -> {
                return blockState.getValue(PropagatingRedstoneLampBlock.SIGNAL_STRENGTH) > 0 ? 15 : 0;
            })
            .strength(0.3F)
            .isValidSpawn((state, getter, blockPos, entityType) -> true)
            .isRedstoneConductor((state, getter, blockpos) -> false);
}
