package bstmap;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    private class Node {
        private K key; // Our key is of type K
        private V value; // Our value is of type V
        private Node left;
        private Node right;

        public Node(K k, V v) { // Our Node constructor
            key = k;
            value = v;
        }
    }
    private Node root; // We don’t want anyone messing with the internal stuff
    private int size;

    private Node getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        int compare = key.compareTo(p.key);
        if (compare > 0) {
            return getHelper(key, p.right);
        } else if (compare < 0) {
            return getHelper(key, p.left);
        } else {
            return p;
        }
    }

    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size++;
            return new Node (key, value);
        }

        int compare = key.compareTo(p.key) ;
        if (compare > 0) {
            p.right = putHelper(key, value, p.right) ;
        } else if (compare < 0) {
            p.left = putHelper(key, value, p.left) ;
        } else {
            p. value = value;
        }
        return p;
    }

    public BSTMap() { //done
        clear();
    }


    public void clear() { //done
        root = null;
        size = 0;

    }

    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) { //done
        return getHelper(key, root) != null;
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) { //done
        Node node = getHelper(key, root) ;
        if (node == null) {
            return null;
        } else {
            return node.value;
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() { //done
        return size;
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) { //done
        root = putHelper (key, value, root);
    }

    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }
    public void printInOrder() {
    }
}
