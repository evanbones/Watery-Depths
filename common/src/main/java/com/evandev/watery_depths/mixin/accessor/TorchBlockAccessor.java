package com.evandev.watery_depths.mixin.accessor;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TorchBlock.class)
public interface TorchBlockAccessor {

    @Invoker("<init>")
    static TorchBlock createTorchBlock(BlockBehaviour.Properties properties, ParticleOptions particleOptions) {
        throw new UnsupportedOperationException();
    }
}
