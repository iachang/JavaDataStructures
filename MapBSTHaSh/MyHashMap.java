import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *
 * @source Princeton 61B Optional Textbook Hashcode
 *
 */

public class MyHashMap<K, V> implements Map61B<K, V> {
    private int totalBuckets;
    private double loadFactor;
    private int total;
    private ArrayList<Entry<K, V>>[] bins;
    private Set<K> keys;

    private static class Entry<K, V> {
        K key;
        V val;
        Entry(K key, V val) {
            this.key = key;
            this.val = val;
        }

        public K getKey() {
            return this.key;
        }

        public V getVal() {
            return this.val;
        }

        public void updateVal(V newVal) {
            this.val = newVal;
        }
    }

    public MyHashMap() {
        this(16, 0.75);
    }

    public MyHashMap(int totalBuckets) {
        this(totalBuckets, 0.75);
    }

    public MyHashMap(int totalBuckets, double loadFactor) {
        this.totalBuckets = totalBuckets;
        this.loadFactor = loadFactor;
        this.total = 0;
        bins = (ArrayList<Entry<K, V>>[]) new ArrayList[totalBuckets];
        for (int i = 0; i < totalBuckets; i++) {
            bins[i] = new ArrayList<>();
        }
        keys = new HashSet<K>();
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % totalBuckets;
    }

    @Override
    public void clear() {
        total = 0;
        for (int i = 0; i < totalBuckets; i++) {
            if (bins[i] != null) {
                bins[i].clear();
            }
        }
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("argument to contains is null!");
        }
        return get(key) != null;
    }

    @Override
    public V get(K key) {
        int i = hash(key);
        for (Entry<K, V> ent : bins[i]) {
            K asdf = ent.getKey();
            if (asdf.equals(key)) {
                return ent.getVal();
            }
        }
        return null;
    }

    @Override
    public int size() {
        return total;
    }

    @Override
    public void put(K key, V value) {
        if ((double) size() / totalBuckets > loadFactor) {
            resize(totalBuckets * 2);
        }
        int i = hash(key);
        for (Entry<K, V> entry : bins[i]) {
            if (entry.getKey().equals(key)) {
                entry.updateVal(value);
                return;
            }
        }
        bins[i].add(new Entry<>(key, value));
        keys.add(key);
        total++;
    }

    private void resize(int num) {
        MyHashMap<K, V> temp = new MyHashMap<K, V>(num, loadFactor);
        for (int i = 0; i < totalBuckets; i++) {
            for (Entry<K, V> entry : bins[i]) {
                temp.put(entry.getKey(), entry.getVal());
            }
        }
        this.totalBuckets = temp.totalBuckets;
        this.total = temp.total;
        this.bins = temp.bins;
        this.keys = temp.keys;
    }

    @Override
    public Set<K> keySet() {
        //Required!
        return keys;
    }

    @Override
    public Iterator<K> iterator() {
        //Required!
        return keys.iterator();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException("Unsupported!");
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException("Unsupported!");
    }
}
