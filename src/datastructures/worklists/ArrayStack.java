package datastructures.worklists;

import java.util.NoSuchElementException;

import cse332.interfaces.worklists.LIFOWorkList;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
	private int capacity;
	private E[] stack;
	private int front;

    @SuppressWarnings("unchecked")
	public ArrayStack() {
    	setup();
    }
    
    @SuppressWarnings("unchecked")
	private void setup() {
    	capacity = 10;
        stack = (E[])new Object[capacity];
        front = 0;
    }

    @Override
    public void add(E work) {
    	stack[front] = work;
    	front++;
        if(front == capacity) {
        	increaseCap();
        }
    }
    
    private void increaseCap() {
    	capacity *= 2;
    	@SuppressWarnings("unchecked")
		E[] newStack = (E[])new Object[capacity];
    	for (int i = 0; i < stack.length; i++) {
    		newStack[i] = stack[i];
    	}
    	stack = newStack;
    }

    @Override
    public E peek() {
    	if (!hasWork()) {
    		throw new NoSuchElementException();
    	}
        return stack[front - 1];
    }

    @Override
    public E next() {
        if (!hasWork()) {
        	throw new NoSuchElementException();
        }
        E value = stack[front - 1];
        stack[front - 1] = null;
        front--;
        return value;
    }

    @Override
    public int size() {
        return front;
    }

    @Override
    public void clear() {
    	setup();
    }
}