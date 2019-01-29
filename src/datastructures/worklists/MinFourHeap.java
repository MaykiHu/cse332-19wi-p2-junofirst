package datastructures.worklists;

import cse332.interfaces.worklists.PriorityWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;
    private int capacity;
    
    public MinFourHeap() {
        setup();
    }
    
    @SuppressWarnings("unchecked")
	private void setup() {
    	capacity = 10;
    	data = (E[])new Comparable[capacity];
    	size = 0;
    }
    
    @Override
    public boolean hasWork() {
        return super.hasWork();
    }

    @Override
    public void add(E work) {
        data[size] = work;
        size++;
        int childLoc = size - 1;
        E childData = data[childLoc];    
        while (childLoc > 0 && childData.compareTo(data[getParentLoc(childLoc)]) < 0) {
            data[childLoc] = data[getParentLoc(childLoc)];
            childLoc = getParentLoc(childLoc);
        }                   
        data[childLoc] = childData;
        if (size == data.length) {
        	capacity *= 2;
        	@SuppressWarnings("unchecked")
			E[] newData = (E[])new Comparable[capacity];
        	for (int i = 0; i < data.length; i++) {
        	  	newData[i] = data[i];
        	}
        	data = newData;
    	}
    }
    
    private int getParentLoc(int childLoc) {
    	return (childLoc - 1) / 4;
    }

    private int getChildLoc(int parentLoc) {
    	return 4 * parentLoc + 1;
    }
    
    @Override
    public E peek() {
        if (!hasWork()) {
          	throw new NoSuchElementException();
        }
        return data[0];
    }

    @Override
    public E next() {
        if (!hasWork()) {
        	throw new NoSuchElementException();
        }
        E min = data[0];
        size--;
        data[0] = data[size];
        data[size] = null;
        int childLoc;
        int parentLoc = 0;
        E loc = data[parentLoc];
        boolean isOrdered = false;
        while (4 * parentLoc + 1 < size && !isOrdered) {
            childLoc = priorityChildLoc(parentLoc);
            if (data[childLoc].compareTo(loc) < 0) {
                data[parentLoc] = data[childLoc];
                parentLoc = childLoc;
            } else {
            	isOrdered = true;
            }
        }
        data[parentLoc] = loc;
        return min;
    }

    public int priorityChildLoc(int parentLoc) {
    	int priorityChildLoc = getChildLoc(parentLoc);
    	int nChild = 1;
    	int nextChildLoc = priorityChildLoc + 1;
    	while (nextChildLoc < size && nChild < 4) {
    		if (data[nextChildLoc].compareTo(data[priorityChildLoc]) < 0) {
    			priorityChildLoc = nextChildLoc;
    		}
    		nChild++;
    		nextChildLoc++;
    	}
    	return priorityChildLoc;
    }
    
    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        setup();
    }
}