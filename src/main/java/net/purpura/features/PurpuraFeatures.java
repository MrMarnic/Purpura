package net.purpura.features;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.feature.template.BlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleTest;
import net.minecraft.world.gen.foliageplacer.SpruceFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;
import net.purpura.Purpura;

/**
 * Copyright (c) 09.09.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */
public class PurpuraFeatures {
    //public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> SPRUCE = register("spruce", Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Features.States.SPRUCE_LOG), new SimpleBlockStateProvider(Features.States.SPRUCE_LEAVES), new SpruceFoliagePlacer(FeatureSpread.of(2, 1), FeatureSpread.of(0, 2), FeatureSpread.of(1, 1)), new StraightTrunkPlacer(5, 2, 1), new TwoLayerFeature(2, 0, 2))).ignoreVines().build()));

    public static final ConfiguredFeature<BaseTreeFeatureConfig, ?> PURPURA_TREE = register("purpura_tree",Feature.TREE.configured((new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Purpura.PURPURA_LOG.get().defaultBlockState()), new SimpleBlockStateProvider(Purpura.PURPURA_LEAVES.get().defaultBlockState()),
            new SpruceFoliagePlacer(FeatureSpread.of(2, 1), FeatureSpread.of(0, 2), FeatureSpread.of(1, 1)), new StraightTrunkPlacer(5, 2, 1),
            new TwoLayerFeature(2, 0, 2))).ignoreVines().build()));

    public static final ConfiguredFeature<?, ?> ORE_PURPURIUM = register("ore_purpurium", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Purpura.PURPURIUM_ORE.get().defaultBlockState(), 9)).range(64).squared().count(20));
    public static final ConfiguredFeature<?, ?> ORE_SOLARIUM = register("ore_solarium", Feature.ORE.configured(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NETHERRACK, Purpura.SOLARIUM_ORE.get().defaultBlockState(), 9)).range(64).squared().count(20));
    public static final ConfiguredFeature<?, ?> ORE_KUNZIT = register("ore_kunzit", Feature.ORE.configured(new OreFeatureConfig(new BlockMatchRuleTest(Purpura.PURPURRACK.get()), Purpura.KUNZIT_ORE.get().defaultBlockState(), 8)).range(16).squared());
    public static final ConfiguredFeature<?, ?> ORE_TETRAEDIT = register("ore_tetraedit", Feature.ORE.configured(new OreFeatureConfig(new BlockMatchRuleTest(Purpura.PURPURRACK.get()), Purpura.TETRAEDIT_ORE.get().defaultBlockState(), 9)).range(64).squared().count(20));

    public static <FC extends IFeatureConfig>ConfiguredFeature<FC,?> register(String name, ConfiguredFeature<FC,?> feature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,name,feature);
    }
}
