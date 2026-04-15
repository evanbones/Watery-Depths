package com.evandev.watery_depths.world.feature.tree.custom;

import com.evandev.watery_depths.Constants;
import com.evandev.watery_depths.mixin.accessor.TreeDecoratorTypeAccessor;
import com.evandev.watery_depths.registration.util.RegistrationProvider;
import com.evandev.watery_depths.registration.util.RegistryObject;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class ModDecoratorTypes {
    public static final RegistrationProvider<TreeDecoratorType<?>> DECORATORS = RegistrationProvider.get(BuiltInRegistries.TREE_DECORATOR_TYPE, Constants.MOD_ID);

    public static final RegistryObject<TreeDecoratorType<CypressSkirtDecorator>> CYPRESS_SKIRT = DECORATORS.register(
            "cypress_skirt",
            () -> TreeDecoratorTypeAccessor.createTreeDecoratorType(CypressSkirtDecorator.CODEC)
    );

    public static void load() {
    }
}