package bearmaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 *
 * @source NaiveMinPQ.java
 */

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    private ArrayList<PriorityNode> items;
    private HashMap<T, PriorityNode> itemToNode;

    private class PriorityNode implements Comparable<PriorityNode> {
        private T item;
        private double priority;
        private int index;

        PriorityNode(T e, double p, int index) {
            this.item = e;
            this.priority = p;
            this.index = index;
        }

        T getItem() {
            return item;
        }

        double getPriority() {
            return priority;
        }

        int getIndex() {
            return index;
        }

        void changeIndex(int ind) {
            this.index = ind;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(PriorityNode other) {
            if (other == null) {
                return -1;
            }
            return Double.compare(this.getPriority(), other.getPriority());
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (o == null || o.getClass() != this.getClass()) {
                return false;
            } else {
                return ((PriorityNode) o).getItem().equals(getItem());
            }
        }

        @Override
        public int hashCode() {
            return item.hashCode();
        }
    }

    public ArrayHeapMinPQ() {
        items = new ArrayList<PriorityNode>();
        itemToNode = new HashMap<T, PriorityNode>();
        items.add(null);
    }

    @Override
    public void add(T item, double priority) {
        //Should check whether the item exists since no duplicates should exist!
        if (contains(item)) {
            throw new IllegalArgumentException("Item already exists! No duplicates aloud!");
        }
        int lastIndex = size() + 1;
        PriorityNode newNode = new PriorityNode(item, priority, lastIndex);
        items.add(newNode);

        //TO DO: Check if newNode dynamically updates. if it does good, if it doesn't bad.:
        itemToNode.put(newNode.getItem(), newNode);

        swim(lastIndex);
    }

    private int parent(int k) {
        return k / 2;
    }

    private int leftChild(int k) {
        return k * 2;
    }

    private int rightChild(int k) {
        return k * 2 + 1;
    }

    private void swim(int k) {
        if (k > 1 && parent(k) > 0) {
            if (items.get(parent(k)).getPriority() > items.get(k).getPriority()) {
                swap(k, parent(k));
                swim(parent(k));
            }
        }
    }

    private int find(T item) {
        if (!itemToNode.containsKey(item)) {
            return -1;
        }
        return itemToNode.get(item).getIndex();
    }

    private void swimDown(int k) {
        //TO DO: Handle the case where the max depth has been reached. (CANNOT exceed the max size)
        if (k < size() && rightChild(k) <= size()) {
            double rightPriority = items.get(rightChild(k)).getPriority();
            double leftPriority = items.get(leftChild(k)).getPriority();
            double currPriority = items.get(k).getPriority();
            if (leftPriority < currPriority && leftPriority <= rightPriority) {
                swap(leftChild(k), k);
                swimDown(leftChild(k));
            } else if (rightPriority < currPriority && rightPriority < leftPriority) {
                swap(rightChild(k), k);
                swimDown(rightChild(k));
            }
        } else if (k < size() && leftChild(k) <= size()) {
            //Right child should not be available since it doesn't pass the upper if-case
            double leftPriority = items.get(leftChild(k)).getPriority();
            double currPriority = items.get(k).getPriority();
            if (leftPriority < currPriority) {
                swap(leftChild(k), k);
                swimDown(leftChild(k));
            }
        }
    }

    private void swap(int child, int par) {
        PriorityNode temp = items.get(child);

        items.get(par).changeIndex(child);
        temp.changeIndex(par);

        items.set(child, items.get(par));
        items.set(par, temp);
    }

    @Override
    public boolean contains(T item) {
        if (find(item) == -1) {
            return false;
        }
        return true;
    }

    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException("Heap is empty!");
        }
        return items.get(1).getItem();
    }

    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException("Heap is empty!");
        }
        T ret = items.get(1).getItem();
        int lastIndex = size();
        items.set(1, items.get(lastIndex)); //First index is 1, NOT 0

        items.remove(lastIndex);
        itemToNode.remove(ret);

        swimDown(1);
        return ret;
    }

    @Override
    public int size() {
        return items.size() - 1;
    }

    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("Item does not exist!");
        }
        double oldPriority = itemToNode.get(item).getPriority();
        int oldIndex = itemToNode.get(item).getIndex();

        itemToNode.get(item).setPriority(priority);

        if (oldPriority < priority) {
            swimDown(oldIndex);
        } else {
            swim(oldIndex);
        }
    }
}
