package edu.sdccd.cisc191;

import java.util.LinkedList;

public class GenericMatchQueue<T> {

    private final LinkedList<T> items = new LinkedList<>();

    public void enqueue(T item) {
        items.addLast(item);
    }

    public T dequeue() {
        if (items.isEmpty()) {
            throw new IllegalStateException();
        }
        return items.pop(); // delete the first value and return it
    }

    public T peek() {
        if (items.isEmpty()) {
            throw new IllegalStateException();
        }
        return items.peek(); // return first value only
    }

    public boolean isEmpty() {
        if (items.isEmpty()) {
            return true;
        }
        return false;
    }

    public int size() {
        return items.size();
    }
}
