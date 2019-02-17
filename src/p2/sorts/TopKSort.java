package p2.sorts;

import java.util.Comparator;
import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        MinFourHeap sortedHeap = new MinFourHeap(comparator);
        // Insert here a sort where the k-elements in array is the largest
        for (int i = 0; i < k; i++) {   // These k elements should be the largest k
            sortedHeap.add((Comparable) array[i]);
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = null;
        }
        for (int i = 0; i < k; i++) {   // Add the k elements from the sorted heap
            array[i] = (E) sortedHeap.next();
        }
    }
}
