package byow.Core;

public class Node implements Comparable<Node>{
    public int ID;
    public int distTo;

    public Node(int ID, int distTo) {
        this.ID = ID;
        this.distTo = distTo;
    }

    @Override
    public int compareTo(Node n) {
        return Integer.compare(this.distTo, n.distTo);
    }

    @Override
    public String toString() {
        return ID + ": " + distTo;
    }
}
