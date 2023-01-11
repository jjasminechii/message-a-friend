package datastructures.worklists;

import process.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {
    private class Node{

        private E work;
        private Node next;

        private Node(){
            work = null;
            next = null;
        }
        private Node(E work){
            this.work = work;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    public ListFIFOQueue() {
        head = new Node();
        tail = head;
        size = 0;
    }

    @Override
    public void add(E work) {
        if(size == 0){
            head.work = work;
        }
        else {
            tail.next = new Node(work);
            tail = tail.next;
        }
        size++;
    }

    @Override
    public E peek() {
        if(!hasWork()){
            throw new NoSuchElementException();
        }
        return head.work;
    }

    @Override
    public E next() {
        if(!hasWork()){
            throw new NoSuchElementException();
        }
        E returnWork = head.work;
        head = head.next;
        size--;
        if(head == null){
            clear();
        }
        return returnWork;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = new Node();
        tail = head;
        size = 0;
    }
}
