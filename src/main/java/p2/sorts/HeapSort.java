package p2.sorts;

import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class HeapSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        MinFourHeap<E> current = new MinFourHeap<>(comparator);
        for (int i = 0; i < array.length; i++){
            current.add(array[i]);
        }
        for(int j = 0; j < array.length; j++){
            array[j] = current.next();
        }
    }
}
