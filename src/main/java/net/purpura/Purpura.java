package net.purpura;

import com.google.common.collect.Sets;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.purpura.armor.PurpuraArmorMaterial;
import net.purpura.armor.PurpuraItemTier;
import net.purpura.blocks.DayChangerBlock;
import net.purpura.blocks.DayChangerTileEntity;
import net.purpura.blocks.DayChangerTileEntityRenderer;
import net.purpura.items.HammerItem;
import net.purpura.packet.PurpuraPacketHandler;

import java.util.HashMap;

/**
 * Copyright (c) 07.09.2021
 * Developed by MrMarnic
 * GitHub: https://github.com/MrMarnic
 */

@Mod("purpura")
public class Purpura {

    public static final String MODID = "purpura";

    public static ItemGroup PURPURA_ITEMS = new ItemGroup("purpura_items") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Purpura.KUNZIT.get());
        }
    };
    
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> PURPURIUM = ITEMS.register("purpurium",()-> new Item(new Item.Properties().tab(PURPURA_ITEMS).tab(PURPURA_ITEMS)));



    public static final RegistryObject<Item> SOLARIUM = ITEMS.register("solarium",()-> new Item(new Item.Properties().tab(PURPURA_ITEMS)));

    public static final RegistryObject<Item> HAMMER = ITEMS.register("hammer", () -> new HammerItem(2,3,PurpuraItemTier.TETRAEDIT_KUNZIT));
    public static final RegistryObject<Item> KUNZIT_HAMMER = ITEMS.register("kunzit_hammer", () -> new HammerItem(2,3,PurpuraItemTier.KUNZIT));

    public static final RegistryObject<Item> KUNZIT = ITEMS.register("kunzit",()-> new Item(new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> KUNZIT_HELMET = ITEMS.register("kunzit_helmet",()-> new ArmorItem(PurpuraArmorMaterial.KUNZIT, EquipmentSlotType.HEAD,new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> KUNZIT_CHESTPLATE = ITEMS.register("kunzit_chestplate",()-> new ArmorItem(PurpuraArmorMaterial.KUNZIT, EquipmentSlotType.CHEST,new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> KUNZIT_LEGGINGS = ITEMS.register("kunzit_leggings",()-> new ArmorItem(PurpuraArmorMaterial.KUNZIT, EquipmentSlotType.LEGS,new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> KUNZIT_BOOTS = ITEMS.register("kunzit_boots",()-> new ArmorItem(PurpuraArmorMaterial.KUNZIT, EquipmentSlotType.FEET,new Item.Properties().tab(PURPURA_ITEMS)));

    public static final RegistryObject<Item> KUNZIT_SWORD = ITEMS.register("kunzit_sword",()-> new SwordItem(PurpuraItemTier.KUNZIT,2,3f,new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> KUNZIT_SHOVEL = ITEMS.register("kunzit_shovel",()-> new ShovelItem(PurpuraItemTier.KUNZIT,2,3f,new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> KUNZIT_AXE = ITEMS.register("kunzit_axe",()-> new AxeItem(PurpuraItemTier.KUNZIT,2,3f,new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> KUNZIT_PICKAXE = ITEMS.register("kunzit_pickaxe",()-> new PickaxeItem(PurpuraItemTier.KUNZIT,2,3f,new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> KUNZIT_HOE = ITEMS.register("kunzit_hoe",()-> new HoeItem(PurpuraItemTier.KUNZIT,2,3f,new Item.Properties().tab(PURPURA_ITEMS)));


    public static final RegistryObject<Item> TETRAEDIT = ITEMS.register("tetraedit",()-> new Item(new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> TETRAEDIT_HELMET = ITEMS.register("tetraedit_helmet",()-> new ArmorItem(PurpuraArmorMaterial.TETRAEDIT, EquipmentSlotType.HEAD,new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> TETRAEDIT_CHESTPLATE = ITEMS.register("tetraedit_chestplate",()-> new ArmorItem(PurpuraArmorMaterial.TETRAEDIT, EquipmentSlotType.CHEST,new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> TETRAEDIT_LEGGINGS = ITEMS.register("tetraedit_leggings",()-> new ArmorItem(PurpuraArmorMaterial.TETRAEDIT, EquipmentSlotType.LEGS,new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> TETRAEDIT_BOOTS = ITEMS.register("tetraedit_boots",()-> new ArmorItem(PurpuraArmorMaterial.TETRAEDIT, EquipmentSlotType.FEET,new Item.Properties().tab(PURPURA_ITEMS)));

    public static final RegistryObject<Item> TETRAEDIT_SWORD = ITEMS.register("tetraedit_sword",()-> new SwordItem(PurpuraItemTier.TETRAEDIT,2,3f,new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> TETRAEDIT_SHOVEL = ITEMS.register("tetraedit_shovel",()-> new ShovelItem(PurpuraItemTier.TETRAEDIT,2,3f,new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> TETRAEDIT_AXE = ITEMS.register("tetraedit_axe",()-> new AxeItem(PurpuraItemTier.TETRAEDIT,2,3f,new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> TETRAEDIT_PICKAXE = ITEMS.register("tetraedit_pickaxe",()-> new PickaxeItem(PurpuraItemTier.TETRAEDIT,2,3f,new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> TETRAEDIT_HOE = ITEMS.register("tetraedit_hoe",()-> new HoeItem(PurpuraItemTier.TETRAEDIT,2,3f,new Item.Properties().tab(PURPURA_ITEMS)));


    public static final RegistryObject<Item> TETRAEDIT_KUNZIT_HELMET = ITEMS.register("tetraedit_kunzit_helmet",()-> new ArmorItem(PurpuraArmorMaterial.TETRAEDIT_KUNZIT, EquipmentSlotType.HEAD,new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> TETRAEDIT_KUNZIT_CHESTPLATE = ITEMS.register("tetraedit_kunzit_chestplate",()-> new ArmorItem(PurpuraArmorMaterial.TETRAEDIT_KUNZIT, EquipmentSlotType.CHEST,new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> TETRAEDIT_KUNZIT_LEGGINGS = ITEMS.register("tetraedit_kunzit_leggings",()-> new ArmorItem(PurpuraArmorMaterial.TETRAEDIT_KUNZIT, EquipmentSlotType.LEGS,new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> TETRAEDIT_KUNZIT_BOOTS = ITEMS.register("tetraedit_kunzit_boots",()-> new ArmorItem(PurpuraArmorMaterial.TETRAEDIT_KUNZIT, EquipmentSlotType.FEET,new Item.Properties().tab(PURPURA_ITEMS)));

    public static final RegistryObject<Item> TETRAEDIT_KUNZIT_SWORD = ITEMS.register("tetraedit_kunzit_sword",()-> new SwordItem(PurpuraItemTier.TETRAEDIT_KUNZIT,2,3f,new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> TETRAEDIT_KUNZIT_SHOVEL = ITEMS.register("tetraedit_kunzit_shovel",()-> new ShovelItem(PurpuraItemTier.TETRAEDIT_KUNZIT,2,3f,new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> TETRAEDIT_KUNZIT_AXE = ITEMS.register("tetraedit_kunzit_axe",()-> new AxeItem(PurpuraItemTier.TETRAEDIT_KUNZIT,2,3f,new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> TETRAEDIT_KUNZIT_PICKAXE = ITEMS.register("tetraedit_kunzit_pickaxe",()-> new PickaxeItem(PurpuraItemTier.TETRAEDIT_KUNZIT,2,3f,new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> TETRAEDIT_KUNZIT_HOE = ITEMS.register("tetraedit_kunzit_hoe",()-> new HoeItem(PurpuraItemTier.TETRAEDIT_KUNZIT,2,3f,new Item.Properties().tab(PURPURA_ITEMS)));

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);

    public static final RegistryObject<Block> PURPURIUM_ORE = BLOCKS.register("purpurium_ore",() -> new Block(AbstractBlock.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> PURPURIUM_BLOCK = BLOCKS.register("purpurium_block",() -> new Block(AbstractBlock.Properties.of(Material.METAL)));
    public static final RegistryObject<Block> SOLARIUM_ORE = BLOCKS.register("solarium_ore",() -> new Block(AbstractBlock.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> SOLARIUM_BLOCK = BLOCKS.register("solarium_block",() -> new Block(AbstractBlock.Properties.of(Material.METAL)));
    public static final RegistryObject<Block> KUNZIT_ORE = BLOCKS.register("kunzit_ore",() -> new Block(AbstractBlock.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> KUNZIT_BLOCK = BLOCKS.register("kunzit_block",() -> new Block(AbstractBlock.Properties.of(Material.METAL)));
    public static final RegistryObject<Block> TETRAEDIT_ORE = BLOCKS.register("tetraedit_ore",() -> new Block(AbstractBlock.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> TETRAEDIT_BLOCK = BLOCKS.register("tetraedit_block",() -> new Block(AbstractBlock.Properties.of(Material.METAL)));
    public static final RegistryObject<Block> PURPURRACK = BLOCKS.register("purpurrack",() -> new Block(AbstractBlock.Properties.of(Material.STONE)));
    public static final RegistryObject<Block> DAY_CHANGER = BLOCKS.register("day_changer", DayChangerBlock::new);

    /**Item Blocks**/
    public static final RegistryObject<Item> PURPURIUM_ORE_ITEM = ITEMS.register("purpurium_ore",() -> new BlockItem(PURPURIUM_ORE.get(),new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> PURPURIUM_BLOCK_ITEM = ITEMS.register("purpurium_block",() -> new BlockItem(PURPURIUM_BLOCK.get(),new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> SOLARIUM_ORE_ITEM = ITEMS.register("solarium_ore",() -> new BlockItem(SOLARIUM_ORE.get(),new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> SOLARIUM_BLOCK_ITEM = ITEMS.register("solarium_block",() -> new BlockItem(SOLARIUM_BLOCK.get(),new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> KUNZIT_ORE_ITEM = ITEMS.register("kunzit_ore",() -> new BlockItem(KUNZIT_ORE.get(),new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> KUNZIT_BLOCK_ITEM = ITEMS.register("kunzit_block",() -> new BlockItem(KUNZIT_BLOCK.get(),new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> TETRAEDIT_ORE_ITEM = ITEMS.register("tetraedit_ore",() -> new BlockItem(TETRAEDIT_ORE.get(),new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> TETRAEDIT_BLOCK_ITEM = ITEMS.register("tetraedit_block",() -> new BlockItem(TETRAEDIT_BLOCK.get(),new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> PURPURRACK_ITEM = ITEMS.register("purpurrack",() -> new BlockItem(PURPURRACK.get(),new Item.Properties().tab(PURPURA_ITEMS)));
    public static final RegistryObject<Item> DAY_CHANGER_ITEM = ITEMS.register("day_changer",() -> new BlockItem(DAY_CHANGER.get(),new Item.Properties().tab(PURPURA_ITEMS)));


    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MODID);

    public static final RegistryObject<TileEntityType<DayChangerTileEntity>> DAY_CHANGER_TILE_ENTITY = TILE_ENTITIES.register("day_changer",() -> TileEntityType.Builder.of(DayChangerTileEntity::new,DAY_CHANGER.get()).build(null));

    public static HashMap<DimensionType,Boolean> DAY_CHANGER_USED = new HashMap<>();

    public Purpura() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::onClientSetup);
        bus.addListener(this::onCommonSetup);

        BLOCKS.register(bus);
        ITEMS.register(bus);
        TILE_ENTITIES.register(bus);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        PurpuraPacketHandler.registerPackets();
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT,() -> () -> {
            ClientRegistry.bindTileEntityRenderer(DAY_CHANGER_TILE_ENTITY.get(), DayChangerTileEntityRenderer::new);
        });
    }
}
