import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MyTrieSet implements TrieSet61B {

    private Node root;
    private int n; //number of keys in trie

    public MyTrieSet() {
        root = new Node(false);
    }

    private static class Node {
        private boolean isKey;
        private HashMap<Character, Node> next;
        private Node(boolean b) {
            next = new HashMap<>();
            isKey = b;
        }

        private void addChar(char c, Node n) {
            next.put(c, n);
        }

        private Node getNext(char c) {
            return next.get(c);
        }

        private boolean hasChar(char c) {
            return next.containsKey(c);
        }

        private boolean isKey() {
            return isKey;
        }

        private Set<Character> getKeys() {
            return next.keySet();
        }

        private void changeKey(boolean val) {
            isKey = val;
        }

    }

    @Override
    public void clear() {
        root = new Node(false);
    }

    @Override
    public boolean contains(String key) {
        Node k = root;
        for (int i = 0; i < key.length(); i++) {
            if (k.hasChar(key.charAt(i))) {
                k = k.getNext(key.charAt(i));
            } else {
                return false;
            }
        }
        return k.isKey();
    }

    @Override
    public void add(String key) {
        Node k = root;
        for (int i = 0; i < key.length(); i++) {
            if (!k.hasChar(key.charAt(i))){
                k.addChar(key.charAt(i), new Node(key.length() == (i + 1)));
            } else {
                k.getNext(key.charAt(i)).changeKey(key.length() == (i + 1));
            }
            k = k.getNext(key.charAt(i));
        }
    }

    private void colHelp(String s, List<String> x, Node ni) {
        if (ni.isKey()) {
            x.add(s);
        }

        for (Character c : ni.getKeys()) {
            colHelp(s + c, x, ni.getNext(c));
        }
    }

    @Override
    public List<String> keysWithPrefix(String prefix) {
        Node curr = root;
        for (int i = 0; i < prefix.length(); i++) {
            if (curr.hasChar(prefix.charAt(i))) {
                curr = curr.getNext(prefix.charAt(i));
            }
        }

        ArrayList<String> care = new ArrayList<>();
        for (Character c : curr.getKeys()) {
            colHelp(prefix + c, care, curr.getNext(c));
        }

        return care;
    }


    @Override public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException("Not supported operation!");
    }
}
