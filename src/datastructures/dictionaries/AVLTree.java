package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;
import cse332.datastructures.trees.BinarySearchTree.BSTNode;

/**
 * TODO: Replace this comment with your own as appropriate.
 *
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 *
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 *    instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 *    children array or left and right fields in AVLNode.  This will 
 *    instead mask the super-class fields (i.e., the resulting node 
 *    would actually have multiple copies of the node fields, with 
 *    code accessing one pair or the other depending on the type of 
 *    the references used to access the instance).  Such masking will 
 *    lead to highly perplexing and erroneous behavior. Instead, 
 *    continue using the existing BSTNode children array.
 * 4. If this class has redundant methods, your score will be heavily
 *    penalized.
 * 5. Cast children array to AVLNode whenever necessary in your
 *    AVLTree. This will result a lot of casts, so we recommend you make
 *    private methods that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 */

public class AVLTree<K extends Comparable<K>, V> extends BinarySearchTree<K, V>  {
    private AVLNode root;
    
    public AVLTree() {
        super();
        this.root = null;
    }
    
    public class AVLNode extends BSTNode { // AVLNode is subclass of BSTNode
        private int height;
        
        public AVLNode(K key, V value) {
            super(key, value);
            height = 1;
        }         
    }
    
    @SuppressWarnings("unchecked")
    private AVLNode cast(BSTNode node) {
        return (AVLNode) node;
    }
    
    private int height(AVLNode node) { // Accessing height of node
        if (node == null) {
            return 0;
        }
        return node.height;
    }
    
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        V oldValue = find(key);
        root = insert(root, key, value); // Traverse through tree and insert
        return oldValue;
    }
    
    private AVLNode insert(AVLNode node, K key, V value) {
        if (node == null) {
            node = new AVLNode(key, value);
            this.size++;
        } else if (key.compareTo(node.key) < 0) { // Should be left tree
            AVLNode left = cast(node.children[0]);
            AVLNode right = cast(node.children[1]);
            node.children[0] = insert(left, key, value);
            if (height(left) - height(right) == 2) {
                if (key.compareTo(left.key) < 0) {
                    node = rotateWithLeft(node);
                } else {
                    node = doubleRotateWithLeft(node);
                }
            }
        } else if (key.compareTo(node.key) > 0) { // Should be right tree
            AVLNode left = cast(node.children[0]);
            AVLNode right = cast(node.children[1]);
            node.children[1] = insert(right, key, value);
            if (height(right) - height(left) == 2) {
                if (key.compareTo(right.key) > 0) {
                    node = rotateWithRight(node);
                } else {
                    node = doubleRotateWithRight(node);
                }
            }
        } else if (key.compareTo(node.key) == 0) {  // Same key, replace value
            node.value = value;
        }
        AVLNode left = cast(node.children[0]);
        AVLNode right = cast(node.children[1]);
        node.height = Math.max(height(left), height(right)) + 1; // Update height
        return node;
    }
    
    @SuppressWarnings("unchecked")
    public AVLNode rotateWithLeft(AVLNode problemNode) {
        AVLNode lChildProblem = (AVLTree<K, V>.AVLNode) problemNode.children[0];
        problemNode.children[0] = lChildProblem.children[1];
        lChildProblem.children[1] = problemNode;
        AVLNode problemNodeL = cast(problemNode.children[0]);
        AVLNode problemNodeR = cast(problemNode.children[1]);
        problemNode.height = Math.max(height(problemNodeL), height(problemNodeR)) + 1;
        AVLNode lChildProblemL = cast(lChildProblem.children[0]);
        lChildProblem.height = Math.max(height(lChildProblemL), problemNode.height) + 1;
        return lChildProblem;
    }
    
    @SuppressWarnings("unchecked")
    public AVLNode rotateWithRight(AVLNode problemNode) {
        AVLNode rChildProblem = (AVLTree<K, V>.AVLNode) problemNode.children[1];
        problemNode.children[1] = rChildProblem.children[0];
        rChildProblem.children[0] = problemNode;
        AVLNode problemNodeL = cast(problemNode.children[0]);
        AVLNode problemNodeR = cast(problemNode.children[1]);
        problemNode.height = Math.max(height(problemNodeL), height(problemNodeR)) + 1;
        AVLNode rChildProblemR = cast(rChildProblem.children[1]);
        rChildProblem.height = Math.max(height(rChildProblemR), problemNode.height) + 1;
        return rChildProblem;
    }
    
    public AVLNode doubleRotateWithLeft(AVLNode problemNode) {
        problemNode.children[0] = rotateWithRight(cast(problemNode.children[0]));
        return rotateWithLeft(problemNode);
    }
    
    public AVLNode doubleRotateWithRight(AVLNode problemNode) {
        problemNode.children[1] = rotateWithLeft(cast(problemNode.children[1]));
        return rotateWithRight(problemNode);
    }    
    
    public AVLNode find(K key, V value) {
        AVLNode prev = null;
        AVLNode current = this.root;

        int child = -1;

        while (current != null) {
            int direction = Integer.signum(key.compareTo(current.key));

            // We found the key!
            if (direction == 0) {
                return current;
            }
            else {
                // direction + 1 = {0, 2} -> {0, 1}
                child = Integer.signum(direction + 1);
                prev = current;
                current = cast(current.children[child]);
            }
        }

        // If value is not null, we need to actually add in the new value
        if (value != null) {
            current = new AVLNode(key, null);
            if (this.root == null) {
                this.root = current;
            }
            else {
                assert(child >= 0); // child should have been set in the loop
                                    // above
                prev.children[child] = current;
            }
            this.size++;
        }

        return current;
    }
}