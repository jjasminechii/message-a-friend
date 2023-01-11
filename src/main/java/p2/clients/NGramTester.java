package p2.clients;

import process.datastructures.trees.BinarySearchTree;
import process.interfaces.misc.Dictionary;
import process.types.AlphabeticString;
import process.types.BString;
import process.types.NGram;
import datastructures.dictionaries.AVLTree;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.HashTrieMap;
import p2.wordsuggestor.WordSuggestor;

import java.io.IOException;
import java.util.function.Supplier;

public class NGramTester {
    public static <A extends Comparable<A>, K extends BString<A>, V> Supplier<Dictionary<K, V>> trieConstructor(Class<K> clz) {
        return () -> new HashTrieMap<A, K, V>(clz);
    }

    public static <K, V> Supplier<Dictionary<K, V>> hashtableConstructor(
            Supplier<Dictionary<K, V>> constructor) {
        return () -> new ChainingHashTable<K, V>(constructor);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <K, V> Supplier<Dictionary<K, V>> binarySearchTreeConstructor() {
        return () -> new BinarySearchTree();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <K, V> Supplier<Dictionary<K, V>> avlTreeConstructor() {
        return () -> new AVLTree();
    }


    public static void main(String[] args) {
        try {
            WordSuggestor suggestions = new WordSuggestor("corpus/eggs.txt", 2, -1,
                    NGramTester.trieConstructor(NGram.class),
                    NGramTester.trieConstructor(AlphabeticString.class));
            System.out.println(suggestions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
