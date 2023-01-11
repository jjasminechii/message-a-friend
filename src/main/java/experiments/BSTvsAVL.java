package experiments;

public class BSTvsAVL{

    private static final int NUM_TESTS = 100;
    private static final int NUM_WARMUP = 5;
    private static final int NUM_ELEMENTS = 1000;
    private static boolean WARMUP_RUNS = true;

    public static void main(String args[]) {
        double totalAVLInsertTime = 0;
        double totalBSTInsertTime = 0;
        double totalAVLFindTime = 0;
        double totalBSTFindTime = 0;
        for(int i = 0; i < NUM_TESTS; i++){
            if(i == NUM_WARMUP){
                WARMUP_RUNS = false;
            }
            BinarySearchTree BST = new BinarySearchTree();
            AVLTree AVL = new AVLTree();

            totalAVLInsertTime += insertAVL(AVL);
            totalBSTInsertTime += insertBST(BST);
            totalAVLFindTime += findAVL(AVL);
            totalBSTFindTime += findBST(BST);
        }
        double divisor = (NUM_TESTS - NUM_WARMUP) * NUM_ELEMENTS;
        System.out.println("The average time to insert element in AVL of size " + NUM_ELEMENTS + ": " + (totalAVLInsertTime / divisor ) + " ns");
        System.out.println("The average time to insert element in BST of size " + NUM_ELEMENTS + ": " + (totalBSTInsertTime / divisor ) + " ns");
        System.out.println();
        System.out.println("The average time to find element in AVL of size " + NUM_ELEMENTS + ": " + (totalAVLFindTime / divisor ) + " ns");
        System.out.println("The average time to find element in BST of size " + NUM_ELEMENTS + ": " + (totalBSTFindTime / divisor ) + " ns");
    }

    private static double insertAVL(AVLTree AVL){
        long totalTime = 0;
        long startTime = System.nanoTime();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            AVL.insert(i, i);
        }
        long endTime = System.nanoTime();

        totalTime += (endTime - startTime);
        if(WARMUP_RUNS){
            return 0;
        }
        return totalTime;
    }

    private static double insertBST(BinarySearchTree BST){
        long totalTime = 0;
        long startTime = System.nanoTime();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            BST.insert(i, i);
        }
        long endTime = System.nanoTime();

        totalTime += (endTime - startTime);
        if(WARMUP_RUNS){
            return 0;
        }
        return totalTime;
    }

    private static double findAVL(AVLTree AVL){
        long totalTime = 0;
        long startTime = System.nanoTime();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            AVL.find(i);
        }
        long endTime = System.nanoTime();

        totalTime += (endTime - startTime);
        if(WARMUP_RUNS){
            return 0;
        }
        return totalTime;
    }

    private static double findBST(BinarySearchTree BST){
        long totalTime = 0;
        long startTime = System.nanoTime();
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            BST.find(i);
        }
        long endTime = System.nanoTime();

        totalTime += (endTime - startTime);
        if(WARMUP_RUNS){
            return 0;
        }
        return totalTime;
    }
}
