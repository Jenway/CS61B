public class ArrayDeque<T> {

    private T[] items;
    private int size;
    private int length;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        length = 8;
        nextFirst = 4;
        nextLast = 5;
    }

//    Specifically, any deque implementation must have exactly the following operations:
// Adds an item of type T to the front of the deque
    private void grow(){
//  This code works aiming to behave like below 2 e.g.

//        [6,7,8,1,2,3,4,5] last:2,front:3
//        [6,7,8,n,n,n,n,n,n,n,n,1,2,3,4,5] last:2 front: 11=(3 + length)

//        [1,2,3,4,5,6,7,8] last:7,front:0
//        [n,n,n,n,n,n,n,n,1,2,3,4,5,6,7,8] last: plusOne(7) front: 0 + length
        T[] newArray = (T[]) new Object[length * 2];
        int iter = plusOne(nextFirst);
        while (iter < length){
            newArray[iter + length] = items[iter];
            iter++;
        }
        int last_iter = minusOne(nextLast);
        if (last_iter == length){
            nextLast = 0;
        }else {
            while (last_iter >= 0) {
                newArray[last_iter] = items[last_iter];
                last_iter--;
            }
        }
        nextFirst += length;
        length *= 2;
        items = newArray;
    }
    private void shrink(){

//        [6,7,8,n,n,n,n,n,n,n,n,1,2,3,4,5] last:2 front: 11=(3 + length)
//        [6,7,8,1,2,3,4,5] last:2,front:3

//        [n,n,n,n,n,n,n,n,1,2,3,4,5,6,7,8] last: plusOne(7) front: 0 + length
//        [1,2,3,4,5,6,7,8] last:7,front:0

        T[] newArray = (T[]) new Object[length / 2];
        int iter = plusOne(nextFirst);
        int newArrayIndex = 0;

        while (iter != nextLast) {
            newArray[newArrayIndex] = items[iter];
            iter = plusOne(iter);
            newArrayIndex++;
        }

        nextFirst = 0;
        nextLast = newArrayIndex;

        length /= 2;
        items = newArray;
    }
    private int minusOne(int index){
        if (index == 0) {
            return length - 1;
        }
        return index - 1;
    }
    private int plusOne(int index){
        if (index == length - 1) {
            return 0;
        }
        return index + 1;
    }
    public void addFirst(T item){
        if (size == length) {
            grow();
        }
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }
//    : Adds an item of type T to the back of the deque.
    public void addLast(T item){
        if (size == length) {
            grow();
        }
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }
//    Returns true if deque is empty, false otherwise.
    public boolean isEmpty(){
        return this.size == 0;
    }
    //    Returns the number of items in the deque.
    public int size(){
        return this.size;
    }
//    Prints the items in the deque from first to last, separated by a space.
    public void printRawDeque(){
        for (T item:
                items) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
    public void printDeque(){
        int iter = plusOne(nextFirst);
        for (int i = 0; i < size; i++) {
            System.out.print(items[iter] + " ");
            iter = plusOne(iter);
        }
        System.out.println();
    }
    //    Removes and returns the item at the front of the deque. If no such item exists, returns null.

    public T removeFirst(){
        if (size == 0) {
            return null;
        }
        nextFirst = plusOne(nextFirst);
        T ret = items[nextFirst];
        size--;
        if (length >= 16 && length / size >= 4) {
            shrink();
        }
        return ret;
    }
    //    Removes and returns the item at the back of the deque. If no such item exists, returns null.

    public T removeLast(){
        if (size == 0) {
            return null;
        }
        nextLast = minusOne(nextLast);
        T ret = items[nextLast];

        size--;
        if (length >= 16 && length / size >= 4) {
            shrink();
        }
        return ret;
    }
    //    Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. Must not alter the deque!

    public T get(int index){
        if (index > size){
            return null;
        }
        index = (index + plusOne(nextFirst)) % length;
        return items[index];
    }

}
