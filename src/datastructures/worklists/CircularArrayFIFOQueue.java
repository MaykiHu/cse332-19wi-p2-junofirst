package datastructures.worklists;

import java.util.NoSuchElementException;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E> extends FixedSizeFIFOWorkList<E> {
	private int front;
	private int back;
	private int size;
	private E[] queue;
	
    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        setup(capacity);
    }

    @SuppressWarnings("unchecked")
	private void setup(int capacity) {
    	front = 0;
        back = -1;
        size = 0;
        queue = (E[])new Comparable[capacity];
    }
    
    @Override
    public void add(E work) {
        if (isFull()) {
        	throw new IllegalStateException();
        } 
        back = (back + 1) % capacity();
        queue[back] = work;
        size++;
    }

    @Override
    public E peek() {
    	if (!hasWork()) {
        	throw new NoSuchElementException();
        }
        return queue[front];
    }
    
    @Override
    public E peek(int i) {
    	if (!hasWork()) {
        	throw new NoSuchElementException();
        } else if (i >= size || i < 0) {
    		throw new IndexOutOfBoundsException();
    	}
        return queue[(front + i) % capacity()];
    }
    
    @Override
    public E next() {
        if (size == 0) {
        	throw new NoSuchElementException(); 
        }
        E val = queue[front];
        front = (front + 1) % capacity();
        size--;
        return val;
    }
    
    @Override
    public void update(int i, E value) {
    	if (!hasWork()) {
    		throw new NoSuchElementException();
    	} else if (i >= size || i < 0) {
    		throw new IndexOutOfBoundsException();
    	}
        queue[(front + i) % capacity()] = value;
    }
    
    @Override
    public int size() {
        return size;
    }
    
    @Override
    public void clear() {
        setup(capacity());
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        }
        else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        }
        else {
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;

            // Your code goes here

            throw new NotYetImplementedException();
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        throw new NotYetImplementedException();
    }
}
