package datastructures.dictionaries;

import process.interfaces.trie.TrieSet;
import process.types.BString;

public class HashTrieSet<A extends Comparable<A>, E extends BString<A>> extends TrieSet<A, E> {
    /* Note: You should not be adding any methods to this class...you only need to implement
     *       the constructor!  */

    public HashTrieSet(Class<E> Type) {
        // Call the correct super constructor...that's it!
        super(new HashTrieMap<>(Type));
    }
}