import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {

    private final int trials;
    private static final double CONFIDENCE_95 = 1.96;
    private final double finalMean;
    private final double finalStddev;

    /**
     * perform trials independent experiments on an n-by-n grid
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        int row, col;
        int closedSites;
        Percolation p;
        double[] percolationRatio = new double[trials];

        this.trials = trials;

        for (int i = 0; i < trials; i++) {
            p = new Percolation(n);

            closedSites = 0;
            while (!p.percolates()) {
                do {
                    row = StdRandom.uniform(1, n+1);
                    col = StdRandom.uniform(1, n+1);
                } while (p.isOpen(row, col));

                p.open(row, col);
                closedSites++;
            }

            percolationRatio[i] = (double) closedSites / (n * n);
        }

        finalMean = StdStats.mean(percolationRatio);
        finalStddev = StdStats.stddev(percolationRatio);
    }

    /**
     * sample mean of percolation threshold
     */
    public double mean() {
        return finalMean;
    }

    /**
     * sample standard deviation of percolation threshold
     */
    public double stddev() {
        return finalStddev;
    }

    /**
     * low  endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return finalMean - CONFIDENCE_95 * finalStddev / Math.sqrt(trials);
    }

    /**
     * high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return finalMean + CONFIDENCE_95 * finalStddev / Math.sqrt(trials);
    }

    /**
     * test client (described below)
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            StdOut.println("Usage: java PercolationStats <n> <t>");
            return;
        }

        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, t);

        StdOut.println("mean                    = " + ps.mean());
        StdOut.println("stddev                  = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }

}
