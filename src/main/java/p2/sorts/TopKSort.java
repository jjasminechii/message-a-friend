package p2.sorts;

import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    /**
     * Behaviour is undefined when k > array.length
     */
    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        MinFourHeap<E> current = new MinFourHeap<>(comparator);
        if(k > array.length){
            k = array.length;
        }
        for(int i = 0; i < k; i++){
            current.add(array[i]);
        }

        for (int j = k; j < array.length; j++){
            if(comparator.compare(array[j], current.peek()) > 0) {
                current.next();
                current.add(array[j]);
            }
            array[j] = null;
        }

        for(int m = 0; m < k; m++){
            array[m] = current.next();
        }
    }
}
