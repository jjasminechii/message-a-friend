package experiments;

import java.util.ArrayList;

public class BestChain {
    private static final int NUM_TESTS = 20;
    private static final int NUM_WARMUP = 5;
    private static final int NUM_ELEMENTS = 100000;
    private static boolean WARMUP_RUNS = true;

    public static void main(String[] args) {
        double MFTInsertTime = 0;
        double BSTInsertTime = 0;
        double AVLInsertTime = 0;

        double MFTFindTime = 0;
        double BSTFindTime = 0;
        double AVLFindTime = 0;

        for (int i = 0; i < NUM_TESTS; i++) {
            if(i == NUM_WARMUP){
                WARMUP_RUNS = false;
            }
            ChainingHashTable<Integer, Integer> MTFHash = new ChainingHashTable<>(MoveToFrontList::new);
            ChainingHashTable<Integer, Integer> AVLHash = new ChainingHashTable<>(AVLTree::new);
            ChainingHashTable<Integer, Integer> BSTHash = new ChainingHashTable<>(BinarySearchTree::new);

            ArrayList<Integer> numsToAdd = new ArrayList<Integer>();
            for (int n = 0; n < NUM_ELEMENTS; n++) {
                int rand = (int)(Math.random() * NUM_ELEMENTS);
                numsToAdd.add(rand);
            }
            MFTInsertTime = insertToHash(numsToAdd, MTFHash);
            AVLInsertTime = insertToHash(numsToAdd, AVLHash);
            BSTInsertTime = insertToHash(numsToAdd, BSTHash);
            MFTFindTime = findInHash(numsToAdd, MTFHash);
            AVLFindTime = findInHash(numsToAdd, AVLHash);
            BSTFindTime = findInHash(numsToAdd, BSTHash);
        }

        double divisor = (NUM_TESTS - NUM_WARMUP) * NUM_ELEMENTS;
        System.out.println("The average time to insert element in MFT of size " + NUM_ELEMENTS + ": " + (MFTInsertTime / divisor ) + " ns");
        System.out.println("The average time to insert element in BST of size " + NUM_ELEMENTS + ": " + (BSTInsertTime / divisor ) + " ns");
        System.out.println("The average time to insert element in AVL of size " + NUM_ELEMENTS + ": " + (AVLInsertTime / divisor ) + " ns");
        System.out.println();
        System.out.println("The average time to find element in MFT of size " + NUM_ELEMENTS + ": " + (MFTFindTime / divisor ) + " ns");
        System.out.println("The average time to find element in BST of size " + NUM_ELEMENTS + ": " + (BSTFindTime / divisor ) + " ns");
        System.out.println("The average time to find element in AVL of size " + NUM_ELEMENTS + ": " + (AVLFindTime / divisor ) + " ns");
    }

    private static double insertToHash(ArrayList<Integer> elements, ChainingHashTable<Integer, Integer> hash){
        long startTime;
        long endTime;
        double totalTime = 0;
        startTime = System.nanoTime();
        for (int i = 0; i < elements.size(); i++) {
            hash.insert(elements.get(i), i);
        }
        endTime = System.nanoTime();
        totalTime += (endTime - startTime);
        if(WARMUP_RUNS){
            return 0;
        }
        return totalTime;
    }

    private static double findInHash(ArrayList<Integer> elements, ChainingHashTable<Integer, Integer> hash){
        long startTime;
        long endTime;
        double totalTime = 0;
        startTime = System.nanoTime();
        for (int i = 0; i < elements.size(); i++) {
            hash.find(elements.get(i));
        }
        endTime = System.nanoTime();
        totalTime += (endTime - startTime);
        if(WARMUP_RUNS){
            return 0;
        }
        return totalTime;
    }
}