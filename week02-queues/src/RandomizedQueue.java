import java.util.NoSuchElementException;
import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        size = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        if (size == items.length)
            items = resizeArray(items, 2 * size);

        items[size++] = item;
        swap(items, size - 1, StdRandom.uniform(size));
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0)
            throw new NoSuchElementException();

        Item item = items[size - 1];
        items[size - 1] = null;

        if (size-- == items.length / 4)
            items = resizeArray(items, items.length / 2);

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0)
            throw new NoSuchElementException();

        int index = StdRandom.uniform(size);

        return items[index];
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        Item[] iteratorArray;
        int index;

        public RandomizedQueueIterator() {
            iteratorArray = (Item[]) new Object[size];
            int i = 0;

            while (i < size) {
                iteratorArray[i] = items[i];
                i++;
                swap(iteratorArray, i - 1, StdRandom.uniform(i));
            }
        }

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            if (index == size)
                throw new NoSuchElementException();
            return iteratorArray[index++];
        }

    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void printAll() {
        for (Item it : items)
            System.out.println(it);
    }

    private Item[] resizeArray(Item[] original, int length) {
        Item[] result = (Item[]) new Object[length];

        for (int i = 0; i < Math.min(original.length, size); i++)
            result[i] = original[i];

        return result;
    }

    private void swap(Item[] array, int left, int right) {
        Item old = array[left];
        array[left] = array[right];
        array[right] = old;
    }

    // unit testing (optional)
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();

        rq.enqueue("O");
        rq.enqueue("Rato");
        rq.enqueue("Roeu");
        rq.enqueue("A");
        rq.enqueue("Roupa");
        rq.enqueue("Do");
        rq.enqueue("Rei");
        rq.enqueue("De");
        rq.enqueue("Roma");

        for (int i = 1; i <= 5; i++) {
            System.out.println("Iteration #" + (i + 1));
            for (String element : rq)
                System.out.println("  " + element);
        }

        try {
            for (int it = 1; it <= 3; it++) {
                System.out.println("Contents at iteration #" + it + ":");
                for (String element : rq)
                    System.out.print(" " + element);
                System.out.println();
                for (int i = 0; i < 5; i++) {
                    System.out.print("size = " + rq.size() + ", ");
                    System.out.println("rq.dequeue() = " + rq.dequeue());
                }
            }
        }
        catch (NoSuchElementException ex) {
            System.out.println("No more elements.");
        }
    }

}
