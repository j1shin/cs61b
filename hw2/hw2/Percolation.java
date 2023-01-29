package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private int numOpenSites;
    private boolean[] grid;
    private int length;
    private WeightedQuickUnionUF union;
    private WeightedQuickUnionUF percolationTest;
    private int virtualTop;
    private int virtualBot;

    private int index(int row, int col) {
        return (row * length) + col;
    }

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        numOpenSites = 0;
        grid = new boolean[N * N];
        length = N;
        union = new WeightedQuickUnionUF(N * N + 2);
        percolationTest = new WeightedQuickUnionUF((N * N) + 2);
        virtualTop = length * length;
        virtualBot = length * length + 1;

        for (int i = 0; i < grid.length; i++) {
            grid[i] = false;
        }
    }

    private void checkOpen(int row, int col, int adjRow, int adjCol) {
        if (adjRow < 0 || adjRow >= length || adjCol < 0 || adjCol >= length) {
            return;
        }
        if (isOpen(adjRow, adjCol)) {
            union.union(index(row, col), index(adjRow, adjCol));
            percolationTest.union(index(row, col), index(adjRow, adjCol));
        }
    }
    public void open(int row, int col) {
        if (row > length - 1 || col > length - 1 || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (grid[index(row, col)]) {
            return;
        }
        grid[index(row, col)] = true;
        numOpenSites++;

        //check directions (up, down, left, right)
        checkOpen(row, col, row + 1, col);
        checkOpen(row, col, row - 1, col);
        checkOpen(row, col, row, col + 1);
        checkOpen(row, col, row, col - 1);

        if (row == 0) {
            union.union(index(row, col), virtualTop);
            percolationTest.union(index(row, col), virtualTop);
        }
        if (row == length - 1) {
            union.union(index(row, col), (virtualBot));
        }
    }

    public boolean isOpen(int row, int col) {
        if (row > length || col > length || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException();
        }
        return grid[index(row, col)];
    }

    public boolean isFull(int row, int col) {
        if (row > length || col > length || row < 0 || col < 0) {
            throw new IndexOutOfBoundsException();
        }
        return percolationTest.connected(index(row, col), virtualTop);
    }

    public int numberOfOpenSites() {
        return numOpenSites;
    }

    public boolean percolates() {
        return union.connected(length * length, (virtualBot));
    }
}
