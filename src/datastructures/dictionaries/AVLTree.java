package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;
import javafx.scene.Node;

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
    
    public void AVLTree(AVLNode root) {
        this.root = root;
    }
    
    public void doubleRotateWithRight(AVLNode root) {
        rotateWithLeft(root.right);
        rotateWithRight(root);
    }
    
    public void rotateWithRight(AVLNode root) {
        AVLNode temp = root.right;
        root.right = temp.left;
        temp.left = root;
        root.height = max(root.right.height(),
                          root.left.height()) + 1;
        temp.height = max(temp.right.height(),
                          temp.left.height()) + 1;
        root = temp;
    }    
    
    private class AVLNode {
        private int height;
        private AVLNode left;
        private AVLNode right;
        
        public void AVLNode() {
            this.height = 0;
        } 
        
        // Need another constructor for new nodes so that we can increment the height property
    }
}
