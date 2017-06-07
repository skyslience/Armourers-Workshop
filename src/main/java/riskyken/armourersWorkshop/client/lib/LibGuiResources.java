package riskyken.armourersWorkshop.client.lib;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import riskyken.armourersWorkshop.common.lib.LibModInfo;

@SideOnly(Side.CLIENT)
public class LibGuiResources {
    
    private static final String PREFIX_RESOURCE = LibModInfo.ID.toLowerCase() + ":textures/gui/";
    
    public static final String WARDROBE_1 = PREFIX_RESOURCE + "wardrobe1.png";
    public static final String WARDROBE_2 = PREFIX_RESOURCE + "wardrobe2.png";
}
