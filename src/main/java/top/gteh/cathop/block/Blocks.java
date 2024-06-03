package top.gteh.cathop.block;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import top.gteh.cathop.Cathop;

public class Blocks {

    private static Item CCR_LOGO_ITEM;

    private static final ItemGroup LOGO_GROUP = FabricItemGroupBuilder
            .create(new Identifier(Cathop.MOD_ID, "logo_group"))
            .icon(() -> new ItemStack(CCR_LOGO_ITEM))
            .build();

    private static Block registerBlock(String name, Block block, ItemGroup itemGroup, boolean registerItem) {
        if (registerItem) {
            registerBlockItem(name, block, itemGroup);
        }
        return Registry.register(Registry.BLOCK, new Identifier(Cathop.MOD_ID, name), block);
    }

    private static Item registerBlockItem(String name, Block block, ItemGroup itemGroup) {
        return Registry.register(Registry.ITEM, new Identifier(Cathop.MOD_ID, name), new BlockItem(block, new FabricItemSettings().group(itemGroup)));
    }

    public static void logoBlockRegister() {
        Block CCR_LOGO = registerBlock("ccr_logo", new LogoBlock(), LOGO_GROUP, false);
        CCR_LOGO_ITEM = registerBlockItem("ccr_logo", CCR_LOGO, LOGO_GROUP);
        registerBlock("cotai_logo", new LogoBlock(), LOGO_GROUP, true);
        registerBlock("cr_logo", new LogoBlock(), LOGO_GROUP, true);
        registerBlock("first_bus_logo", new LogoBlock(), LOGO_GROUP, true);
        registerBlock("hunan_buses_logo", new LogoBlock(), LOGO_GROUP, true);
        registerBlock("lr_logo", new LogoBlock(), LOGO_GROUP, true);
        registerBlock("mxrt_logo", new LogoBlock(), LOGO_GROUP, true);
        registerBlock("qxmrt_logo", new LogoBlock(), LOGO_GROUP, true);
        registerBlock("sm_dragon_logo", new LogoBlock(), LOGO_GROUP, true);
        registerBlock("sm_logo", new LogoBlock(), LOGO_GROUP, true);
        registerBlock("vcr_logo", new LogoBlock(), LOGO_GROUP, true);
    }
}
