package com.github.lehjr.powersuits.basemod;


import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleCategory;
import com.github.lehjr.numina.util.capabilities.module.powermodule.EnumModuleTarget;
import com.github.lehjr.numina.util.capabilities.module.powermodule.IConfig;
import com.github.lehjr.numina.util.capabilities.module.powermodule.PowerModuleCapability;
import com.github.lehjr.numina.util.capabilities.module.rightclick.IRightClickModule;
import com.github.lehjr.numina.util.capabilities.module.rightclick.RightClickModule;
import com.github.lehjr.powersuits.block.LuxCapacitorBlock;
import com.github.lehjr.powersuits.block.WorkBenchBlock;
import com.github.lehjr.powersuits.config.MPSSettings;
import com.github.lehjr.powersuits.constants.MPSConstants;
import com.github.lehjr.powersuits.constants.MPSRegistryNames;
import com.github.lehjr.powersuits.container.MPAWorkbenchContainer;
import com.github.lehjr.powersuits.container.MPAWorkbenchContainerProvider;
import com.github.lehjr.powersuits.entity.LuxCapacitorEntity;
import com.github.lehjr.powersuits.entity.PlasmaBallEntity;
import com.github.lehjr.powersuits.entity.RailgunBoltEntity;
import com.github.lehjr.powersuits.entity.SpinningBladeEntity;
import com.github.lehjr.powersuits.item.armor.PowerArmorBoots;
import com.github.lehjr.powersuits.item.armor.PowerArmorChestplate;
import com.github.lehjr.powersuits.item.armor.PowerArmorHelmet;
import com.github.lehjr.powersuits.item.armor.PowerArmorLeggings;
import com.github.lehjr.powersuits.item.module.armor.IronPlatingModule;
import com.github.lehjr.powersuits.item.module.armor.LeatherPlatingModule;
import com.github.lehjr.powersuits.item.module.cosmetic.TransparentArmorModule;
import com.github.lehjr.powersuits.item.module.energy_generation.AdvancedSolarGeneratorModule;
import com.github.lehjr.powersuits.item.module.energy_generation.BasicSolarGeneratorModule;
import com.github.lehjr.powersuits.item.module.energy_generation.KineticGeneratorModule;
import com.github.lehjr.powersuits.item.module.energy_generation.ThermalGeneratorModule;
import com.github.lehjr.powersuits.item.module.environmental.*;
import com.github.lehjr.powersuits.item.module.miningenhancement.*;
import com.github.lehjr.powersuits.item.module.movement.*;
import com.github.lehjr.powersuits.item.module.special.InvisibilityModule;
import com.github.lehjr.powersuits.item.module.special.MagnetModule;
import com.github.lehjr.powersuits.item.module.tool.*;
import com.github.lehjr.powersuits.item.module.vision.BinocularsModule;
import com.github.lehjr.powersuits.item.module.vision.NightVisionModule;
import com.github.lehjr.powersuits.item.module.weapon.*;
import com.github.lehjr.powersuits.item.tool.PowerFist;
import com.github.lehjr.powersuits.tile_entity.LuxCapacitorTileEntity;
import com.github.lehjr.powersuits.tile_entity.WorkBenchTileEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

public class MPSObjects {
    public static final MPSCreativeTab creativeTab = new MPSCreativeTab();
    public static final Item.Properties singleStack = new Item.Properties()
            .maxStackSize(1)
            .group(MPSObjects.creativeTab)
            .defaultMaxDamage(-1)
            .setNoRepair();

    public static final Item.Properties fullStack = new Item.Properties()
            .group(MPSObjects.creativeTab)
            .defaultMaxDamage(-1)
            .setNoRepair();
    /**
     * Blocks ------------------------------------------------------------------------------------
     */
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MPSConstants.MOD_ID);

    public static final RegistryObject<WorkBenchBlock> WORKBENCH_BLOCK = BLOCKS.register(MPSRegistryNames.WORKBENCH,
            () -> new WorkBenchBlock());

    public static final RegistryObject<LuxCapacitorBlock> LUX_CAPACITOR_BLOCK = BLOCKS.register(MPSRegistryNames.LUX_CAPACITOR,
            () -> new LuxCapacitorBlock());


    /**
     * Tile Entity Types -------------------------------------------------------------------------
     */
    public static final DeferredRegister<TileEntityType<?>> TILE_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, MPSConstants.MOD_ID);

    public static final RegistryObject<TileEntityType<WorkBenchTileEntity>> WORKBENCH_TILE_TYPE = TILE_TYPES.register(MPSRegistryNames.WORKBENCH,
            () -> TileEntityType.Builder.create(WorkBenchTileEntity::new, WORKBENCH_BLOCK.get()).build(null));

    public static final RegistryObject<TileEntityType<LuxCapacitorTileEntity>> LUX_CAP_TILE_TYPE = TILE_TYPES.register(MPSRegistryNames.LUX_CAPACITOR,
            () -> TileEntityType.Builder.create(LuxCapacitorTileEntity::new, LUX_CAPACITOR_BLOCK.get()).build(null));

    /**
     * Entity Types ------------------------------------------------------------------------------
     */
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, MPSConstants.MOD_ID);

    public static final RegistryObject<EntityType<LuxCapacitorEntity>> LUX_CAPACITOR_ENTITY_TYPE = ENTITY_TYPES.register(MPSRegistryNames.LUX_CAPACITOR,
            ()-> EntityType.Builder.<LuxCapacitorEntity>create(LuxCapacitorEntity::new, EntityClassification.MISC)
                    .size(0.25F, 0.25F)
                    .build(new ResourceLocation(MPSConstants.MOD_ID, MPSRegistryNames.LUX_CAPACITOR).toString()));

    public static final RegistryObject<EntityType<SpinningBladeEntity>> SPINNING_BLADE_ENTITY_TYPE = ENTITY_TYPES.register(MPSRegistryNames.SPINNING_BLADE,
            ()-> EntityType.Builder.<SpinningBladeEntity>create(SpinningBladeEntity::new, EntityClassification.MISC)
                    .size(0.25F, 0.25F) // FIXME! check size
                    .build(new ResourceLocation(MPSConstants.MOD_ID, MPSRegistryNames.SPINNING_BLADE).toString()));

    public static final RegistryObject<EntityType<PlasmaBallEntity>> PLASMA_BALL_ENTITY_TYPE = ENTITY_TYPES.register(MPSRegistryNames.PLASMA_BALL,
            ()-> EntityType.Builder.<PlasmaBallEntity>create(PlasmaBallEntity::new, EntityClassification.MISC)
//                    .size(0.25F, 0.25F)
                    .build(new ResourceLocation(MPSConstants.MOD_ID, MPSRegistryNames.PLASMA_BALL).toString()));

    public static final RegistryObject<EntityType<RailgunBoltEntity>> RAILGUN_BOLT_ENTITY_TYPE = ENTITY_TYPES.register(MPSRegistryNames.RAILGUN_BOLT,
            ()-> EntityType.Builder.<RailgunBoltEntity>create(RailgunBoltEntity::new, EntityClassification.MISC)
                    .size(0.25F, 0.25F)
                    .build(new ResourceLocation(MPSConstants.MOD_ID, MPSRegistryNames.RAILGUN_BOLT).toString()));

    /**
     * Items -------------------------------------------------------------------------------------
     */
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MPSConstants.MOD_ID);

    /* BlockItems --------------------------------------------------------------------------------- */
    // use directly as a module
    public static final RegistryObject<Item> WORKBENCH_ITEM = ITEMS.register(MPSRegistryNames.WORKBENCH,
            () -> new BlockItem(WORKBENCH_BLOCK.get(), fullStack) {

                @Nullable
                @Override
                public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
                    return new CapProvider(stack);
                }

                class CapProvider implements ICapabilityProvider {
                    ItemStack module;
                    IRightClickModule rightClick;

                    public CapProvider(@Nonnull ItemStack module) {
                        this.module = module;
                        this.rightClick = new RightClickie(module, EnumModuleCategory.TOOL, EnumModuleTarget.TOOLONLY, MPSSettings::getModuleConfig);
                    }

                    @Nonnull
                    @Override
                    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                        return PowerModuleCapability.POWER_MODULE.orEmpty(cap, LazyOptional.of(() -> rightClick));
                    }

                    class RightClickie extends RightClickModule {
                        public RightClickie(@Nonnull ItemStack module, EnumModuleCategory category, EnumModuleTarget target, Callable<IConfig> config) {
                            super(module, category, target, config);
                        }

                        @Override
                        public ActionResult onItemRightClick(ItemStack itemStackIn, World worldIn, PlayerEntity playerIn, Hand hand) {
                            if (!worldIn.isRemote()) {
                                NetworkHooks.openGui((ServerPlayerEntity) playerIn, new MPAWorkbenchContainerProvider(0), (buffer) -> buffer.writeInt(0));
                            }
                            return super.onItemRightClick(itemStackIn, worldIn, playerIn, hand);
                        }
                    }
                }
            } );

    public static final RegistryObject<Item> LUX_CAPACITOR_ITEM = ITEMS.register(MPSRegistryNames.LUX_CAPACITOR,
            () -> new BlockItem(LUX_CAPACITOR_BLOCK.get(), fullStack));

    /* Armor -------------------------------------------------------------------------------------- */
    public static final RegistryObject<Item> POWER_ARMOR_HELMET = ITEMS.register(MPSRegistryNames.POWER_ARMOR_HELMET,
            () -> new PowerArmorHelmet());

    public static final RegistryObject<Item> POWER_ARMOR_CHESTPLATE = ITEMS.register(MPSRegistryNames.POWER_ARMOR_CHESTPLATE,
            () -> new PowerArmorChestplate());

    public static final RegistryObject<Item> POWER_ARMOR_LEGGINGS = ITEMS.register(MPSRegistryNames.POWER_ARMOR_LEGGINGS,
            () -> new PowerArmorLeggings());

    public static final RegistryObject<Item> POWER_ARMOR_BOOTS = ITEMS.register(MPSRegistryNames.POWER_ARMOR_BOOTS,
            () -> new PowerArmorBoots());

    /* HandHeld ----------------------------------------------------------------------------------- */
    public static final RegistryObject<Item> POWER_FIST = ITEMS.register(MPSRegistryNames.POWER_FIST,
            () -> new PowerFist());

    /* Modules ------------------------------------------------------------------------------------ */
    // Armor --------------------------------------------------------------------------------------
    public static final RegistryObject<Item> LEATHER_PLATING_MODULE = registerModule(MPSRegistryNames.LEATHER_PLATING_MODULE, new LeatherPlatingModule());
    public static final RegistryObject<Item> IRON_PLATING_MODULE = registerModule(MPSRegistryNames.IRON_PLATING_MODULE, new IronPlatingModule());
    public static final RegistryObject<Item> DIAMOND_PLATING_MODULE = registerModule(MPSRegistryNames.DIAMOND_PLATING_MODULE, new IronPlatingModule());
    public static final RegistryObject<Item> ENERGY_SHIELD_MODULE = registerModule(MPSRegistryNames.ENERGY_SHIELD_MODULE, new IronPlatingModule());

    // Cosmetic -----------------------------------------------------------------------------------
    public static final RegistryObject<Item> TRANSPARENT_ARMOR_MODULE = registerModule(MPSRegistryNames.TRANSPARENT_ARMOR_MODULE, new TransparentArmorModule());

    // Energy Generation --------------------------------------------------------------------------
    public static final RegistryObject<Item> SOLAR_GENERATOR_MODULE = registerModule(MPSRegistryNames.SOLAR_GENERATOR_MODULE, new BasicSolarGeneratorModule());
    public static final RegistryObject<Item> ADVANCED_SOLAR_GENERATOR_MODULE = registerModule(MPSRegistryNames.ADVANCED_SOLAR_GENERATOR_MODULE, new AdvancedSolarGeneratorModule());
    public static final RegistryObject<Item> KINETIC_GENERATOR_MODULE = registerModule(MPSRegistryNames.KINETIC_GENERATOR_MODULE, new KineticGeneratorModule());
    public static final RegistryObject<Item> THERMAL_GENERATOR_MODULE = registerModule(MPSRegistryNames.THERMAL_GENERATOR_MODULE, new ThermalGeneratorModule());

    // Environmental ------------------------------------------------------------------------------
    public static final RegistryObject<Item> AUTO_FEEDER_MODULE = registerModule(MPSRegistryNames.AUTO_FEEDER_MODULE, new AutoFeederModule());
    public static final RegistryObject<Item> COOLING_SYSTEM_MODULE = registerModule(MPSRegistryNames.COOLING_SYSTEM_MODULE, new CoolingSystemModule());
    public static final RegistryObject<Item> FLUID_TANK_MODULE = registerModule(MPSRegistryNames.FLUID_TANK_MODULE, new FluidTankModule());
    public static final RegistryObject<Item> MOB_REPULSOR_MODULE = registerModule(MPSRegistryNames.MOB_REPULSOR_MODULE, new MobRepulsorModule());
    public static final RegistryObject<Item> WATER_ELECTROLYZER_MODULE = registerModule(MPSRegistryNames.WATER_ELECTROLYZER_MODULE, new WaterElectrolyzerModule());

    // Mining Enhancements ------------------------------------------------------------------------
    public static final RegistryObject<Item> AOE_PICK_UPGRADE_MODULE = registerModule(MPSRegistryNames.AOE_PICK_UPGRADE_MODULE, new AOEPickUpgradeModule());
    public static final RegistryObject<Item> AQUA_AFFINITY_MODULE = registerModule(MPSRegistryNames.AQUA_AFFINITY_MODULE, new AquaAffinityModule());
    public static final RegistryObject<Item> SILK_TOUCH_MODULE = registerModule(MPSRegistryNames.SILK_TOUCH_MODULE, new SilkTouchModule());
    public static final RegistryObject<Item> FORTUNE_MODULE = registerModule(MPSRegistryNames.FORTUNE_MODULE, new FortuneModule());
    public static final RegistryObject<Item> VEIN_MINER_MODULE = registerModule(MPSRegistryNames.VEIN_MINER_MODULE, new VeinMinerModule());

    // Movement -----------------------------------------------------------------------------------
    public static final RegistryObject<Item> BLINK_DRIVE_MODULE = registerModule(MPSRegistryNames.BLINK_DRIVE_MODULE, new BlinkDriveModule());
    public static final RegistryObject<Item> CLIMB_ASSIST_MODULE = registerModule(MPSRegistryNames.CLIMB_ASSIST_MODULE, new ClimbAssistModule());
    public static final RegistryObject<Item> DIMENSIONAL_RIFT_MODULE = registerModule(MPSRegistryNames.DIMENSIONAL_RIFT_MODULE, new DimensionalRiftModule());
    public static final RegistryObject<Item> FLIGHT_CONTROL_MODULE = registerModule(MPSRegistryNames.FLIGHT_CONTROL_MODULE, new FlightControlModule());
    public static final RegistryObject<Item> GLIDER_MODULE = registerModule(MPSRegistryNames.GLIDER_MODULE, new GliderModule());
    public static final RegistryObject<Item> JETBOOTS_MODULE = registerModule(MPSRegistryNames.JETBOOTS_MODULE, new JetBootsModule());
    public static final RegistryObject<Item> JETPACK_MODULE = registerModule(MPSRegistryNames.JETPACK_MODULE, new JetPackModule());
    public static final RegistryObject<Item> JUMP_ASSIST_MODULE = registerModule(MPSRegistryNames.JUMP_ASSIST_MODULE, new JumpAssistModule());
    public static final RegistryObject<Item> PARACHUTE_MODULE = registerModule(MPSRegistryNames.PARACHUTE_MODULE, new ParachuteModule());
    public static final RegistryObject<Item> SHOCK_ABSORBER_MODULE = registerModule(MPSRegistryNames.SHOCK_ABSORBER_MODULE, new ShockAbsorberModule());
    public static final RegistryObject<Item> SPRINT_ASSIST_MODULE = registerModule(MPSRegistryNames.SPRINT_ASSIST_MODULE, new SprintAssistModule());
    public static final RegistryObject<Item> SWIM_BOOST_MODULE = registerModule(MPSRegistryNames.SWIM_BOOST_MODULE, new SwimAssistModule());

    // Special ------------------------------------------------------------------------------------
    public static final RegistryObject<Item> ACTIVE_CAMOUFLAGE_MODULE = registerModule(MPSRegistryNames.ACTIVE_CAMOUFLAGE_MODULE, new InvisibilityModule());
    public static final RegistryObject<Item> MAGNET_MODULE = registerModule(MPSRegistryNames.MAGNET_MODULE, new MagnetModule());

    // Vision -------------------------------------------------------------------------------------
    public static final RegistryObject<Item> BINOCULARS_MODULE = registerModule(MPSRegistryNames.BINOCULARS_MODULE, new BinocularsModule());
    public static final RegistryObject<Item> NIGHT_VISION_MODULE = registerModule(MPSRegistryNames.NIGHT_VISION_MODULE, new NightVisionModule());

    // Tools --------------------------------------------------------------------------
    public static final RegistryObject<Item> AXE_MODULE = registerModule(MPSRegistryNames.AXE_MODULE, new AxeModule());
    public static final RegistryObject<Item> DIAMOND_PICK_UPGRADE_MODULE = registerModule(MPSRegistryNames.DIAMOND_PICK_UPGRADE_MODULE, new DiamondPickUpgradeModule());
    public static final RegistryObject<Item> FLINT_AND_STEEL_MODULE = registerModule(MPSRegistryNames.FLINT_AND_STEEL_MODULE, new FlintAndSteelModule());
    public static final RegistryObject<Item> HOE_MODULE = registerModule(MPSRegistryNames.HOE_MODULE, new HoeModule());
    public static final RegistryObject<Item> LEAF_BLOWER_MODULE = registerModule(MPSRegistryNames.LEAF_BLOWER_MODULE, new LeafBlowerModule());
    public static final RegistryObject<Item> LUX_CAPACITOR_MODULE = registerModule(MPSRegistryNames.LUX_CAPACITOR_MODULE, new LuxCapacitorModule());
    public static final RegistryObject<Item> PICKAXE_MODULE = registerModule(MPSRegistryNames.PICKAXE_MODULE, new PickaxeModule());
    public static final RegistryObject<Item> SHEARS_MODULE = registerModule(MPSRegistryNames.SHEARS_MODULE, new ShearsModule());
    public static final RegistryObject<Item> SHOVEL_MODULE = registerModule(MPSRegistryNames.SHOVEL_MODULE, new ShockAbsorberModule());

    // Debug --------------------------------------------------------------------------------------
    // todo

    // Weapons ------------------------------------------------------------------------------------
    public static final RegistryObject<Item> BLADE_LAUNCHER_MODULE = registerModule(MPSRegistryNames.BLADE_LAUNCHER_MODULE, new BladeLauncherModule());
    public static final RegistryObject<Item> LIGHTNING_MODULE = registerModule(MPSRegistryNames.LIGHTNING_MODULE, new LightningModule());
    public static final RegistryObject<Item> MELEE_ASSIST_MODULE = registerModule(MPSRegistryNames.MELEE_ASSIST_MODULE, new MeleeAssistModule());
    public static final RegistryObject<Item> PLASMA_CANNON_MODULE = registerModule(MPSRegistryNames.PLASMA_CANNON_MODULE, new PlasmaCannonModule());
    public static final RegistryObject<Item> RAILGUN_MODULE = registerModule(MPSRegistryNames.RAILGUN_MODULE, new RailgunModule());

    /**
     * Container Types ----------------------------------------------------------------------------
     */
    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, MPSConstants.MOD_ID);

    public static final RegistryObject<ContainerType<MPAWorkbenchContainer>> MPA_WORKBENCH_CONTAINER_TYPE = CONTAINER_TYPES.register(MPSRegistryNames.MPA_WORKBENCH_CONTAINER_TYPE,
            () -> IForgeContainerType.create((windowId, inv, data) -> new MPAWorkbenchContainer(windowId, inv)));


//                    new ContainerType<>(MPAWorkbenchContainer::new)
//            .setRegistryName(MPA_WORKBENCH_CONTAINER_TYPE),
//
//
//    // Crafting Gui
//                new ContainerType<>(MPACraftingContainer::new)
//            .setRegistryName(MPA_CRAFTING_CONTAINER_TYPE__REG_NAME)


////    @ObjectHolder(MOD_ID + ":bolt")
////    public static EntityType<BoltEntity> BOLT_ENTITY_TYPE;
//
//
//

//        @ObjectHolder(MPA_CRAFTING_CONTAINER_TYPE__REG_NAME)
//        public static final ContainerType<MPACraftingContainer> MPA_CRAFTING_CONTAINER_TYPE = null;
//
//        @ObjectHolder(MPA_TINKER_TABLE_CONTAINER_TYPE__REG_NAME)
//        public static final ContainerType<MPAWorkbenchContainer> TINKER_TABLE_CONTAINER_TYPE = null;
//    }




    static RegistryObject<Item> registerModule(String regName, Item item) {
        MPSModules.INSTANCE.addModule(MPSRegistryNames.getRegName(regName));
        return ITEMS.register(regName, () -> item);
    }
}