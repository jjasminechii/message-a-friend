package datastructures.worklists;

import process.interfaces.worklists.PriorityWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeapComparable<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size = 0;
    public MinFourHeapComparable() {
        data = (E[])new Comparable[10];
        size = 0;
    }

    @Override
    public boolean hasWork() {
        return size > 0;
    }

    @Override
    public void add(E work){
        if(size >= data.length){
            E[] newData = (E[]) new Comparable[data.length * 2];
            for(int i = 0; i < data.length; i++){
                newData[i] = data[i];
            }
            data = newData;
        }
//        if(size == 0){
//            data[0] = work;
//        }
//        else{
            data[size] = work;
            percolateUp(size);
//        }
        size++;
    }

    @Override
    public E peek() {
        if(!hasWork()){
            throw new NoSuchElementException();
        }
        return data[0];
    }

    @Override
    public E next() {
        if(!hasWork()){
            throw new NoSuchElementException();
        }
        size--;
        E returnValue = data[0];
        data[0] = data[size];
        data[size] = null;
        percolateDown(0);
        return returnValue;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        data = (E[])new Comparable[10];
        this.size = 0;
    }

    private void percolateUp(int node){
        if(node == 0){
            return;
        }
        int parent = (node - 1) / 4;
        if(data[parent].compareTo(data[node]) > 0){
            E temp = data[parent];
            data[parent] = data[node];
            data[node] = temp;
            percolateUp(parent);
        }
    }

    private void percolateDown(int node){
        if(node >= size){
            return;
        }
        int child = node*4+1;
        int smallestVal = node;
        //finds the smallest node out of the 4 children to swap with
        for(int i = 0; i < 4; i++){
            if(child+i < data.length && data[child+i] != null){
                if(data[child+i].compareTo(data[smallestVal]) < 0){
                    smallestVal = child+i;
                }
            }
        }
        //swap the node if it wasn't the starting node
        if(smallestVal != node){
            E temp = data[smallestVal];
            data[smallestVal] = data[node];
            data[node] = temp;
            percolateDown(smallestVal);
        }
    }

    //Used for testing
    private void printArray(){
        for(int i = 0; i < size; i++){
            System.out.print(data[i] +" ");
        }
        System.out.println();
    }
}
