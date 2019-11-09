package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int openSites;
    private WeightedQuickUnionUF set;
    private WeightedQuickUnionUF percSet;
    private int N;
    private int topConnector;
    private int bottomConnector;

    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("N must be greater than 0!");
        }
        this.N = N;
        openSites = 0;
        grid = new boolean[N][N];
        topConnector = N * N;
        bottomConnector = N * N + 1;
        set = new WeightedQuickUnionUF(N * N + 1);
        percSet = new WeightedQuickUnionUF(N * N + 2);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                grid[i][j] = false;
            }
        }

        //Top Connector
        /*
        for (int i = 0; i < N; i++) {
            set.union(topConnector, i);
            percSet.union(topConnector, i);
            percSet.union(bottomConnector, N * N - (i + 1));
        }*/
    }

    private int xyTo1D(int r, int c) {
        return r * N + c;
    }

    private void connect(int r1, int c1, int r2, int c2) {
        if (isOpen(r1, c1) && isOpen(r2, c2)) {
            set.union(xyTo1D(r1, c1), xyTo1D(r2, c2));
            percSet.union(xyTo1D(r1, c1), xyTo1D(r2, c2));
        }
    }

    public void open(int row, int col) {
        if (row >= N || row < 0 || col < 0 || col >= N) {
            throw new java.lang.IndexOutOfBoundsException("out of bounds");
        }

        if (!grid[row][col]) {
            grid[row][col] = true;
            openSites++;
        }

        //CHeck neighbors
        if (row == 0) {
            if (N != 1) {
                connect(row + 1, col, row, col);
            }
            set.union(topConnector, xyTo1D(row, col));
            percSet.union(topConnector, xyTo1D(row, col));
        }
        if (row == N - 1) { //Skips the case where row == 0 sine that's the first case
            if (N != 1) {
                connect(row - 1, col, row, col);
            }
            percSet.union(bottomConnector, xyTo1D(row, col));
        }
        if (row != 0 && row != N - 1) {
            connect(row - 1, col, row, col);
            connect(row + 1, col, row, col);
        }

        if (col == N - 1) {
            if (N != 1) {
                connect(row, col - 1, row, col);
            }
        } else if (col == 0) {
            connect(row, col + 1, row, col);
        } else {
            connect(row, col - 1, row, col);
            connect(row, col + 1, row, col);
        }

    }

    public boolean isOpen(int row, int col) {
        //Empty spot where water has never reached it? Excavation site
        if (row >= N || row < 0 || col < 0 || col >= N) {
            throw new java.lang.IndexOutOfBoundsException("out of bounds");
        }

        return grid[row][col];
    }

    public boolean isFull(int row, int col) {
        //Has water reached it from the top?
        if (row >= N || row < 0 || col < 0 || col >= N) {
            throw new java.lang.IndexOutOfBoundsException("out of bounds");
        }

        return set.connected(topConnector, xyTo1D(row, col)) && isOpen(row, col);
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        if (openSites == 0) {
            return false;
        }
        return percSet.connected(topConnector, bottomConnector);
    }

    public static void main(String[] args) {
        Percolation percs = new Percolation(5);
        percs.open(0, 2);
        percs.open(1, 2);
        percs.open(2, 2);
        percs.open(1, 3);
        percs.open(2, 4);
        percs.open(3, 4);
        percs.open(4, 4);
        percs.open(2, 3);
        percs.open(2, 3);
        percs.open(4, 0);
        /*
        System.out.println(true == percs.percolates()); // Return True
        System.out.println(9 == percs.numberOfOpenSites());
        System.out.println(false == percs.isFull(0, 0));
        System.out.println(false == percs.isFull(4, 0));
        System.out.println(true == percs.isFull(0, 2));

        Percolation percs2 = new Percolation(1);
        System.out.println(false == percs2.percolates());
        percs2.open(0, 0);
        System.out.println(true == percs2.percolates());
        System.out.println(true == percs2.isFull(0, 0));

        Percolation percs3 = new Percolation(3);
        System.out.println(false == percs3.percolates());
        percs3.open(0, 0);
        percs3.open(1, 0);
        percs3.open(2, 0);
        percs3.open(2, 2);
        System.out.println(true == percs3.percolates());
        System.out.println(false == percs3.isFull(2, 2));*/

    }
}
