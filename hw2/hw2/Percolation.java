package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int N;
    private final WeightedQuickUnionUF sites;

    private final int _topSite;

    private final int _bottomSite;

    private final boolean[][] flagOpen;

    private int _numbersOpen = 0;

    public Percolation(int N) {
        // create N-by-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        this.N = N;
        this._topSite = N * N;
        this._bottomSite = N * N + 1;

        this.sites = new WeightedQuickUnionUF(N * N + 2);

        for (int i = 0; i < N; i++) {
            sites.union(_topSite, xyTo1D(0, i));
            sites.union(_bottomSite, xyTo1D(N - 1, i));
        }

        this.flagOpen = new boolean[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.flagOpen[i][j] = false;
            }
        }
    }

    public static void main(String[] args) {
        // use for unit testing (not required)
    }

    private int xyTo1D(int r, int c) {
        return r * N + c;
    }

    private void validRange(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            System.out.println(row + " , " + col);
            throw new IndexOutOfBoundsException();
        }
    }

    private void unionIfOpen(int row, int col, int newRow, int newCol) {
        if (newRow >= 0 && newRow < N && newCol >= 0 && newCol < N && flagOpen[newRow][newCol]) {
            // Do Not change the line above into " if(!is_open)"
            this.sites.union(xyTo1D(row, col), xyTo1D(newRow, newCol));
        }
    }

    public void open(int row, int col) {
        // open the site (row, col) if it is not open already
        if (isOpen(row, col)) {
            return;
        }
        flagOpen[row][col] = true;
        unionIfOpen(row, col, row - 1, col);
        unionIfOpen(row, col, row + 1, col);
        unionIfOpen(row, col, row, col - 1);
        unionIfOpen(row, col, row, col + 1);

        flagOpen[row][col] = true;
        this._numbersOpen++;
    }

    public boolean isOpen(int row, int col) {
        validRange(row, col);
        // is the site (row, col) open?
        return flagOpen[row][col];
    }

    public boolean isFull(int row, int col) {
        validRange(row, col);
        // is the site (row, col) full?
        if (!isOpen(row, col)) {
            return false;
        }
        int index =  xyTo1D(row, col);
        if (index >= this._topSite) {
            return false;
        } else {
            return this.sites.connected(_topSite, index);
        }
    }

    public int numberOfOpenSites() {
        // number of open sites
        return this._numbersOpen;
    }

    public boolean percolates() {
        // does the system percolate?
        if (this._numbersOpen == 0) {
            return false;
        } else {
            return this.sites.connected(this._topSite, this._bottomSite);
        }
    }
}
