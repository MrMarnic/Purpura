package net.purpura.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.purpura.Purpura;

/**
 * Copyright (c) 07.09.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class ModItemProvider extends ItemModelProvider {

    public ModItemProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Purpura.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent("purpura","minecraft:item/handheld");
    }
}
