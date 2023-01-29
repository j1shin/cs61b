package deque;
import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private class HelperNode {
        T item;
        HelperNode next;
        HelperNode prev;

        HelperNode(HelperNode p, T i, HelperNode n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private int size;
    private HelperNode sentinel;

    public LinkedListDeque() {
        size = 0;
        sentinel = new HelperNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    public void addFirst(T item) {
        size++;
        sentinel.next = new HelperNode(sentinel, item, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
    }

    public void addLast(T item) {
        size++;
        sentinel.prev = new HelperNode(sentinel.prev, item, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
    }

    public int size() {
        return size;
    }


    public void printDeque() {
        while (sentinel != null) {
            System.out.print(sentinel.item + "");
            sentinel = sentinel.next;
        }
    }


    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size--;
        T returnvalue = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        return returnvalue;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size--;
        T returnvalue = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        return returnvalue;
    }

    public T get(int index) {
        if (index > size - 1) {
            return null;
        }
        HelperNode newNext = sentinel.next;
        while (index > 0) {
            index--;
            newNext = newNext.next;
        }
        return newNext.item;
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

    private T helperRecursion(int index, HelperNode helper) {
        if (index == 0) {
            return helper.item;
        } else {
            return helperRecursion(index - 1, helper.next);
        }
    }

    public T getRecursive(int index) {
        if (index > size - 1) {
            return null;
        } else {
            if (index < 0) {
                return null;
            } else {
                return helperRecursion(index, sentinel.next);
            }
        }
    }
    public Iterator<T> iterator() {
        return new LLIterator();
    }
    private class LLIterator implements Iterator<T> {
        private int position;

        public LLIterator() {
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
