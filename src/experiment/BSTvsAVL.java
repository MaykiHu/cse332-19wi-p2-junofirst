package experiment;

import cse332.datastructures.trees.BinarySearchTree;
import datastructures.dictionaries.AVLTree;

public class BSTvsAVL {
    public static final int NUM_TRIALS = 10;
    public static final int NUM_WARMUP = 5;
    
    // These two methods are for making the worst possible BST and AVL trees
    public static double worstAVL(int k) {
        AVLTree<Integer, Boolean> tree = new AVLTree<>();
        int currMax = 0;
        int currMin = 0;
        long time = System.nanoTime();
        for (int i = 0; i < k / 4; i++) {
            currMax += 2;
            currMin += 2;
            tree.insert(currMax, false);
            tree.insert(currMax - 1, false);
            tree.insert(currMin, false);
            tree.insert(currMin + 1, false);
        }
        return System.nanoTime() - time;
    }
    
    public static double worstBST(int k) {
        BinarySearchTree<Integer, Boolean> tree = new BinarySearchTree<>();
        long time = System.nanoTime();
        for (int i = 1; i <= k; i++) {
            tree.insert(i, false);
        }
        return System.nanoTime() - time;
    }
    
    // These two methods are for testing the worst case of the find methods for BST and AVL
    public static double testAVLFind(int k, int range) {
        AVLTree<Integer, Boolean> tree = new AVLTree<>();
        int currMax = 0;
        int currMin = 0;
        for (int i = 0; i < k / 4; i++) {
            currMax += 2;
            currMin += 2;
            tree.insert(currMax, false);
            tree.insert(currMax - 1, false);
            tree.insert(currMin, false);
            tree.insert(currMin + 1, false);
        }
        long time = System.nanoTime();
        for (int i = 0; i < k; i++) {
            tree.find((int) Math.random() * range);
        }
        return System.nanoTime() - time;
    }

    public static double testBSTFind(int k, int range) {
        BinarySearchTree<Integer, Boolean> tree = new BinarySearchTree<>();
        for (int i = 1; i <= k; i++) {
            tree.insert(i, false);
        }
        long time = System.nanoTime();
        for (int i = 0; i < k; i++) {
            tree.find((int) Math.random() * range);
        }
        return System.nanoTime() - time;
    }
    
    // These two methods are for finding the averages of the insert methods for BST and AVL
    public static double avgWorstBST(int n) {
        double total = 0;
        for(int i = 0; i < NUM_TRIALS; i++) {
            double time = worstBST(n);
            if (i >= NUM_WARMUP) {
                total += time;
            }
        }
        double avgRuntime = total / (double)(NUM_TRIALS - NUM_WARMUP);
        return avgRuntime / 1000000000; 
    }
    
    public static double avgWorstAVL(int n) {
        double total = 0;
        for(int i = 0; i < NUM_TRIALS; i++) {
            double time = worstAVL(n);
            if (i >= NUM_WARMUP) {
                total += time;
            }
        }
        double avgRuntime = total / (double)(NUM_TRIALS - NUM_WARMUP);
        return avgRuntime / 1000000000;  
    }
    
    // These two methods are for finding the averages of the find methods for BST and AVL
    public static double avgTestBSTFind(int n, int range) {
        double total = 0;
        for(int i = 0; i < NUM_TRIALS; i++) {
            double time = testBSTFind(n, range);
            if (i >= NUM_WARMUP) {
                total += time;
            }
        }
        double avgRuntime = total / (double)(NUM_TRIALS - NUM_WARMUP);
        return avgRuntime / 1000000000; 
    }
    
    public static double avgTestAVLFind(int n, int range) {
        double total = 0;
        for(int i = 0; i < NUM_TRIALS; i++) {
            double time = testAVLFind(n, range);
            if (i >= NUM_WARMUP) {
                total += time;
            }
        }
        double avgRuntime = total / (double)(NUM_TRIALS - NUM_WARMUP);
        return avgRuntime / 1000000000; 
    }
}
