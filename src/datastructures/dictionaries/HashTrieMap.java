package datastructures.dictionaries;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map.Entry;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.BString;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.trie.TrieMap;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<DeletelessDictionary<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new ChainingHashTable<A, HashTrieNode>(() -> new MoveToFrontList<>());
            this.value = value;     
        }

        @Override
        public Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> iterator() {
            return new HashTrieMapIterator();
        }
        
        private class HashTrieMapIterator implements Iterator<Entry<A, HashTrieMap<A, K, V>.HashTrieNode>> {
            Iterator<Item<A, HashTrieMap<A, K, V>.HashTrieNode>> tableItr = pointers.iterator();
            @Override
            public boolean hasNext() {
                return tableItr.hasNext();
            }

            @Override
            public Entry<A, HashTrieMap<A, K, V>.HashTrieNode> next() {
                if (tableItr.hasNext()) {
                    Item<A, HashTrieNode> node = tableItr.next();
                    return new AbstractMap.SimpleEntry<A, HashTrieNode>(node.key, node.value);
                }
                return null;
            }
            
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
    }

    @SuppressWarnings("unchecked")
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        V prevVal = null;
        if (key.isEmpty()) {
            if (root.value == null) {
                size++;
                root.value = value;
                return prevVal;
            }
            prevVal = root.value;
            root.value = value;
            return prevVal;
        }
        ChainingHashTable<A, HashTrieNode> children = (ChainingHashTable<A, HashTrieMap<A, K, V>.HashTrieNode>) 
                                            root.pointers;
        Iterator<A> itr = key.iterator(); // Should check if instance of
        while(itr.hasNext()) {
            A currChar = itr.next();
            if (children.find(currChar) == null) {
                children.insert(currChar, new HashTrieNode());
            } else {
                prevVal = children.find(currChar).value;
            }
            if (!itr.hasNext()) {
                if (children.find(currChar).value == null) {
                    size++;
                    prevVal = null;
                } else {
                    prevVal = children.find(currChar).value;
                }
                children.find(currChar).value = value;
            }
            children = (ChainingHashTable<A, HashTrieNode>)
                        children.find(currChar).pointers;
        }
        return prevVal;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        V val = null;
        if (key.isEmpty() && root.value != null) {
            return root.value;
        }
        ChainingHashTable<A, HashTrieNode> children = (ChainingHashTable<A, HashTrieMap<A, K, V>.HashTrieNode>)
                                            root.pointers;
        Iterator<A> itr = key.iterator(); // Should check if instance of
        boolean hasPath = true;
        while(itr.hasNext() && hasPath) {
            A currChar = itr.next();
            if (children.find(currChar) != null) {
                val = children.find(currChar).value;
                children = (ChainingHashTable<A, HashTrieMap<A, K, V>.HashTrieNode>) 
                        children.find(currChar).pointers;
            } else {
                hasPath = false;
                val = null;
            }
        }
        return val;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean findPrefix(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        
        if (key.isEmpty()) {
            return true;
        }
        
        ChainingHashTable<A, HashTrieNode> children = (ChainingHashTable<A, HashTrieMap<A, K, V>.HashTrieNode>) 
                                            root.pointers;
        Iterator<A> itr = key.iterator();
        while (itr.hasNext()) {
            A currChar = itr.next();
            if (children.find(currChar) != null) {
                children = (ChainingHashTable<A, HashTrieMap<A, K, V>.HashTrieNode>) 
                children.find(currChar).pointers;
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public void delete(K key) {
        throw new UnsupportedOperationException(
                "DeletelessDictionary does not support deletion");
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(
                "DeletelessDictionary does not support clearing");
    }
}