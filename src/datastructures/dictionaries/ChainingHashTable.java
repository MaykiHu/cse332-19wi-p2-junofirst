package datastructures.dictionaries;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

import cse332.datastructures.containers.*;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import datastructures.worklists.ArrayStack;

/**
 * TODO: Replace this comment with your own as appropriate.
 * 1. You must implement a generic chaining hashtable. You may not
 *    restrict the size of the input domain (i.e., it must accept 
 *    any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should rehash as appropriate (use load factor as
 *    shown in class!). 
 * 5. HashTable should be able to resize its capacity to prime numbers for more 
 *    than 200,000 elements. After more than 200,000 elements, it should 
 *    continue to resize using some other mechanism.
 * 6. We suggest you hard code some prime numbers. You can use this
 *    list: http://primes.utm.edu/lists/small/100000.txt 
 *    NOTE: Do NOT copy the whole list!
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;  
    private Dictionary<K, V>[] hashArray;
    private int capacity;
    @SuppressWarnings("rawtypes")
    private ArrayStack capacities;
    
    @SuppressWarnings("unchecked")
    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        initCapacities();
        capacity = (int) capacities.next(); // We know our capacities are stored as ints
        hashArray = (Dictionary<K, V> []) new Dictionary[capacity];
    }

    // Adds initial capacity sizes which are prime
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void initCapacities() {
        capacities = new ArrayStack();
        capacities.add(205759); // Each are divided by 2 and the nearest prime
        capacities.add(102877);
        capacities.add(51437);
        capacities.add(25717);
        capacities.add(12853);
        capacities.add(6421);
        capacities.add(3203);
        capacities.add(1597);
        capacities.add(797);
        capacities.add(397);
        capacities.add(197);
        capacities.add(97);
        capacities.add(47);
        capacities.add(23);
        capacities.add(11);
    }
    
    // Returns the index of key
    private int getArrIndex(K key) {
        int code = key.hashCode();
        return code % capacity;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public V insert(K key, V value) {
        int index = getArrIndex(key);
        V prevVal = find(key);
        if (hashArray[index] == null) {           // This will be a new chain (and item)
            hashArray[index] = newChain.get();    // Make a new chain
        } 
        if (hashArray[index].find(key) == null) { // This will be a new item in chain
            size++;
        }
        hashArray[index].insert(key, value);      // Update chain with item
        if (1.0 * size / capacity >= 0.75) {      // According to Java Doc, 0.75 is default load
            size = 0;
            if (capacities.size() == 0) {         // Update the capacity of table
                capacity *= 2;
            } else {
                capacity = (int) capacities.next();
            }
            Dictionary<K, V>[] oldHashArray = hashArray;
            hashArray = (Dictionary<K, V>[]) new Dictionary[capacity];
            for (Dictionary<K, V> chain : oldHashArray) {
                if (chain != null) {
                    Iterator<Item<K, V>> itr = chain.iterator();
                    while (itr.hasNext()) {
                        Item<K, V> chainItem = itr.next();
                        insert(chainItem.key, chainItem.value);
                    }
                }
            }
        }
        return prevVal;
    }

    @Override
    public V find(K key) {
        int index = getArrIndex(key);   // Index of where key is located
        Dictionary<K, V> chain = hashArray[index];   // Where key should be stored, get it
        if (chain != null) { 
            return chain.find(key);
        }
        return null; // There is no mapping for this key
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new HashTableIterator();
    }
    
    // Has to implement Iterator
    private class HashTableIterator implements Iterator<Item<K, V>> {
        private int currIndex;
        @SuppressWarnings("rawtypes")
        private ArrayStack chain;
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private void nextChain() {
            while (currIndex < hashArray.length && chain == null) {
                Dictionary<K, V> bucket = hashArray[currIndex];
                if (bucket != null && !bucket.isEmpty()) {
                    ArrayStack reverseChain = new ArrayStack();
                    chain = new ArrayStack();
                    for (Item<K, V> chainItem : bucket) {
                        reverseChain.add(chainItem);
                    }
                    while (reverseChain.hasWork()) {
                        chain.add(reverseChain.next());
                    }
                }
                currIndex++;
            }
        }
        
        public boolean hasNext() {
            nextChain();
            if (chain != null) {
                return chain.hasWork();
            }
            return false;
        }

        public Item<K, V> next() {
            if (hasNext()) {
                @SuppressWarnings("unchecked")
                Item<K, V> chainItem = (Item<K, V>) chain.next();
                if (!chain.hasWork()) {
                    chain = null;
                }
                return chainItem;
            }
            return null;
        }
    }
}
