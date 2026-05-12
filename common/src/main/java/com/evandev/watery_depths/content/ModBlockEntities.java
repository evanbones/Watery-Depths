package com.evandev.watery_depths.content;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.block.entity.TubewormBlockEntity;
import com.evandev.watery_depths.platform.Services;
import com.evandev.watery_depths.registration.util.RegistrationProvider;
import com.evandev.watery_depths.registration.util.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
    public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITIES = RegistrationProvider.get(BuiltInRegistries.BLOCK_ENTITY_TYPE, Constants.MOD_ID);

    public static final RegistryObject<BlockEntityType<TubewormBlockEntity>> TUBEWORM = BLOCK_ENTITIES.register("tubeworm",
            () -> Services.PLATFORM.createBlockEntityType(
                    TubewormBlockEntity::new,
                    ModBlocks.TUBEWORM.get()
            ));

    public static void load() {
    }

}