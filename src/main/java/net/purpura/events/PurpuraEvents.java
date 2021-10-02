package net.purpura.events;

import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.PortalSize;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.ItemTier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.EndCityStructure;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.purpura.Purpura;
import net.purpura.blocks.PurpuraLeavesBlockColor;
import net.purpura.blocks.PurpuraPortalBlock;
import net.purpura.blocks.PurpuraPortalSize;
import net.purpura.features.PurpuraFeatures;
import net.purpura.tree.PurpuraTree;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Copyright (c) 08.09.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */

@Mod.EventBusSubscriber
public class PurpuraEvents {

    @SubscribeEvent
    public static void playerTickEvent(TickEvent.PlayerTickEvent e) {
        if(e.side == LogicalSide.SERVER) {
            if(PurpuraPortalBlock.PORTAL_COOLDOWN.containsKey(e.player.getUUID())) {
                int ticks = PurpuraPortalBlock.PORTAL_COOLDOWN.get(e.player.getUUID());
                PurpuraPortalBlock.PORTAL_COOLDOWN.put(e.player.getUUID(),ticks-1);
                if(ticks <= 0) {
                    PurpuraPortalBlock.PORTAL_COOLDOWN.remove(e.player.getUUID());
                }
            }

            if(e.player.isInWaterOrRain()) {
                if(!e.player.hasEffect(Effects.POISON)) {
                    e.player.addEffect(new EffectInstance(Effects.POISON,20,2));
                }
            }
        }
    }

    @SubscribeEvent
    public static void blockPlacedEvent(BlockEvent.EntityPlaceEvent e) {
        if(e.getEntity() instanceof ServerPlayerEntity) {
            if(e.getPlacedBlock().getBlock() instanceof AbstractFireBlock) {
                if(e.getEntity().level.getBlockState(e.getPos().below()).getBlock() == Purpura.PURPURIUM_BLOCK.get()) {
                    Optional<PurpuraPortalSize> optional = PurpuraPortalSize.findEmptyPortalShape(e.getWorld(), e.getPos(), Direction.Axis.X);
                    if (optional.isPresent()) {
                        optional.get().createPortalBlocks();
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void biomeLoadingEvent(BiomeLoadingEvent e) {
        if(e.getCategory() == Biome.Category.NETHER) {
            List<Supplier<ConfiguredFeature<?,?>>> undergroundFeatures = e.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES);
            undergroundFeatures.add(() -> PurpuraFeatures.ORE_SOLARIUM);
        } else if(e.getName().toString().equalsIgnoreCase("purpura:purpura_biome") | e.getName().toString().equalsIgnoreCase("purpura:purpura_biome_wasteland")) {
            List<Supplier<ConfiguredFeature<?,?>>> feature = e.getGeneration().getFeatures(GenerationStage.Decoration.VEGETAL_DECORATION);
            feature.add(() -> PurpuraFeatures.PURPURA_TREE.decorated(Features.Placements.HEIGHTMAP_SQUARE).decorated(Placement.COUNT_EXTRA.configured(new AtSurfaceWithExtraConfig(5,0.1f,1))));
            List<Supplier<ConfiguredFeature<?,?>>> undergroundFeatures = e.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES);
            undergroundFeatures.add(() -> PurpuraFeatures.ORE_KUNZIT);
            undergroundFeatures.add(() -> PurpuraFeatures.ORE_TETRAEDIT);
        }else if(!(e.getCategory() == Biome.Category.THEEND) && !(e.getCategory() == Biome.Category.NETHER)) {
            List<Supplier<ConfiguredFeature<?,?>>> undergroundFeatures = e.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES);
            undergroundFeatures.add(() -> PurpuraFeatures.ORE_PURPURIUM);
        }
    }
}
