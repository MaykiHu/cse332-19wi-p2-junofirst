package p2.sorts;

import java.util.Comparator;

public class QuickSort {
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        if (array != null) {
            quickSort(array, comparator, 0, array.length - 1);
        }
    }

    // Quick sorts an array
    private static <E> void quickSort(E[] array, Comparator<E> comparator, int left, int right) {
        int size = right - left + 1;
        if (size <= 3) { // Base case: There are at most 3 remaining things to sort 
            shortSort(array, comparator, left, right);
        } else { // Recursive quickSort
            E pivot = median(array, comparator, left, right);
            int pivotIndex = split(array, comparator, left, right, pivot);
            quickSort(array, comparator, left, pivotIndex - 1);
            quickSort(array, comparator, pivotIndex + 1, right);
        }
    }

    // Sorting a small element size
    private static <E> void shortSort(E[] array, Comparator<E> comparator, int left, int right) {
        int size = right - left + 1;
        if (size > 1) { // If more than 1 element, we would have to perform sorts, else none
            if (size == 2 && comparator.compare(array[left], array[right]) > 0) {
                swap(array, left, right);
            } else { // Size is 3
                sortThree(array, comparator, left, right - 1, right);
            }
        }
    }

    // Sorts from largest to smallest given 3 elements
    private static <E> void sortThree(E[] array, Comparator<E> comparator, int left, int mid, int right) {
        if (comparator.compare(array[mid], array[left]) < 0) {
            swap(array, mid, left);
        }
        if (comparator.compare(array[right], array[left]) < 0) {
            swap(array, right, left);
        }
        if (comparator.compare(array[right], array[mid]) < 0) {
            swap(array, right, mid);
        }
    }
    
    // Finds median of 3 values and sorts them from smallest to greatest
    private static <E> E median(E[] array, Comparator<E> comparator, int left, int right) {
        int mid = (left + right) / 2;
        sortThree(array, comparator, left, (left + right) / 2, right);
        swap(array, mid, right - 1); // Swap our pivot to second-last pos, so we split less checks
        return array[right - 1];
    }
    
    // Swaps elements given two indices
    private static <E> void swap(E[] array, int first, int second) {
        E temp = array[first];
        array[first] = array[second];
        array[second] = temp;
    }
    
    // Splits the array into chunks to sort
    private static <E> int split(E[] array, Comparator<E> comparator, int left, int right, E pivot) {
        int leftPos = left + 1;
        int rightPos = right - 2;
        while (leftPos < rightPos) { // When done looking at halves
            while (comparator.compare(array[leftPos], pivot) < 0) { // Find where smaller than pivot
                leftPos++;
            }
            while(comparator.compare(array[rightPos], pivot) > 0) { // Find where larger than pivot
                rightPos--;
            }
            if (leftPos < rightPos) { // If it exited above while loops, needs to swap small-large
                swap(array, leftPos, rightPos);
            }
        }
        swap(array, leftPos, right - 1); // Swap back pivot from second to last pos as center
        return leftPos; // Our pivot index~ we've made it to the middle of our small-pivot-large
    }
}