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
        MinFourHeap largeHeap = new MinFourHeap(comparator);
        for (int i = 0; i < k; i++) {   
            largeHeap.add((Comparable) array[i]);
            array[i] = null;
        }
        for (int i = k; i < array.length; i++)  { // Swap out for larger values if need be
            if (comparator.compare(array[i], (E) largeHeap.peek()) > 0)  {
                largeHeap.next();
                largeHeap.add((Comparable) array[i]);
            }
            array[i] = null;
        }    
        for (int i = 0; i < k; i++) {   // Add the k elements from the sorted heap
            array[i] = (E) largeHeap.next();
        }
    }
}
