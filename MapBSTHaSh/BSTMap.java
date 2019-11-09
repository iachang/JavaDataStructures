import java.util.Iterator;
import java.util.Set;

/**
 *
 * @param <K>
 * @source Princeton Optional CS61B Textbook code
 */


public class BSTMap<K extends Comparable, V> implements Map61B<K, V> {

    private Node root;
    private int size;

    private class Node {
        private K key;
        private V val;
        private Node left, right;
        private int size;

        Node(K key, V val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean containsKey(K key) {
        return findNode(root, key) != null;
    }

    @Override
    public V get(K key) {
        if (findNode(root, key) == null) {
            return null;
        } else {
            return findNode(root, key).val;
        }
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.size;
        }
    }

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node put(Node x, K key, V val) {
        if (x == null) {
            return new Node(key, val, 1);
        }
        int compare = key.compareTo(x.key);
        if (compare < 0) {
            x.left = put(x.left, key, val);
        } else if (compare > 0) {
            x.right = put(x.right, key, val);
        } else {
            x.val = val;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public void printInOrder() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }

    private Node findNode(Node x, K key) {
        if (x == null) {
            return null;
        }
        int compare = key.compareTo(x.key);
        if (compare == 0) {
            return x;
        } else if (compare < 0) {
            return findNode(x.left, key);
        } else {
            return findNode(x.right, key);
        }
    }
}
