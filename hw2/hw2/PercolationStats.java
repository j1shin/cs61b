package hw2;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] listofFractions;
    private PercolationFactory pf;
    private int firstInt;
    private int secondInt;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Must be greater than 0");
        }
        listofFractions = new double[T];
        this.pf = pf;
        this.firstInt = N;
        this.secondInt = T;

        for (int i = 0; i < secondInt; i += 1) {
            Percolation newPercolation = pf.make(N);
            while (newPercolation.percolates() == false) {
                int row = StdRandom.uniform(0, N);
                int col = StdRandom.uniform(0, N);
                if (!newPercolation.isOpen(row, col)) {
                    newPercolation.open(row, col);
                }
            }
            double fractions = (double) newPercolation.numberOfOpenSites() / (N * N);
            listofFractions[i] = fractions;
        }
    }

// perform T independent experiments on an N-by-N grid
    public double mean() {
        return StdStats.mean(listofFractions);
    }                                          // sample mean of percolation threshold
    public double stddev() {
        return StdStats.stddev(listofFractions);
    }                                        // sample standard deviation of percolation threshold
    public double confidenceLow() {
        return mean() - ((stddev() * 1.96) / Math.sqrt(secondInt));
    }                                 // low endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + ((stddev() * 1.96) / Math.sqrt(secondInt));
    }                                // high endpoint of 95% confidence interval
}
