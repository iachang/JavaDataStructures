public class ArrayDeque<T> {
    //Add any instance variables here

    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int arrLength;

    private int incLast(int last) {
        return Math.floorMod(last + 1, items.length);
    }

    private int decFirst(int first) {
        return Math.floorMod(first - 1, items.length);
    }

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        arrLength = 8;
        nextFirst = 0;
        nextLast = 1;
    }

    /**
     *
     * @source Josh Hug Copy Constructor Project 1A Youtube Video
     */
    public ArrayDeque(ArrayDeque other) { //Creates a deep copy
        items = (T[]) new Object[8];
        size = 0;
        arrLength = 8;
        nextFirst = 0;
        nextLast = 1;
        for (int i = 0; i < other.size(); i++) {
            addLast((T) other.get(i));
        }
    }

    /**
     *
     * @source CS61B Lecture 6 slides
     */
    private void resize(int newSize) {
        T[] augment = (T[]) new Object[newSize];
        int oldLength = arrLength;
        arrLength = newSize;

        int starter = incLast(nextFirst);
        if (starter > decFirst(nextLast)) {
            int partitionOne = oldLength - starter;
            int partitionTwo = oldLength - partitionOne;
            System.arraycopy(items, starter, augment, 0, partitionOne);
            System.arraycopy(items, 0, augment, partitionOne, partitionTwo);
        } else {
            System.arraycopy(items, starter, augment, 0, size);
        }
        nextLast = size;
        nextFirst = arrLength - 1;
        items = augment;
    }

    public void addFirst(T item) {
        if (size >= arrLength) {
            resize(size * 2);
        }
        items[nextFirst] = item;
        nextFirst = decFirst(nextFirst);
        size += 1;
    }

    public void addLast(T item) {
        if (size >= arrLength) {
            resize(size * 2);
        }
        items[nextLast] = item;
        nextLast = incLast(nextLast);
        size += 1;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        int indexSweep = (nextFirst + 1) % arrLength;
        for (int i = 0; i < size; i++) {
            System.out.print(items[indexSweep] + " ");
            indexSweep = (indexSweep + 1) % arrLength;
        }
        System.out.println();
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        } else if ((float) size / arrLength < 0.25 && arrLength >= 16) {
            resize(arrLength / 2);
        }

        int firstIndex = (nextFirst + 1) % arrLength;
        T first = items[firstIndex];
        items[firstIndex] = null;
        size -= 1;
        nextFirst = firstIndex;
        return first;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        } else if ((float) size / arrLength < 0.25 && arrLength >= 16) {
            resize(arrLength / 2);
        }

        int lastIndex = decFirst(nextLast);
        T last = items[lastIndex];
        items[lastIndex] = null;
        size -= 1;
        nextLast = lastIndex;
        return last;
    }

    public T get(int index) {
        int newIndex = (nextFirst + index + 1) % arrLength;
        if (index > size) {
            return null;
        }
        return items[newIndex];
    }
}
