package deque;
import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {

    private Comparator<T> compare;

    public MaxArrayDeque(Comparator<T> c) {
        compare = c;
    }

    public T max() {
        if (size() == 0) {
            return null;
        }
        T maxValue = this.get(0);
        for (int i = 0; i < size() - 1; i++) {
            if (compare.compare(maxValue, get(i + 1)) < 0) {
                maxValue = this.get(i + 1);
            }
        }
        return maxValue;
    }

    public T max(Comparator<T> c) {
        if (size() == 0) {
            return null;
        }
        T maxValue = this.get(0);
        for (int i = 0; i < size() - 1; i++) {
            if (c.compare(maxValue, this.get(i + 1)) < 0) {
                maxValue = this.get(i + 1);
            }
        }
        return maxValue;
    }
}
