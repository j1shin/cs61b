package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdRandom;

import java.util.*;

import static java.lang.Math.*;

public class InstantiateWorld {
    private static final int WIDTH = 100;
    private static final int HEIGHT = 50;
    private static final Set<Integer> OccupiedSpaces = new HashSet<>();
    private static HashMap<Integer, Room> allRooms = new HashMap<>();
    private static final int MIN_ROOMS = 20;
    private static final int MAX_ROOMS = 30;
    private static PriorityQueue<Node> fringe = new PriorityQueue<>();
    private static final HashMap<Integer, Integer> distTo = new HashMap<>();
    private static final HashMap<Integer, Integer> edgeTo = new HashMap<>();
    private static Random rand;
    public static TETile[][] frame;
    public static TileCoords avatar;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] allTiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                allTiles[x][y] = Tileset.NOTHING;
            }
        }

        Long seed = Long.parseLong(args[0].split("[A-Za-z]")[1]);


        rand = new Random(seed);


        int rooms = rand.nextInt(MAX_ROOMS - MIN_ROOMS) + MIN_ROOMS;
        int halls = rand.nextInt((MAX_ROOMS / 2) - (MIN_ROOMS / 2)) + (MIN_ROOMS / 2);

        int n = 0;
        while (n < rooms) {
            Room a = oneRoom(allTiles, new TileCoords(rand.nextInt(WIDTH), rand.nextInt(HEIGHT)),
                    rand.nextInt(6) + 5, rand.nextInt(6) + 5);
            if (a != null) {
                allRooms.put(n, a);
                n++;
            }
        }

        while (n < halls + rooms) {
            Room h = oneRoom(allTiles, new TileCoords(rand.nextInt(WIDTH), rand.nextInt(HEIGHT)),
                    rand.nextInt(2) + 3, rand.nextInt(6) + 5);
            if (h != null) {
                allRooms.put(n, h);
                n++;
            }
        }

        int currNode = 0;



        Node[] nar = new Node[n];
        for (int i = 0; i < n; i++) {
            distTo.put(i, roomDistance(i, currNode));
            nar[i] = new Node(i, distTo.get(i));
            fringe.add(nar[i]);
        }
        HashSet<Integer> usedNodes = new HashSet<>();


        while (fringe.peek() != null) {
            currNode = fringe.poll().ID;
            usedNodes.add(currNode);


            for (int i = 0; i < n; i++) {

                if(i != currNode & roomDistance(i, currNode) <= distTo.get(i)) {
                    if (!usedNodes.contains(i)) {
                        edgeTo.put(i, currNode);
                    }
                    distTo.put(i, roomDistance(i, currNode));
                }
            }
            for (Node nde: nar) {
                nde.distTo = distTo.get(nde.ID);
            }
        }

        for (int i = 1; i < n; i++) {
            hallway(allTiles, allRooms.get(i), allRooms.get(edgeTo.get(i)));
        }



        ter.renderFrame(allTiles);
    }

    public InstantiateWorld(Long seed) {
//        TERenderer ter = new TERenderer();
//        ter.initialize(WIDTH, HEIGHT);

        TETile[][] allTiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                allTiles[x][y] = Tileset.NOTHING;
            }
        }

        rand = new Random(seed);


        int rooms = rand.nextInt(MAX_ROOMS - MIN_ROOMS) + MIN_ROOMS;
        int halls = rand.nextInt((MAX_ROOMS / 2) - (MIN_ROOMS / 2)) + (MIN_ROOMS / 2);

        int n = 0;
        while (n < rooms) {
            Room a = oneRoom(allTiles, new TileCoords(rand.nextInt(WIDTH), rand.nextInt(HEIGHT)),
                    rand.nextInt(6) + 5, rand.nextInt(6) + 5);
            if (a != null) {
                allRooms.put(n, a);
                n++;
            }
        }

        while (n < halls + rooms) {
            Room h = oneRoom(allTiles, new TileCoords(rand.nextInt(WIDTH), rand.nextInt(HEIGHT)),
                    rand.nextInt(2) + 3, rand.nextInt(6) + 5);
            if (h != null) {
                allRooms.put(n, h);
                n++;
            }
        }

        int currNode = 0;



        Node[] nar = new Node[n];
        for (int i = 0; i < n; i++) {
            distTo.put(i, roomDistance(i, currNode));
            nar[i] = new Node(i, distTo.get(i));
            fringe.add(nar[i]);
        }
        HashSet<Integer> usedNodes = new HashSet<>();


        while (fringe.peek() != null) {
            currNode = fringe.poll().ID;
            usedNodes.add(currNode);


            for (int i = 0; i < n; i++) {

                if(i != currNode & roomDistance(i, currNode) <= distTo.get(i)) {
                    if (!usedNodes.contains(i)) {
                        edgeTo.put(i, currNode);
                    }
                    distTo.put(i, roomDistance(i, currNode));
                }
            }
            for (Node nde: nar) {
                nde.distTo = distTo.get(nde.ID);
            }
        }

        for (int i = 1; i < n; i++) {
            hallway(allTiles, allRooms.get(i), allRooms.get(edgeTo.get(i)));
        }

        while (true) {
            avatar = new TileCoords(rand.nextInt(WIDTH), rand.nextInt(HEIGHT));
            if (allTiles[avatar.xpos][avatar.ypos] == Tileset.FLOOR) {
                allTiles[avatar.xpos][avatar.ypos] = Tileset.AVATAR;
                break;
            }
        }


        frame = allTiles;
    }

    public static int getTCID(TileCoords tc) {
        return ((tc.ypos * WIDTH) + tc.xpos);
    }

    private static int roomDistance(int this_room, int that_room) {
        int xdiff = abs(allRooms.get(that_room).BL.xpos - allRooms.get(this_room).BL.xpos);
        int ydiff = abs(allRooms.get(that_room).BL.ypos - allRooms.get(this_room).BL.ypos);

        return xdiff + ydiff;
    }

    public static Set<TileCoords> drawLine(TETile[][] tiles, TileCoords end1, TileCoords end2, TETile tileType, boolean inferior) {
        Set<TileCoords> s;
        if (end1.ypos == end2.ypos) {
            s = horizLine(tiles, end1, end2, tileType, inferior);
        } else if (end1.xpos == end2.xpos) {
            s = vertLine(tiles, end1, end2, tileType, inferior);
        } else {
            throw new IllegalArgumentException("ends have to be in same line!");
        }
        return s;
    }

    public static Set<TileCoords> horizLine(TETile[][] tiles, TileCoords end1, TileCoords end2, TETile tileType, boolean inferior) {
        Set<TileCoords> s = new HashSet<>();
        int start = min(end1.xpos, end2.xpos);
        int end = max(end1.xpos, end2.xpos);
        for (int i = 0; i <= end - start; i++) {
            TileCoords tc = new TileCoords(start + i, end1.ypos);
            if (!inferior | !OccupiedSpaces.contains(getTCID(tc))) {
                tiles[start + i][end1.ypos] = tileType;
                OccupiedSpaces.add(getTCID(tc));
            }
        }
        return s;
    }

    public static Set<TileCoords> vertLine(TETile[][] tiles, TileCoords end1, TileCoords end2, TETile tileType, boolean inferior) {
        Set<TileCoords> s = new HashSet<>();
        int start = min(end1.ypos, end2.ypos);
        int end = max(end1.ypos, end2.ypos);
        for (int i = 0; i <= end - start; i++) {
            TileCoords tc = new TileCoords(end1.xpos, start + i);
            if (!inferior | !OccupiedSpaces.contains(getTCID(tc))) {
                tiles[end1.xpos][start + i] = tileType;
                OccupiedSpaces.add(getTCID(tc));
            }
        }
        return s;
    }

    public static Room oneRoom(TETile[][] tiles, TileCoords pos, int length, int height) {
        TileCoords BL = pos;
        TileCoords BR = new TileCoords(pos.xpos + length - 1, pos.ypos);
        TileCoords UL = new TileCoords(pos.xpos, pos.ypos + height - 1);
        TileCoords UR = new TileCoords(pos.xpos + length - 1, pos.ypos + height - 1);


        if (boundsCheck(tiles, UR) | boundsCheck(tiles, UL) | boundsCheck(tiles, BL) | boundsCheck(tiles, BR)) {
            return null;
        }

        Room r = new Room(UR, UL, BR, BL, length, height);

        drawLine(tiles, BL, BR, Tileset.WALL, true);
        drawLine(tiles, BR, UR, Tileset.WALL, true);
        drawLine(tiles, UR, UL, Tileset.WALL, true);
        drawLine(tiles, UL, BL, Tileset.WALL, true);

        roomFill(tiles, r);


        return r;
    }

    public static void roomFill(TETile[][] tiles, Room room) {
        for (int i = 1; i <= room.length - 2; i++) {
            for (int j = 1; j <= room.height - 2; j++) {
                tiles[room.BL.xpos + i][room.BL.ypos + j] = Tileset.FLOOR;
                OccupiedSpaces.add(getTCID(new TileCoords(room.BL.xpos + i, room.BL.ypos + j)));
            }
        }
    }

    public static boolean boundsCheck(TETile[][] tiles, TileCoords pos) {
        if(pos.xpos < 1 | pos.ypos < 1 | pos.xpos >= WIDTH - 1 | pos.ypos < 1) {
            return true;
        } else if(pos.ypos >= HEIGHT - 1) {
            return true;
        } else if (tiles[pos.xpos][pos.ypos] != Tileset.NOTHING) {
            return true;
        } else {
            return false;
        }
    }

    public static void hallway(TETile[][] tiles, Room roomA, Room roomB) {
        TileCoords midpoint = new TileCoords(roomB.center.xpos, roomA.center.ypos);
        drawLine(tiles, roomA.center, midpoint, Tileset.FLOOR, false);
        drawLine(tiles, roomA.center.yplus(1), midpoint.yplus(1), Tileset.WALL, true);
        drawLine(tiles, roomA.center.yplus(-1), midpoint.yplus(-1), Tileset.WALL, true);

        drawLine(tiles, midpoint, roomB.center, Tileset.FLOOR, false);
        drawLine(tiles, midpoint.xplus(1), roomB.center.xplus(1), Tileset.WALL, true);
        drawLine(tiles, midpoint.xplus(-1), roomB.center.xplus(-1), Tileset.WALL, true);

        drawLine(tiles, midpoint.bothplus(1, 1), midpoint.bothplus(-1, 1), Tileset.WALL, true);
        drawLine(tiles, midpoint.bothplus(-1, -1), midpoint.bothplus(1, -1), Tileset.WALL, true);

    }

}
