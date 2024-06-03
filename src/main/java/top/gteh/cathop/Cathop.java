package top.gteh.cathop;

import net.fabricmc.api.ModInitializer;
import top.gteh.cathop.block.Blocks;
import top.gteh.cathop.commands.SyncSystemMap;

public class Cathop implements ModInitializer {

    public static String MOD_ID = "cathop";

    @Override
    public void onInitialize() {
        Blocks.logoBlockRegister();
        SyncSystemMap.register();
    }
}
