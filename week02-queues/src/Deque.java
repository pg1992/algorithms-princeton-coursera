import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Node before;
        Node next;
        Item item;
    }

    private Node first;
    private Node last;
    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node current = new Node();
        current.item = item;

        if (first == null)
            last = current;
        else {
            first.before = current;
            current.next = first;
        }

        first = current;
        size++;
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        Node current = new Node();
        current.item = item;

        if (last == null)
            first = current;
        else {
            last.next = current;
            current.before = last;
        }

        last = current;
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null)
            throw new NoSuchElementException();

        Item item = first.item;
        first = first.next;

        if (first != null)
            first.before = null;
        else
            last = null;

        size--;

        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (last == null)
            throw new NoSuchElementException();

        Item item = last.item;
        last = last.before;

        if (last != null)
            last.next = null;
        else
            first = null;

        size--;

        return item;
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (current == null)
                throw new NoSuchElementException();

            Item item = current.item;
            current = current.next;

            return item;
        }

    }

    // return an iterator over items in order from front to end
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (optional)
    public static void main(String[] args) {
        Deque<String> ds = new Deque<>();

        for (int i = 0; i < 20; i++)
            ds.addFirst(String.valueOf(i));

        System.out.println("ds.size() = " + ds.size());

        for (int i = 0; i < 20; i++)
            System.out.print(" " + ds.removeLast());

        System.out.println("\nds.size() = " + ds.size());

        for (int i = 0; i < 30; i++)
            ds.addLast(String.valueOf(i));

        System.out.println("ds.size() = " + ds.size());

        for (int i = 0; i < 30; i++)
            System.out.print(" " + ds.removeLast());

        System.out.println("\nds.size() = " + ds.size());
    }

}
