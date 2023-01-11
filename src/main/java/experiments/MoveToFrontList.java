package experiments;

import cse332.datastructures.containers.Item;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;

import java.util.Iterator;

/**
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find or insert is called on an existing key, move it
 * to the front of the list. This means you remove the node from its
 * current position and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 * elements to the front.  The iterator should return elements in
 * the order they are stored in the list, starting with the first
 * element in the list. When implementing your iterator, you should
 * NOT copy every item to another dictionary/list and return that
 * dictionary/list's iterator.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {

    private class Node extends Item<K,V>{

//        private Item<K,V> data;
        private Node next;

//        private Node(){
//            data = null;
//            next = null;
//        }
        private Node(K keyVal, Node next){
//            data = new Item<K,V>(keyVal, val);
            this(keyVal,null,next);
        }

        private Node(K keyVal, V val, Node next){
            super(keyVal,val);
//            data = new Item<K,V>(keyVal, val);
            this.next = next;
        }
    }

    private Node head;

    public MoveToFrontList(){
        head = null;
    }

    @Override
    public V insert(K key, V value) {
        if(key == null || value == null){
            throw new IllegalArgumentException();
        }
//        if(head == null){
//            head = new Node(key, value);
//            return null;
//        }
        V previousValue = find(key);
        if(previousValue == null){
            Node temp = head;
            head = new Node(key, temp);
            this.size++;
        }
        head.value = value;
        return previousValue;
    }

    @Override
    public V find(K key) {
        if(key == null){
            throw new IllegalArgumentException();
        }
        Node current = head;
//        if(head.data != null && head.data.key.equals(key)){
//            return head.data.value;
//        }
        V returnValue = null;
        while(current != null) {
            if(current.key.equals(key)){
                return current.value;
            }
            if(current.next != null && current.next.key.equals(key)){
                returnValue = current.next.value;
                Node temp = current.next;
                current.next = current.next.next;
                temp.next = head;
                head = temp;
//                head = new Node(key, current.next.data.value, head);
//                current.next = current.next.next;
//                return head.data.value;
            }
            current = current.next;
        }
        return returnValue;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MTFIterator();
    }

    private class MTFIterator extends SimpleIterator<Item<K,V>> {
        private Node current;

        public MTFIterator(){
            current = head;
        }

        public boolean hasNext(){
            return current != null;
        }

        public Item<K,V> next(){
            Item<K,V> returnItem = new Item<K,V>(current);
            current = current.next;
            return returnItem;
        }

    }
}
