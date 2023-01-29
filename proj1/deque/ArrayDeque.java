package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private int size;
    private T[] array;
    private int nextfirst;
    private int nextlast;

    private static final int X = 3;
    private static final int Y = 4;
    private static final int FIRSTLENGTH = 8;
    private static final int SCALAR = 4;
    private void resizing(int cap) {
        T[] newArray = (T[]) new Object[cap];
        for (int i = 0; i < size(); i++) {
            newArray[Math.floorMod(i, cap)] = array[Math.floorMod(i + 1 + nextfirst, array.length)];
        }
        array = newArray;
    }

    public ArrayDeque() {
        array = (T[]) new Object[FIRSTLENGTH];
        this.nextfirst = X;
        this.nextlast = Y;
    }

    @Override
    public void addFirst(T item) {
        if (size == array.length) {
            int newcapacity = array.length * 2;
            resizing(newcapacity);
            nextfirst = array.length - 1;
            nextlast = size();
        }
        this.array[nextfirst] = item;
        nextfirst = Math.floorMod(nextfirst - 1, array.length);
        size++;
    }

    @Override
    public void addLast(T item) {
        if (size == array.length) {
            int newcapacity = array.length * 2;
            resizing(newcapacity);
            nextfirst = array.length - 1;
            nextlast = size();
        }
        array[nextlast] = item;
        nextlast = Math.floorMod((nextlast + 1), array.length);
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i <= array.length; i++) {
            System.out.print(get(i) + "");
        }
    }

    @Override
    public T removeFirst() {
        if (size() <= 0) {
            return null;
        }
        T storedValue = array[Math.floorMod((nextfirst + 1), array.length)];
        array[Math.floorMod(nextfirst + 1, array.length)] = null;
        nextfirst = Math.floorMod((nextfirst + 1), array.length);
        size--;
        if ((array.length / SCALAR) > size && size >= FIRSTLENGTH) {
            int newcapacity = array.length / SCALAR;
            resizing(newcapacity);
            nextfirst = array.length - 1;
            nextlast = size();
        }
        return storedValue;
    }

    @Override
    public T removeLast() {
        if (size() <= 0) {
            return null;
        }
        T storedValue = array[Math.floorMod((nextlast - 1), array.length)];
        array[Math.floorMod(nextlast - 1, array.length)] = null;
        nextlast = Math.floorMod(nextlast - 1, array.length);
        size--;
        if (array.length / SCALAR > size && size >= FIRSTLENGTH) {
            int newcapacity = array.length / SCALAR;
            resizing(newcapacity);
            nextfirst = array.length - 1;
            nextlast = size();
        }
        return storedValue;
    }
    @Override
    public T get(int index) {
        return array[Math.floorMod(nextfirst + 1 + index, array.length)];
    }

    public boolean equals(Object o) {
        if (!(o instanceof Deque) || (o == null)) {
            return false;
        }
        if (this.size() != ((Deque<?>) o).size()) {
            return false;
        }
        Deque castedo = (Deque) o;
        for (int i = 0; i < size; i++) {
            if (!get(i).equals(castedo.get(i))) {
                return false;
            }
        }
        return true;
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }
    private class ArrayDequeIterator implements Iterator<T> {
        private int position;

        public ArrayDequeIterator() {
            position = 0;
        }

        public boolean hasNext() {
            return (position < size);
        }

        public T next() {
            T returnItem = get(position);
            position++;
            return returnItem;
        }
    }
}

