package byow.Core;

public class Room {
    public TileCoords UR;
    public TileCoords UL;
    public TileCoords BR;
    public TileCoords BL;
    public int length;
    public int height;
    public TileCoords center;


    public Room(TileCoords UR, TileCoords UL, TileCoords BR, TileCoords BL, int length, int height) {
        this.UR = UR;
        this.UL = UL;
        this.BR = BR;
        this.BL = BL;

        this.length = length;
        this.height = height;


        this.center = new TileCoords(BL.xpos + (length / 2), BL.ypos + (height / 2));
    }
}
