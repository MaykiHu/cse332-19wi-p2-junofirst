package p2.wordsuggestor;

import java.util.Comparator;
import java.util.function.Supplier;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.Dictionary;
import cse332.misc.LargeValueFirstItemComparator;
import cse332.sorts.InsertionSort;
import cse332.types.AlphabeticString;
import cse332.types.NGram;
import p2.sorts.TopKSort;

public class NGramToNextChoicesMap {
    private final Dictionary<NGram, Dictionary<AlphabeticString, Integer>> map;
    private final Supplier<Dictionary<AlphabeticString, Integer>> newInner;

    public NGramToNextChoicesMap(
            Supplier<Dictionary<NGram, Dictionary<AlphabeticString, Integer>>> newOuter,
            Supplier<Dictionary<AlphabeticString, Integer>> newInner) {
        this.map = newOuter.get();
        this.newInner = newInner;
    }

    /**
     * Increments the count of word after the particular NGram ngram.
     */
    public void seenWordAfterNGram(NGram ngram, String word) {
        AlphabeticString newWord = new AlphabeticString(word);
        if (map.find(ngram) == null) { // If ngram doesn't exist
            map.insert(ngram, newInner.get());
            map.find(ngram).insert(newWord, 1);
        } else {
            Integer wordCount = map.find(ngram).find(newWord); // Count of instances word is following ngram
            if (wordCount == null) { // If the word is not mapped to the ngram
                map.find(ngram).insert(newWord, 1);
            } else {
                map.find(ngram).insert(newWord, wordCount + 1);
            } 
        }
    }

    /**
     * Returns an array of the DataCounts for this particular ngram. Order is
     * not specified.
     *
     * @param ngram
     *            the ngram we want the counts for
     * 
     * @return An array of all the Items for the requested ngram.
     */
    @SuppressWarnings("unchecked")
    public Item<String, Integer>[] getCountsAfter(NGram ngram) {
        if (map.find(ngram) == null) {
            return (Item<String, Integer>[]) new Item[0];
        }
        Item<String, Integer>[] theArray = (Item<String, Integer>[]) new Item[map.find(ngram).size()];
        int index = 0;
        for (Item<AlphabeticString, Integer> item: map.find(ngram)) {
            String something = item.key.toString();
            theArray[index] = new Item<String, Integer>(something, item.value);
            index++;
        }
        return theArray; 
    }

    public String[] getWordsAfter(NGram ngram, int k) {
        Item<String, Integer>[] afterNGrams = getCountsAfter(ngram);

        Comparator<Item<String, Integer>> comp = new LargeValueFirstItemComparator<String, Integer>();
        if (k < 0) {
            InsertionSort.sort(afterNGrams, comp);
        }
        else {
            TopKSort.sort(afterNGrams, k, comp); // :) 
        }

        String[] nextWords = new String[k < 0 ? afterNGrams.length : k];
        for (int l = 0; l < afterNGrams.length && l < nextWords.length
                && afterNGrams[l] != null; l++) {
            nextWords[l] = afterNGrams[l].key;
        }
        return nextWords;
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
}
