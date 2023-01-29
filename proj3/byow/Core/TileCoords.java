package byow.Core;

public class TileCoords {
    public int xpos;
    public int ypos;

    public TileCoords(int xpos, int ypos) {
        this.xpos = xpos;
        this.ypos = ypos;
    }

    public TileCoords xplus(int n) {
        return new TileCoords(xpos + n, ypos);
    }

    public TileCoords yplus(int n) {
        return new TileCoords(xpos, ypos + n);
    }
    public TileCoords bothplus(int a, int b) {
        return new TileCoords(xpos + a, ypos + b);
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() != TileCoords.class) {
            return false;
        } else {
            TileCoords tc = (TileCoords) o;
            if (tc.xpos == this.xpos && tc.ypos == this.ypos) {
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public String toString() {
        return xpos + ":" + ypos;
    }

}
