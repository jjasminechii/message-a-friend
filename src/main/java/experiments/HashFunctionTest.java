package experiments;


import java.util.ArrayList;
import java.util.List;

public class HashFunctionTest {

    private static final int NUM_TESTS = 20;
    private static final int NUM_WARMUP = 5;
    private static final int NUM_ELEMENTS = 100;

    public static void main (String[] args){
        List<AlphabeticString> words = randomFiveWordGenerator();
        double totalTime = 0;
        for (int i = 0; i < NUM_TESTS; i++){
            ChainingHashTable<AlphabeticString, Integer> MTFHash = new ChainingHashTable<>(MoveToFrontList::new);
            long startTime = System.nanoTime();
            for (int j = 0; j < words.size(); j++){
                MTFHash.insert(words.get(j), 0);
            }
            long endTime = System.nanoTime();
            if (i >= NUM_WARMUP){
                totalTime += endTime - startTime;
            }
        }
        double averageRunTime = totalTime / (NUM_TESTS - NUM_WARMUP) / NUM_ELEMENTS;
        System.out.println("The average run time is " + averageRunTime + " ns");
    }

    private static List<AlphabeticString> randomFiveWordGenerator (){
        String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
        List<AlphabeticString> words = new ArrayList<>();
        String temp = "";
        for (int i = 0; i < NUM_ELEMENTS; i++) {
            for (int j = 0; j < 5; j++) {
                int rand = (int) (Math.random() * 26);
                temp = temp + ALPHABET.charAt(rand);
            }
            words.add(new AlphabeticString(temp));
        }
        return words;
    }
}
