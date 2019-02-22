package experiment;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import cse332.datastructures.trees.BinarySearchTree;
import cse332.interfaces.misc.Dictionary;
import cse332.types.AlphabeticString;
import datastructures.dictionaries.*;

public class DictionaryVsAll {
    private static final int NUM_TRIALS = 100;
    private static final int NUM_WARMUPS = 10;

    public static void main(String[] args) throws FileNotFoundException {
        File input = new File("alice.txt");
        System.out.println("Insert time for BST is " + testInsertTime(new BinarySearchTree<String, Integer>(), input));
        System.out.println("Insert time for AVL is " + testInsertTime(new AVLTree<String, Integer>(), input));
        System.out.println("Insert time for HashTrieMap insert time is " + testInsertTimeTrie(new HashTrieMap<Character, AlphabeticString, Integer>(AlphabeticString.class), input));
        System.out.println("Insert time for ChainingHashTable using MoveToFrontList is " + testInsertTime(new ChainingHashTable<String, Integer>(() -> new MoveToFrontList<>()), input));
        System.out.println("Insert time for ChainingHashTable using BST insert time is " + testInsertTime(new ChainingHashTable<String, Integer>(() -> new BinarySearchTree<>()), input));
        System.out.println("Insert time for ChainingHashTable using AVL insert time is " + testInsertTime(new ChainingHashTable<String, Integer>(() -> new AVLTree<>()), input));
    }
    
    public static void increaseCount(Dictionary<String, Integer> map, String key) {
        if (map.find(key) == null) {
            map.insert(key, 0);
        }
        map.insert(key, map.find(key) + 1);
    }
    
    public static double testInsertTime(Dictionary<String, Integer> map, File inputFile) throws FileNotFoundException {
        double totalTime = 0;
        double startTime;
        double endTime;
        for (int i = 0; i < NUM_TRIALS; i++) {
            startTime = System.nanoTime();
            @SuppressWarnings("resource")
            Scanner input = new Scanner(inputFile);
            while (input.hasNext()) {
                increaseCount(map, input.next());
            }
            endTime = System.nanoTime();
            if (NUM_WARMUPS <= i) {
                totalTime += (endTime - startTime);
            }
        }
        return totalTime / (NUM_TRIALS - NUM_WARMUPS);
    }
    
    public static double testInsertTimeTrie(Dictionary<AlphabeticString, Integer> map, File inputFile) throws FileNotFoundException {
        double totalTime = 0;
        double startTime;
        double endTime;
        for (int i = 0; i < NUM_TRIALS; i++) {
            startTime = System.nanoTime();
            Scanner input = new Scanner(inputFile);
            while (input.hasNext()) {
                AlphabeticString key = new AlphabeticString(input.next());
                Integer value = map.find(key);
                if (value == null) {
                    map.insert(key, 1);
                } else {
                    map.insert(key, value + 1);
                }   
            }
            endTime = System.nanoTime();
            if(NUM_WARMUPS <= i) {
                totalTime += (endTime - startTime);
            }
        }
        return totalTime / (NUM_TRIALS - NUM_WARMUPS);
    }
}