public class ArrayDeque<T> {
    private final int INIT_CAPACITY = 8;
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[INIT_CAPACITY];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    //    Specifically, any deque implementation must have exactly the following operations:
    // Adds an item of type T to the front of the deque
    private void resize() {
        if (size == items.length) {
            resizeHelper(items.length * 2);
        }
        if (size() < (items.length) * 0.25 && items.length > 8) {
            resizeHelper(items.length / 2);
        }
    }

    private void resizeHelper(int capacity) {
        T[] tempArr = items;
        int begin = plusOne(nextFirst);
        int end = minusOne(nextLast);
        items = (T[]) new Object[capacity];
        nextFirst = 0;
        nextLast = 1;
        for (int i = begin; i != end; i = plusOne(i, tempArr.length)) {
            items[nextLast] = tempArr[i];
            nextLast = plusOne(nextLast);
        }
        items[nextLast] = tempArr[end];
        nextLast = plusOne(nextLast);

    }
    
    private int minusOne(int index) {
        return Math.floorMod(index - 1, items.length);
    }

    private int plusOne(int index) {
        return Math.floorMod(index + 1, items.length);
    }

    private int plusOne(int index, int length) {
        return Math.floorMod(index + 1, length);
    }

    public void addFirst(T item) {
        resize();
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size++;
    }

    //    : Adds an item of type T to the back of the deque.
    public void addLast(T item) {
        resize();
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    //    Returns true if deque is empty, false otherwise.
    public boolean isEmpty() {
        return this.size == 0;
    }

    //    Returns the number of items in the deque.
    public int size() {
        return this.size;
    }

    //    Prints the items in the deque from first to last, separated by a space.
    private void printRawDeque() {
        for (T item
                :
                items) {
            System.out.print(item + " ");
        }
        System.out.println();
    }

    public void printDeque() {
        int iter = plusOne(nextFirst);
        for (int i = 0; i < size; i++) {
            System.out.print(items[iter] + " ");
            iter = plusOne(iter);
        }
        System.out.println();
    }
    //    Removes and returns the item at the front of the deque.
    //    If no such item exists, returns null.

    private T getFirst() {
        return items[plusOne(nextFirst)];
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        resize();
        T ret = getFirst();
        nextFirst = plusOne(nextFirst);
        items[nextFirst] = null;
        size--;
        return ret;
    }
    //    Removes and returns the item at the back of the deque.
    //    If no such item exists, returns null.

    private T getLast() {
        return items[minusOne(nextLast)];
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        resize();
        T ret = getLast();
        nextLast = minusOne(nextLast);
        items[nextLast] = null;
        size--;
        return ret;
    }
    //    Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
    //    If no such item exists, returns null. Must not alter the deque!

    public T get(int index) {
        if (index >= size || index < 0 || isEmpty()) {
            return null;
        }
        index = Math.floorMod(plusOne(nextFirst) + index, items.length);
        return items[index];
    }

}
