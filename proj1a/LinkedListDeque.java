public class LinkedListDeque<T> {
    private class Node {
        private final T item;
        private Node prev;
        private Node next;

        public Node(T i, Node p,Node n){
            item = i;
            prev = p;
            next = n;
        }
    }

    private final Node sentinel;
    private int size;

    public LinkedListDeque(){
        sentinel = new Node(null,null,null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }
    public void addFirst(T item) {
        Node newItem = new Node(item,sentinel,sentinel.next);
        sentinel.next.prev = newItem;
        sentinel.next = newItem;
        size += 1;
    }
    public void addLast(T item){
        Node newItem = new Node(item,sentinel.prev,sentinel);
        sentinel.prev.next = newItem;
        sentinel.prev = newItem;
        size += 1;
    }
    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size(){
        return this.size;
    }
    public void printDeque() {
        Node ptr = sentinel.next;
        while (ptr != sentinel) {
            System.out.print(ptr.item + " ");
            ptr = ptr.next;
        }
    }
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T ret = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return ret;
    }
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T ret = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return ret;
    }
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        Node ptr = sentinel;
        for (int i = 0; i <= index; i++) {
            ptr = ptr.next;
        }
        return ptr.item;
    }
    private T getRecursiveHelper (Node list, int i) {
        if(list == sentinel){
            return null;
        } else if(i == 0){
            return list.item;
        } else {
            return getRecursiveHelper(list.next, i-1);
        }
    }
    public T getRecursive(int index){
        if (index >= size){
            return null;
        }else {
            return getRecursiveHelper(sentinel.next, index -1);
        }
    }

}
