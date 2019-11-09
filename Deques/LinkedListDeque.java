public class LinkedListDeque<T> implements Deque<T> {
    //Add any instance variables here

    private BlorpNode sentinel;
    private int size;


    private class BlorpNode {
        private T item;
        private BlorpNode next;
        private BlorpNode prev;

        BlorpNode(T b, BlorpNode n, BlorpNode p) {
            item = b;
            next = n;
            prev = p;
        }

    }

    public LinkedListDeque() {
        sentinel = new BlorpNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /**
     *
     * @source Josh Hug Copy Constructor Project 1A Youtube Video
     */
    public LinkedListDeque(LinkedListDeque other) { //Creates a deep copy
        sentinel = new BlorpNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;

        for (int i = 0; i < other.size(); i++) {
            addLast((T) other.get(i));
        }
    }

    @Override
    public void addFirst(T item) {
        BlorpNode oldFirst = sentinel.next;
        BlorpNode newFirst = new BlorpNode(item, oldFirst, sentinel);
        oldFirst.prev = newFirst;
        sentinel.next = newFirst;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        BlorpNode oldLast = sentinel.prev;
        BlorpNode newLast = new BlorpNode(item, sentinel, oldLast);
        oldLast.next = newLast;
        sentinel.prev = newLast;
        size += 1;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        BlorpNode pointer = sentinel.next;
        while (pointer != sentinel.prev) { //watch out might need a speical equals method
            System.out.print(pointer.item + " ");
            pointer = pointer.next;
        }
        System.out.println(sentinel.prev.item + "");
    }

    @Override
    public T removeFirst() {
        BlorpNode second = sentinel.next.next;
        BlorpNode first = sentinel.next;

        if (isEmpty()) {
            return null;
        }

        second.prev = sentinel;
        sentinel.next = second;

        size -= 1;
        return first.item;
    }

    @Override
    public T removeLast() {
        BlorpNode secondLast = sentinel.prev.prev;
        BlorpNode last = sentinel.prev;

        if (isEmpty()) {
            return null;
        }

        secondLast.next = sentinel;
        sentinel.prev = secondLast;

        size -= 1;
        return last.item;
    }

    @Override
    public T get(int index) {
        BlorpNode pointer = sentinel.next;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                return pointer.item;
            } else {
                pointer = pointer.next;
            }
        }
        return null;
    }

    public T getRecursive(int index) {
        return recursiveHelper(sentinel.next, index);
    }

    private T recursiveHelper(BlorpNode b, int index) {
        if (index == 0) {
            return b.item;
        } else {
            return recursiveHelper(b.next, index - 1);
        }
    }
}
