package riskyken.armourersWorkshop.common.skin.type;

import org.lwjgl.opengl.GL11;

import riskyken.armourersWorkshop.api.common.skin.type.ISkinType;
import riskyken.armourersWorkshop.client.model.armourer.ModelHand;
import riskyken.armourersWorkshop.common.skin.Point3D;
import riskyken.armourersWorkshop.common.skin.Rectangle3D;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SkinBowPartFrame1 extends AbstractSkinPartTypeBase {
    
    public SkinBowPartFrame1(ISkinType baseType) {
        super(baseType);
        this.buildingSpace = new Rectangle3D(-6, -20, -18, 12, 40, 28);
        this.guideSpace = new Rectangle3D(-2, -2, 2, 4, 4, 8);
        this.offset = new Point3D(0, 0, 0);
    }

    @Override
    public String getPartName() {
        return "frame1";
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void renderBuildingGuide(float scale, boolean showSkinOverlay, boolean showHelper) {
        GL11.glTranslated(0, this.buildingSpace.getY() * scale, 0);
        GL11.glTranslated(0, -this.guideSpace.getY() * scale, 0);
        ModelHand.MODEL.render(scale);
        GL11.glTranslated(0, this.guideSpace.getY() * scale, 0);
        GL11.glTranslated(0, -this.buildingSpace.getY() * scale, 0);
    }
}