package gh2;
import deque.Deque;
import deque.ArrayDeque;

public class GuitarString {
    private static final int SR = 44100;
    private static final double DECAY = .996;

    private Deque<Double> buffer;

    public GuitarString(double frequency) {
        buffer = new ArrayDeque<>();
        double n = SR / frequency;
        for (int i = 0; i < (n); i++) {
            buffer.addFirst(0.0);
        }
    }

    public void pluck() {
        for (int t = 0; t < buffer.size(); t++) {
            double r = Math.random() - 0.5;
            buffer.removeFirst();
            buffer.addLast(r);
        }
    }

    public void tic() {
        Double current = buffer.removeFirst();
        Double redequed = ((current + buffer.get(0)) / 2) * DECAY;
        buffer.addLast(redequed);
    }

    public double sample() {
        return buffer.get(buffer.size() - 1);
    }
}
