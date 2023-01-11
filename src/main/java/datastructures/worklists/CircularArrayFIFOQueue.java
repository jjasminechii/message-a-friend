package datastructures.worklists;

import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
    private E[] stack;
    private int front;
    private int back;
    private int size;

    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        stack = (E[])new Comparable[capacity];
        front = 0;
        back = -1;
        size = 0;
    }

    @Override
    public void add(E work) {
        if(!isFull()){
            back = (back + 1) % capacity();
            stack[back] = work;
            size++;
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    public E peek() {
        if(!hasWork()){
            throw new NoSuchElementException();
        } else {
            return stack[front];
        }
    }

    @Override
    public E peek(int i) {
        if (!hasWork()){
            throw new NoSuchElementException();
        } else if(i < 0 || i >= size()) {
            throw new IndexOutOfBoundsException();
        } else {
            return stack[(front + i) % capacity()];
        }
    }

    @Override
    public E next() {
        if(!hasWork()){
            throw new NoSuchElementException();
        } else {
            size--;
            E returnValue = stack[front];
            front = (front + 1) % capacity();
            return returnValue;
        }
    }

    @Override
    public void update(int i, E value) {
        if(!hasWork()) {
            throw new NoSuchElementException();
        } else if (i < 0 || i >= size()){
            throw new IndexOutOfBoundsException();
        } else {
            stack[(front + i) % capacity()] = value;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        front = 0;
        back = -1;
        size = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        int compareValue = 0;
        for(int i = 0; i < Math.min(this.size(), other.size()); i++){
            compareValue = this.peek(i).compareTo(other.peek(i));
            if(compareValue != 0){
                return compareValue;
            }
        }
        return this.size() - other.size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;
            return this.compareTo(other) == 0;
        }
    }

    @Override
    public int hashCode() {
        int current = back;
        current = 31 * current + front;
        current = 31 * current + Arrays.hashCode(stack);
        return current;
    }
}