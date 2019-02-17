package p2.sorts;

import java.util.Comparator;
import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

public class HeapSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        sort(array, (x, y) -> x.compareTo(y));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <E> void sort(E[] array, Comparator<E> comparator) {
        MinFourHeap sortedHeap = new MinFourHeap(comparator);
        for (E item : array) {
            sortedHeap.add((Comparable) item);
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = (E) sortedHeap.next();
        }
    }
}
