package com.evandev.watery_depths.mixin.accessor;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(WallTorchBlock.class)
public interface WallTorchBlockAccessor {

    @Invoker("<init>")
    static WallTorchBlock createWallTorchBlock(BlockBehaviour.Properties properties, ParticleOptions particleOptions) {
        throw new UnsupportedOperationException();
    }
}
