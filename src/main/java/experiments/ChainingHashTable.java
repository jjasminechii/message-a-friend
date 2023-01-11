package experiments;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.misc.SimpleIterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * 1. You must implement a generic chaining hashtable. You may not
 * restrict the size of the input domain (i.e., it must accept
 * any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should rehash as appropriate (use load factor as
 * shown in class!).
 * 5. HashTable should be able to resize its capacity to prime numbers for more
 * than 200,000 elements. After more than 200,000 elements, it should
 * continue to resize using some other mechanism.
 * 6. We suggest you hard code some prime numbers. You can use this
 * list: http://primes.utm.edu/lists/small/100000.txt
 * NOTE: Do NOT copy the whole list!
 * 7. When implementing your iterator, you should NOT copy every item to another
 * dictionary/list and return that dictionary/list's iterator.
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;
    private Dictionary<K, V>[] hashing;
    private int[] primeNumbers = {2393, 9137, 13309, 21187, 29573, 33589, 66463, 86069, 122131, 170741};
    private int currentPrime;
    private double LOAD_FACTOR;
    private int startingSize = 131;

    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        this.hashing = new Dictionary[startingSize];
        this.currentPrime = 0;
    }

    private void rehash() {
        Dictionary<K, V>[] oldDictionary;
        if (currentPrime < primeNumbers.length - 1) {
            currentPrime++;
            oldDictionary = new Dictionary[primeNumbers[currentPrime]];
        } else {
            oldDictionary = new Dictionary[2 * hashing.length];
        }
        for (Dictionary<K, V> current : hashing) {
            if (current != null) {
                for (Item<K, V> currentItem : current) {
                    int index = Math.abs(currentItem.key.hashCode() % oldDictionary.length);
                    if (oldDictionary[index] == null) {
                        oldDictionary[index] = newChain.get();
                    }
                    oldDictionary[index].insert(currentItem.key, currentItem.value);
                }
            }
        }
        hashing = oldDictionary;
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        LOAD_FACTOR = (double) size / hashing.length;
        if (LOAD_FACTOR >= 1) {
            rehash();
        }
        V current = find(key);
        if (current == null) {
            size++;
        }
        int index = Math.abs(key.hashCode() % hashing.length);
        if (index < 0) {
            return null;
        } else {
            if (hashing[index] == null) {
                hashing[index] = newChain.get();
            }
            hashing[index].insert(key, value);
            return current;
        }
    }

    @Override
    // good
    public V find(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int current = Math.abs(key.hashCode() % hashing.length);
        if (hashing[current] == null) {
            return null;
        }
        return hashing[current].find(key);
    }

    // good
    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MyHashIterator();
    }

    private class MyHashIterator extends SimpleIterator<Item<K, V>> {
        private int tracking; // counting
        private Iterator<Item<K, V>> currentIterator;

        @Override
        public Item<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else {
                return currentIterator.next();
            }
        }

        @Override
        public boolean hasNext() {
            for (int i = tracking; i < hashing.length; i++) {
                if (tracking == hashing.length - 1) {
                    return false;
                }
                if (currentIterator != null) {
                    if (currentIterator.hasNext()) {
                        return true;
                    }
                }
                tracking++;
                if (hashing[tracking] == null) {
                    currentIterator = null;
                } else {
                    currentIterator = hashing[tracking].iterator();
                }
            }
            return false;
        }
    }


    /**
     * Temporary fix so that you can debug on IntelliJ properly despite a broken iterator
     * Remove to see proper String representation (inherited from Dictionary)
     */
    @Override
    public String toString() {
        return "ChainingHashTable String representation goes here.";
    }

    //    for testing
//    public static void main(String[] args) {
//        ChainingHashTable<Integer, Integer> debug = new ChainingHashTable<>(MoveToFrontList::new);
//        for(int i = 0; i < 100; i++) {
//            debug.insert(i, i + 1);
//        }
//
//        Iterator<Item<Integer, Integer>> current = debug.iterator();
//        while(current.hasNext()) {
//            Item element = current.next();
//            System.out.println(element + " ");
//        }
//    }
}

