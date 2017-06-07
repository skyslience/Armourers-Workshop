package riskyken.armourersWorkshop.api.common;

public interface IRectangle3D {

    public void setX(int value);
    public void setY(int value);
    public void setZ(int value);
    
    public int getX();
    public int getY();
    public int getZ();
    
    public int getWidth();
    public int getHeight();
    public int getDepth();
}
