package p2.sorts;

import cse332.exceptions.NotYetImplementedException;

import java.util.Comparator;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        quickSort(array, comparator, 0, array.length - 1);
    }

    private static <E> void quickSort(E[] array, Comparator<E> comparator, int left, int right) {
        if(left < right) {
            int pivot = median(array, comparator, left, right);
            quickSort(array, comparator, left, pivot - 1);
            quickSort(array, comparator, pivot + 1, right);
        }
    }
    public static <E> int median(E[] array, Comparator<E> comparator, int left, int right) {
        E pivot = array[right];
        int currentLeft = (left - 1);
        for (int j = left; j < right; j++) {
            if (comparator.compare(array[j], pivot) < 0) {
                currentLeft++;
                swapReferences(array, currentLeft, j);
            }
        }
        swapReferences(array, currentLeft + 1, right);
        return currentLeft + 1;
    }

    private static <E> void swapReferences(E[] array, int left, int right) {
        E current = array[left];
        array[left] = array[right];
        array[right] = current;
    }
}