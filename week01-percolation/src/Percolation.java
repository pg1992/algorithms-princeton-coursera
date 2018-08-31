import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Percolation {

    private final WeightedQuickUnionUF connectedSites;
    private final WeightedQuickUnionUF antiBackwash;
    private final int latticeSize;
    private final int top, bottom;
    private int numberOfOpenSites;
    private byte[] allSites;

    /**
     * create n-by-n grid, with all sites blocked
     */
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("The argument should be positive.");

        int numPoints = n * n + 2;

        latticeSize = n;
        connectedSites = new WeightedQuickUnionUF(numPoints);
        antiBackwash = new WeightedQuickUnionUF(numPoints);
        allSites = new byte[numPoints];

        top = numPoints - 2;
        bottom = numPoints - 1;

        allSites[top] = 1;
        allSites[bottom] = 1;
    }

    private void validate(int row, int col) {
        if (row < 1 || row > latticeSize || col < 1 || col > latticeSize)
            throw new IllegalArgumentException("Invalid coordinates: (" + row + ", " + col + ")");
    }


    private int xyToArrayIndex(int row, int col) {
        validate(row, col);

        row--;
        col--;

        return row * latticeSize + col;
    }

    private void tryToConnect(int first, int second) {
        boolean isOpen = allSites[second] == 1;

        if (isOpen) {
            connectedSites.union(first, second);
            if (second != bottom)
                antiBackwash.union(first, second);
        }
    }

    /**
     * open site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        validate(row, col);

        if (isOpen(row, col)) return;

        int index = xyToArrayIndex(row, col);
        int up;
        int down;
        int left;
        int right;

        try {
            up = xyToArrayIndex(row - 1, col);
        } catch (IllegalArgumentException e) {
            up = -1;
        }
        try {
            down = xyToArrayIndex(row + 1, col);
        } catch (IllegalArgumentException e) {
            down = -1;
        }
        try {
            left = xyToArrayIndex(row, col - 1);
        } catch (IllegalArgumentException e) {
            left = -1;
        }
        try {
            right = xyToArrayIndex(row, col + 1);
        } catch (IllegalArgumentException e) {
            right = -1;
        }

        if (row == 1)
            tryToConnect(index, top);

        if (row == latticeSize)
            tryToConnect(index, bottom);

        if (up >= 0) tryToConnect(index, up);
        if (down >= 0) tryToConnect(index, down);
        if (left >= 0) tryToConnect(index, left);
        if (right >= 0) tryToConnect(index, right);

        allSites[index] = 1;
        numberOfOpenSites++;
    }

    /**
     * is site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        validate(row, col);

        return allSites[xyToArrayIndex(row, col)] == 1;
    }

    /**
     * is site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        validate(row, col);

        return antiBackwash.connected(top, xyToArrayIndex(row, col));
    }

    /**
     * number of open sites
     */
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    /**
     * does the system percolate?
     */
    public boolean percolates() {
        return connectedSites.connected(top, bottom);
    }

    /**
     * test client (optional)
     */
    public static void main(String[] args) {
        Percolation p;
        p = new Percolation(StdIn.readInt());

        while (!StdIn.isEmpty())
            p.open(StdIn.readInt(), StdIn.readInt());

        StdOut.println("Percolates: " + p.percolates());
    }

}
