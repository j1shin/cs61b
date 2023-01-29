package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> working = new AListNoResizing<>();
        BuggyAList<Integer> notworking = new BuggyAList<>();

        working.addLast(4);
        working.addLast(5);
        working.addLast(6);

        notworking.addLast(4);
        notworking.addLast(5);
        notworking.addLast(6);

        assertEquals(working.size(), notworking.size());

        assertEquals(working.removeLast(), notworking.removeLast());
        assertEquals(working.removeLast(), notworking.removeLast());
        assertEquals(working.removeLast(), notworking.removeLast());
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> Bugged = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                Bugged.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int size_bugged = Bugged.size();
            } else if (operationNumber == 2) {
                // getLast
                } if (L.size() > 0) {
                    L.getLast();
                    Bugged.getLast();
            } else if (operationNumber == 3) {
                //removeLast
                } if (L.size() > 0) {
                    L.removeLast();
                    Bugged.removeLast();
            }
        }
    }
}
