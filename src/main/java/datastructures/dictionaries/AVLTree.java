package datastructures.dictionaries;

import cse332.datastructures.trees.BinarySearchTree;
/**
 * AVLTree must be a subclass of BinarySearchTree<E> and must use
 * inheritance and calls to superclass methods to avoid unnecessary
 * duplication or copying of functionality.
 * <p>
 * 1. Create a subclass of BSTNode, perhaps named AVLNode.
 * 2. Override the insert method such that it creates AVLNode instances
 * instead of BSTNode instances.
 * 3. Do NOT "replace" the children array in BSTNode with a new
 * children array or left and right fields in AVLNode.  This will
 * instead mask the super-class fields (i.e., the resulting node
 * would actually have multiple copies of the node fields, with
 * code accessing one pair or the other depending on the type of
 * the references used to access the instance).  Such masking will
 * lead to highly perplexing and erroneous behavior. Instead,
 * continue using the existing BSTNode children array.
 * 4. Ensure that the class does not have redundant methods
 * 5. Cast a BSTNode to an AVLNode whenever necessary in your AVLTree.
 * This will result a lot of casts, so we recommend you make private methods
 * that encapsulate those casts.
 * 6. Do NOT override the toString method. It is used for grading.
 * 7. The internal structure of your AVLTree (from this.root to the leaves) must be correct
 */

public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V> {

    public class AVLNode extends BSTNode {
        public int height;
        public AVLNode(K key, V value) {
            super(key, value);
            height = 0;
        }
    }

    public V insert(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        AVLNode current = find(key, value, (AVLNode)root, null, -1);
        V oldValue = current.value;
        current.value = value;
        return oldValue;
    }

    protected AVLNode find(K key, V value, AVLNode current, AVLNode prev, int direction) {
        AVLNode returnNode;
        if (value == null){
            return null;
        }
        if(current == null){
            current = new AVLNode(key, null);
            if (this.root == null) {
                this.root = current;
            }
            else{
                prev.children[direction] = current;
            }
            this.size++;
            return current;
        }

        if(key.compareTo(current.key) == 0){
            return current;
        }
        else if(key.compareTo(current.key) < 1){
            returnNode = find(key, value, (AVLNode)current.children[0], current,0);
        }
        else{
            returnNode = find(key, value, (AVLNode)current.children[1], current,1);
        }

        //update height
        current.height = Math.max(getHeight((AVLNode)current.children[0]), getHeight((AVLNode)current.children[1])) + 1;

        //check for imbalances
        AVLNode temp = checkBalance(current);
        if(temp != null){
            if(prev == null){
                root = temp;
            }
            else{
                prev.children[direction] = temp;
            }
        }
        return returnNode;
    }

    private AVLNode checkBalance(AVLNode current) {
        if (current == null) {
            return null;
        }
        if (getHeight((AVLNode) current.children[0]) >= 2 + getHeight((AVLNode) current.children[1])) {
            if (getHeight((AVLNode) current.children[0].children[0]) > getHeight((AVLNode) current.children[0].children[1])) {
                return RotateRight(current);
            } else {
                return RotateLeftDouble(current);
            }
        } else if (getHeight((AVLNode) current.children[1]) >= 2 + getHeight((AVLNode) current.children[0])) {
            if (getHeight((AVLNode) current.children[1].children[1]) > getHeight((AVLNode) current.children[1].children[0])) {
                return RotateLeft(current);
            } else {
                return RotateRightDouble(current);
            }
        }
        return null;
    }

    //Left
    private AVLNode RotateLeft(AVLNode node){
        AVLNode temp = (AVLNode)node.children[1];
        node.children[1] = temp.children[0];
        temp.children[0] = node;
        node.height = Math.max(getHeight((AVLNode)node.children[0]), getHeight((AVLNode)node.children[1])) + 1;
        temp.height = Math.max(getHeight((AVLNode)temp.children[0]), getHeight((AVLNode)temp.children[1])) + 1;
        return temp;
    }

    //Right
    private AVLNode RotateRight(AVLNode node){
        AVLNode temp = (AVLNode)node.children[0];
        node.children[0] = temp.children[1];
        temp.children[1] = node;
        node.height = Math.max(getHeight((AVLNode)node.children[0]), getHeight((AVLNode)node.children[1])) + 1;
        temp.height = Math.max(getHeight((AVLNode)temp.children[0]), getHeight((AVLNode)temp.children[1])) + 1;
        return temp;
    }

    //LR
    private AVLNode RotateLeftDouble(AVLNode node){
        node.children[0] = RotateLeft((AVLNode)node.children[0]);
        node = RotateRight(node);
        return node;
    }

    //RL
    private AVLNode RotateRightDouble(AVLNode node){
        node.children[1] = RotateRight((AVLNode)node.children[1]);
        return RotateLeft(node);
    }

    private int getHeight(AVLNode node){
        if(node == null){
            return -1;
        }
        return node.height;
    }
}
