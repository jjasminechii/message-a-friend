package experiments;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GeneralPurposeDictionary {
    private static final int NUM_TESTS = 20;
    private static final int NUM_WARMUP = 5;
    private static boolean WARMUP_RUN = true;
    public static final File FILE_TO_READ = new File("./src/main/java/experiments/alice.txt");

    public static void main(String[] args) throws FileNotFoundException {
        long BSTTime = 0;
        long AVLTime = 0;
        long hashTableTime = 0;
        long trieMapTime = 0;
        BinarySearchTree<String, Integer> BST = new BinarySearchTree<>();
        AVLTree<String, Integer> AVL = new AVLTree<>();
        ChainingHashTable<String, Integer> hashTable = new ChainingHashTable<>(MoveToFrontList::new);
        HashTrieMap<Character, AlphabeticString, Integer> trieMap = new HashTrieMap<>(AlphabeticString.class);
        for (int i = 0; i < NUM_TESTS; i++) {
            if (NUM_WARMUP == i) {
                WARMUP_RUN = false;
            }
            BST = new BinarySearchTree<>();
            AVL = new AVLTree<>();
            hashTable = new ChainingHashTable<>(MoveToFrontList::new);
            trieMap = new HashTrieMap<>(AlphabeticString.class);
            Scanner sc = new Scanner(FILE_TO_READ);

            long startTime;
            long endTime;

            while (sc.hasNext()) {
                String word = sc.next();

                //BST
                Integer exists = BST.find(word);
                startTime = System.nanoTime();
                if (exists == null) {
                    BST.insert(word, 1);
                } else {
                    BST.insert(word, exists + 1);
                }
                endTime = System.nanoTime();
                if (!WARMUP_RUN) {
                    BSTTime += (endTime - startTime);
                }

                //AVL
                exists = AVL.find(word);
                startTime = System.nanoTime();
                if (exists == null) {
                    AVL.insert(word, 1);
                } else {
                    AVL.insert(word, exists + 1);
                }
                endTime = System.nanoTime();
                if (!WARMUP_RUN) {
                    AVLTime += (endTime - startTime);
                }

                //HashTable
                exists = hashTable.find(word);
                startTime = System.nanoTime();
                if (exists == null) {
                    hashTable.insert(word, 1);
                } else {
                    hashTable.insert(word, exists + 1);
                }
                endTime = System.nanoTime();
                if (!WARMUP_RUN) {
                    hashTableTime += (endTime - startTime);
                }

                //Trie
                AlphabeticString alphabeticWord = new AlphabeticString(word);
                exists = trieMap.find(alphabeticWord);
                startTime = System.nanoTime();
                if (exists == null) {
                    trieMap.insert(alphabeticWord, 1);
                } else {
                    trieMap.insert(alphabeticWord, exists + 1);
                }
                endTime = System.nanoTime();
                if (!WARMUP_RUN) {
                    trieMapTime += (endTime - startTime);
                }
            }
        }
        double divisor = (NUM_TESTS - NUM_WARMUP) * Math.pow(10, 6);
        System.out.println("Runtime for BST insert is " + BSTTime / divisor + " ms");
        System.out.println("Runtime for AVL insert is " + AVLTime / divisor + " ms");
        System.out.println("Runtime for HashTable insert is " + hashTableTime / divisor + " ms");
        System.out.println("Runtime for HashTrieMap insert is " + trieMapTime / divisor + " ms");
        System.out.println();

        WARMUP_RUN = true;
        BSTTime = 0;
        AVLTime = 0;
        hashTableTime = 0;
        trieMapTime = 0;

        for (int i = 0; i < NUM_TESTS; i++) {
            if (NUM_WARMUP == i) {
                WARMUP_RUN = false;
            }
            Scanner sc = new Scanner(FILE_TO_READ);

            long startTime;
            long endTime;

            while (sc.hasNext()) {
                String word = sc.next();

                //BST
                startTime = System.nanoTime();
                BST.find(word);
                endTime = System.nanoTime();
                if (!WARMUP_RUN) {
                    BSTTime += (endTime - startTime);
                }

                //AVL
                startTime = System.nanoTime();
                AVL.find(word);
                endTime = System.nanoTime();
                if (!WARMUP_RUN) {
                    AVLTime += (endTime - startTime);
                }

                //HashTable
                startTime = System.nanoTime();
                hashTable.find(word);
                endTime = System.nanoTime();
                if (!WARMUP_RUN) {
                    hashTableTime += (endTime - startTime);
                }

                //Trie
                AlphabeticString alphabeticWord = new AlphabeticString(word);
                startTime = System.nanoTime();
                trieMap.find(alphabeticWord);
                endTime = System.nanoTime();
                if (!WARMUP_RUN) {
                    trieMapTime += (endTime - startTime);
                }
            }
        }
        System.out.println("Runtime for BST find is " + BSTTime / divisor + " ms");
        System.out.println("Runtime for AVL find is " + AVLTime / divisor + " ms");
        System.out.println("Runtime for HashTable find is " + hashTableTime / divisor + " ms");
        System.out.println("Runtime for HashTrieMap find is " + trieMapTime / divisor + " ms");
    }
}
