package riskyken.armourersWorkshop.client.helper;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import riskyken.armourersWorkshop.api.common.IPoint3D;
import riskyken.armourersWorkshop.api.common.IRectangle3D;
import riskyken.armourersWorkshop.api.common.skin.data.ISkinPointer;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinPartType;
import riskyken.armourersWorkshop.api.common.skin.type.ISkinType;
import riskyken.armourersWorkshop.client.model.skin.IEquipmentModel;
import riskyken.armourersWorkshop.client.render.SkinModelRenderer;
import riskyken.armourersWorkshop.client.render.SkinPartRenderer;
import riskyken.armourersWorkshop.client.skin.cache.ClientSkinCache;
import riskyken.armourersWorkshop.common.skin.data.Skin;
import riskyken.armourersWorkshop.common.skin.data.SkinPart;
import riskyken.armourersWorkshop.utils.SkinNBTHelper;

@SideOnly(Side.CLIENT)
public final class SkinScaledRenderHelper {
    
    private SkinScaledRenderHelper() {
    }
    
    public static void renderScaledSkinInBox(ItemStack itemStack, int width, int height, boolean showSkinPaint, boolean doLodLoading, boolean requestSkin) {
        if (itemStack == null) {
            return;
        }
        renderScaledSkinInBox(SkinNBTHelper.getSkinPointerFromStack(itemStack), width, height, showSkinPaint, doLodLoading, requestSkin);
    }
    
    public static void renderScaledSkinInBox(ISkinPointer skinPointer, int width, int height, boolean showSkinPaint, boolean doLodLoading, boolean requestSkin) {
        if (skinPointer == null) {
            return;
        }
        renderScaledSkinInBox(ClientSkinCache.INSTANCE.getSkin(skinPointer, requestSkin), skinPointer, width, height, showSkinPaint, doLodLoading);
    }
    
    /**
     * Loop over each part and add it's bounds to a list.
     * 
     * @param bounds
     */
    private static void addBoundsForPart(ArrayList<IRectangle3D> bounds) {
        
    }
    
    public static void renderScaledSkinInBox(Skin skin, ISkinPointer skinPointer, int width, int height, boolean showSkinPaint, boolean doLodLoading) {
        if (skin == null) {
            return;
        }
        ISkinType skinType = skin.getSkinType();
        
        ArrayList<SkinPart> skinParts = skin.getParts();
        IRectangle3D[] partBounds = new IRectangle3D[skinParts.size()];
        IRectangle3D[] paintPartBounds = new IRectangle3D[skinType.getSkinParts().size()];
        
        for (int i = 0; i < skinParts.size(); i++) {
            SkinPart skinPart = skinParts.get(i);
            partBounds[i] = skinPart.getPartBounds();
            // Need to keep offsets
            partBounds[i].setX(skinPart.getPartType().getItemRenderOffset().getX());
            partBounds[i].setY(skinPart.getPartType().getItemRenderOffset().getY());
            partBounds[i].setZ(skinPart.getPartType().getItemRenderOffset().getZ());
        }
        
        for (int i = 0; i < skinType.getSkinParts().size(); i++) {
            ISkinPartType skinPart = skinType.getSkinParts().get(i);
            paintPartBounds[i] = skinPart.getGuideSpace();
            paintPartBounds[i].setX(skinPart.getItemRenderOffset().getX());
            paintPartBounds[i].setY(skinPart.getItemRenderOffset().getY());
            paintPartBounds[i].setZ(skinPart.getItemRenderOffset().getZ());
        }
        
        
        
        
        float mcScale = 0.0625F;
        float scale = 1;
        
        GL11.glPushMatrix();
        GL11.glScalef(width * mcScale, height * mcScale, width * mcScale);
        
        // target box
        renderBox(width * mcScale,
                height * mcScale,
                width * mcScale,
                ((float)-width / 2F) * mcScale,
                ((float)-height / 2F) * mcScale,
                ((float)-width / 2F) * mcScale,
                0F, 0F, 1F);
        
        // scale
        /*
         * renderBox(scale * width, scale * height, scale * width,
         * ((float)-width / 2F) * scale, ((float)-height / 2F) * scale,
         * ((float)-width / 2F) * scale, 0F, 1F, 0F);
         */
        
        renderSkinWithHelper(skin, skinPointer, showSkinPaint, doLodLoading);
        
        GL11.glPopMatrix();
    }
    
    private static void renderSkinWithHelper(Skin skin, ISkinPointer skinPointer, boolean showSkinPaint, boolean doLodLoading) {
        ISkinType skinType = skinPointer.getSkinType();
        
        IEquipmentModel targetModel = SkinModelRenderer.INSTANCE.getModelForEquipmentType(skinType);
        
        if (targetModel == null) {
            renderSkinWithoutHelper(skin, skinPointer, doLodLoading);
            return;
        }
        
        targetModel.render(null, null, skin, showSkinPaint, skinPointer.getSkinDye(), null, true, 0, doLodLoading);
    }
    
    private static void renderSkinWithoutHelper(Skin skin, ISkinPointer skinPointer, boolean doLodLoading) {
        float scale = 1F / 16F;
        for (int i = 0; i < skin.getParts().size(); i++) {
            GL11.glPushMatrix();
            SkinPart skinPart = skin.getParts().get(i);
            IPoint3D offset = skinPart.getPartType().getOffset();
            GL11.glTranslated(offset.getX() * scale, (offset.getY() + 1) * scale, offset.getZ() * scale);
            SkinPartRenderer.INSTANCE.renderPart(skinPart, 0.0625F, skinPointer.getSkinDye(), null, doLodLoading);
            GL11.glPopMatrix();
        }
        
    }
    
    private static void renderBox(float width, float height, float depth, float xOffset, float yOffset, float zOffset, float r, float g, float b) {
        float f1 = 0.002F;
        AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(0, 0, 0, width, height, depth);
        
        aabb.offset(xOffset, yOffset, zOffset);
        
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(r, g, b, 0.4F);
        GL11.glLineWidth(1.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(false);
        RenderGlobal.drawOutlinedBoundingBox(aabb.contract(f1, f1, f1), -1);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glColor4f(1F, 1F, 1F, 1F);
    }
}
