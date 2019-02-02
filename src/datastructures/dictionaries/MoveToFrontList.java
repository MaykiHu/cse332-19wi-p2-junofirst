package datastructures.dictionaries;

import java.util.Iterator;
import java.util.NoSuchElementException;

import cse332.datastructures.containers.*;
import cse332.interfaces.misc.DeletelessDictionary;

/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find is called on an item, move it to the front of the 
 *    list. This means you remove the node from its current position 
 *    and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 *    elements to the front.  The iterator should return elements in
 *    the order they are stored in the list, starting with the first
 *    element in the list.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    private Node front;
    
    private class Node {
        private Node next;
        private Item<K, V> item;

        public Node(Item<K, V> item) {
            this.item = item;
            this.next = null;
        }
    }
    
    @Override
    public V insert(K key, V value) {
        V data = find(key); // Data at key
        if (data == null) { // Inserts at front since no key existed before
            Node newFront = new Node(new Item<K, V>(key, value));
            newFront.next = front;
            front = newFront;
            size++;
        } else { // Accesses and moves to front
            boolean keyFound = false;
            Node prev = null;
            Node curr = front;
            while (!keyFound) {
                if (curr.item.key.equals(key)) {
                    curr.item.value = value;
                    data = value;
                    if (prev != null) {
                        prev.next = curr.next;
                        curr.next = front;
                        front = curr;
                    }
                    keyFound = true;
                } else {
                    prev = curr;
                    curr = curr.next;
                }
            }
        }
        return data;
    }

    @Override
    public V find(K key) {
        V data = null;
        Node prev = null;
        Node curr = front;
        boolean keyFound = false;
        while (curr != null && !keyFound) {
            if (curr.item.key.equals(key)) { // If key found
                data = curr.item.value;
                keyFound = true;
                if (prev != null) {     // Link found item to front, only if not first item
                    prev.next = curr.next;
                    curr.next = front;
                    front = curr;
                }
            } else {
                prev = curr;            // Continue searching
                curr = curr.next;
            }
        }
        return data;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new ListIterator();
    }
    
    private class ListIterator implements Iterator<Item<K, V>> {
        private Node currNode;
        
        public ListIterator() {
            currNode = front;
        }
        
        public boolean hasNext() {
            return currNode != null;
        }
        
        public Item<K, V> next() {
            if (hasNext()) {
                Item<K, V> curr = currNode.item;
                currNode = currNode.next;
                return curr;
            }
            throw new NoSuchElementException();
        }
    }
}
