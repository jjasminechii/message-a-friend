package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {

    private final int defaultSize = 10;
    private E[] stack;
    private int size;

    public ArrayStack() {
        stack = (E[])new Object[defaultSize];
        size = 0;
    }

    @Override
    public void add(E work) {
        if(size >= stack.length){
            E[] newStack = (E[]) new Object[stack.length * 2];
            for(int i = 0; i < stack.length; i++){
                newStack[i] = stack[i];
            }
            stack = newStack;
        }
        stack[size] = work;
        size++;
    }

    @Override
    public E peek() {
        if(!hasWork()){
            throw new NoSuchElementException();
        }
        return stack[size - 1];
    }

    @Override
    public E next() {
        if(!hasWork()){
            throw new NoSuchElementException();
        }
        size--;
        return stack[size];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        stack = (E[])new Object[defaultSize];
        size = 0;
    }
}