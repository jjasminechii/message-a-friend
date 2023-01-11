package process.interfaces.trie;

import process.interfaces.misc.Set;
import process.types.BString;

public abstract class TrieSet<A extends Comparable<A>, E extends BString<A>> extends Set<E> {
    protected TrieSet(TrieMap<A, E, Boolean> backingMap) {
        super(backingMap);
    }

    protected TrieSet() {
        super();
    }

    @SuppressWarnings("unchecked")
    public final boolean containsPrefix(E e) {
        return ((TrieMap<A, E, Boolean>) this.map).findPrefix(e);
    }
}
