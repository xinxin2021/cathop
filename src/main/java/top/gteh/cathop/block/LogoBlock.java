package top.gteh.cathop.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;

public class LogoBlock extends Block {

    public LogoBlock() {
        super(FabricBlockSettings.of(Material.METAL)
                .hardness(4.0f)
                .requiresTool()
                .luminance(state -> 10));
    }
}
