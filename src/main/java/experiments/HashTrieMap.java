package experiments;

import process.datastructures.containers.Item;
import process.interfaces.misc.SimpleIterator;
import process.interfaces.trie.TrieMap;
import process.types.BString;
import datastructures.dictionaries.ChainingHashTable;
import datastructures.dictionaries.MoveToFrontList;

import java.util.AbstractMap;
import java.util.Iterator;
import java.util.Map.Entry;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<datastructures.dictionaries.ChainingHashTable<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new ChainingHashTable<>(MoveToFrontList::new);
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieNode>> iterator() {
            return new HTMIterator();
        }
        private class HTMIterator extends SimpleIterator<Entry<A, HashTrieNode>>{
            Iterator<Item<A, HashTrieNode>> pointerIterator = pointers.iterator();
                @Override
                public boolean hasNext() {
                    return pointerIterator.hasNext();
                }

                @Override
                public Entry<A, HashTrieNode> next() {
                    Item<A, HashTrieNode> current = pointerIterator.next();
                    return new AbstractMap.SimpleEntry<>(current.key, current.value);
                }
            }
        }
        public HashTrieMap(Class<K> KClass) {
            super(KClass);
            this.root = new HashTrieNode();
        }

    @Override
    public V insert(K key, V value) {
        if(key == null || value == null){
            throw new IllegalArgumentException();
        }
        if(this.root == null){
            this.root = new HashTrieNode();
        }
        HashTrieNode current =  (HashTrieNode)this.root;
        for(A alphabet : key) {
            if (current != null) {
                if (current.pointers.find(alphabet) == null) {
                    current.pointers.insert(alphabet, new HashTrieNode());
                }
                current = current.pointers.find(alphabet);
            }
        }
        V returnValue = null;
        if(current != null) {
            returnValue = current.value;
            current.value = value;
            if (returnValue == null) {
                size++;
            }
        }
        return returnValue;
    }

    @Override
    public V find(K key) {
        if(key == null){
            throw new IllegalArgumentException();
        }
        if(this.root == null){
            return null;
        }
        HashTrieNode current = (HashTrieNode)this.root;
        for(A alphabet : key){
            current = current.pointers.find(alphabet);
            if(current == null){
                return null;
            }
        }
        return current.value;
    }

    @Override
    public boolean findPrefix(K key) {
        if(key == null){
            throw new IllegalArgumentException();
        }
        if(this.root == null){
            return false;
        }
        if(key.isEmpty() && this.root.value == null && this.root.pointers.toString().equals("{}")){
            return false;
        }
        HashTrieNode current = (HashTrieNode)this.root;
        for(A alphabet : key){
            current = current.pointers.find(alphabet);
            if(current == null){
                return false;
            }
        }
        return true;
    }

    @Override
    public void delete(K key) {
        throw new UnsupportedOperationException();
//        if(key == null){
//            throw new IllegalArgumentException();
//        }
//        if(find(key) == null){
//            return;
//        }
//        HashTrieNode deletePoint = (HashTrieNode)this.root;
//        HashTrieNode current = (HashTrieNode)this.root;
//        HashTrieNode end = (HashTrieNode)this.root;
//
//        if(key.isEmpty()){
//            if(this.root.value != null){
//                this.root.value = null;
//            }
//        }
//        //Get the end node and the last possible node that
//        //can't be deleted because either its value isn't null (symbolizing
//        //it's a word) or because the node has more than one-children (showing another
//        //word depends on the node so it's unable to be deleted)
//        for(A alphabet : key){
//            if(end.value != null || end.pointers.size() > 1){
//                deletePoint = end;
//            }
//            end = end.pointers.find(alphabet);
//        }
//
//        //If the word still has children then we just set the value to null
//        if(end.pointers.size() > 0){
//            size--;
//            end.value = null;
//        }
//        else {
//            //re iterate through the key alphabet to find the delete point
//            //and remove the key value
//            for (A alphabet : key) {
//                if (current == deletePoint) {
//                    current.pointers.delete(alphabet);
//                    //Once it returns the nodes should be garbage collected? and therefore
//                    //should be deleted
//                    size--;
//                    return;
//                }
//                current = current.pointers.find(alphabet);
//            }
//        }
    }

    @Override
    public void clear() {
//        this.root = new HashTrieNode();
//        size = 0;
        throw new UnsupportedOperationException();
    }
}