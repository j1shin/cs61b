package timingtest;
import edu.princeton.cs.algs4.Stopwatch;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        AList N = new AList();
        AList times = new AList();
        AList count = new AList();
        SLList x = new SLList();
        for (int i = 1000; i <= 128000; i *= 2) {
            count.addLast(10000);
            N.addLast(i);
            for (int j = 0; j <= i; j += 1) {
                x.addLast(1);
            }
            Stopwatch ok = new Stopwatch();
            for (int k = 0; k <= 10000; k++) {
                x.getLast();
            }
            double time_seconds = ok.elapsedTime();
            times.addLast(time_seconds);
            }
        printTimingTable(N, times, count);
    }
}