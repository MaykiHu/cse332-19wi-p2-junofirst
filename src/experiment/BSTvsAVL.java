package experiment;

import cse332.datastructures.trees.BinarySearchTree;
import datastructures.dictionaries.AVLTree;

public class BSTvsAVL {
    public static final int NUM_TRIALS = 10;
    public static final int NUM_WARMUP = 5;
    
    public static void main(String[] args) {
        System.out.println(avgTestAVLFind(1000, (int) Math.pow(2.0, 14.0)));
        System.out.println(avgTestAVLFind(1000, (int) Math.pow(2.0, 15.0)));
        System.out.println(avgTestAVLFind(1000, (int) Math.pow(2.0, 16.0)));
        System.out.println(avgTestAVLFind(1000, (int) Math.pow(2.0, 17.0)));
        System.out.println(avgTestAVLFind(1000, (int) Math.pow(2.0, 18.0)));
        System.out.println(avgTestAVLFind(1000, (int) Math.pow(2.0, 19.0)));
        System.out.println(avgTestAVLFind(1000, (int) Math.pow(2.0, 20.0)));
        System.out.println(avgTestAVLFind(1000, (int) Math.pow(2.0, 21.0)));
        
        System.out.println();
        
        System.out.println(avgTestBSTFind(1000, 10000));
        System.out.println(avgTestBSTFind(1000, 20000));
        System.out.println(avgTestBSTFind(1000, 30000));
        System.out.println(avgTestBSTFind(1000, 40000));
        System.out.println(avgTestBSTFind(1000, 50000));
        System.out.println(avgTestBSTFind(1000, 60000));
        System.out.println(avgTestBSTFind(1000, 70000));
        System.out.println(avgTestBSTFind(1000, 80000));
    }
    
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
            //Find the worst case number
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
            //Find the worst case number
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
