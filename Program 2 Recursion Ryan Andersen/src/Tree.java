// ******************ERRORS********************************
// Throws UnderflowException as appropriate

import javax.lang.model.type.ArrayType;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

class UnderflowException extends RuntimeException {
    /**
     * Construct this exception object.
     * @param message the error message.
     */
    public UnderflowException(String message) {
        super(message);
    }
}

public class Tree<E extends Comparable<? super E>> {
    final String ENDLINE = "\n";
    private BinaryNode<E> root;  // Root of tree
    private BinaryNode<E> curr;  // Last node accessed in tree
    private String treeName;     // Name of tree

    /**
     * Create an empty tree
     *
     * @param label Name of tree
     */
    public Tree(String label) {
        treeName = label;
        root = null;
    }

    /**
     * Create non ordered tree from list in preorder
     * @param arr    List of elements
     * @param label  Name of tree
     * @param height Maximum height of tree
     */
    public Tree(ArrayList<E> arr, String label, int height) {
        this.treeName = label;
        root = buildTree(arr, height, null);
    }

    /**
     * Create BST
     * @param arr   List of elements to be added
     * @param label Name of tree
     */
    public Tree(ArrayList<E> arr, String label) {
        root = null;
        treeName = label;
        for (int i = 0; i < arr.size(); i++) {
            bstInsert(arr.get(i));
        }
    }


    /**
     * Create BST from Array
     * @param arr   List of elements to be added
     * @param label Name of  tree
     */
    public Tree(E[] arr, String label) {
        root = null;
        treeName = label;
        for (int i = 0; i < arr.length; i++) {
            bstInsert(arr[i]);
        }
    }

    /**
     * Change name of tree
     * @param name new name of tree
     */
    public void changeName(String name) {
        this.treeName = name;
    }

    /**
     * Return a string displaying the tree contents as a tree with one node per line
     */
    public String toString() {
        if (root == null)
            return (treeName + " Empty tree\n");
        else
            return this.treeName +"\n" + toString(0, root);
    }

    /**
     * Build a string that lists all tree nodes (and their parent) in order, with indentation with respect to node depth in tree
     * @param indent how far to indent the node
     * @param n the node to pass in, should be root on first call
     * @return The string representative of this function
     * Time Complexity: O(n)
     */
    private String toString(int indent, BinaryNode<E> n){
        if(n == null){
            return "";
        }
        String space = "";
        for(int i = 0; i < indent; i++){
            space += " ";
        }

        String s = space + "" + n.element + "[" + (n.parent != null ? n.parent.element : "no parent") + "]\n";

        return toString(indent + 1, n.right) + s + toString(indent + 1, n.left);
    }

    /**
     * Return a string displaying the tree contents as a single line
     */
    public String toString2() {
        if (root == null)
            return treeName + " Empty tree";
        else
            return treeName + " " + toString2(root);
    }

    /**
     * reverse left and right children recursively
     */
    public void flip() {
         flip(root);
    }

    /**
     * reverse left and right children recursively
     * @param n the node to flip children on, should be the root node initially
     * Time Complexity: O(n)
     */
    private void flip(BinaryNode<E> n){
        if(n != null){
            BinaryNode<E> temp = n.right;
            n.right = n.left;
            n.left = temp;
            flip(n.left);
            flip(n.right);
        }
    }


    /**
     * Find successor of "curr" node in tree
     * @return String representation of the successor
     */
    public String successor() {
        if (curr == null) curr = root;
        curr = successor(curr);
        if (curr == null) return "null";
        else return curr.toString();
    }

    /**
     * Find successor of node in tree
     * @param n the node to find successor of
     * @return the node's successor, null if none
     * Time Complexity: O(log n)
     */
    public BinaryNode<E> successor(BinaryNode<E> n){
        if(n.right != null){
            n = n.right;
            while(n.left != null){
                n = n.left;
            }
            return n;
        }
        else{
            E value = n.element;
            while(n.parent != null){
                n = n.parent;
                if(n.element.compareTo(value) > 0){
                    return n;
                }
            }
        }
        return null;
    }

    /**
     * Counts number of nodes in specifed level
     * @param level Level in tree, root is zero
     * @return count of number of nodes at specified level
     */
    public int nodesInLevel(int level) {
        return nodesInLevel(root, level);
    }

    /**
     * Counts number of nodes in specifed level
     * @param level Level in tree, root is zero
     * @param n the node to start counting from, should be root initially
     * @return count of number of nodes at specified level
     * Time Complexity: O(n)
     */
    private int nodesInLevel(BinaryNode<E> n, int level){
        if(n == null){
            return 0;
        }
        if(level == 0){
            return 1;
        }
        return nodesInLevel(n.left, level -1) + nodesInLevel(n.right, level -1);
    }

    /**
     * Print all paths from root to leaves
     */
    public void printAllPaths() {
        printAllPaths(this.root);
    }

    /**
     * Print all paths from root to leaves
     * @param n the node to print from, should be root initially
     *Time Complexity: O(n log n)
     */
    private void printAllPaths(BinaryNode<E> n){
        if(n.left == null && n.right == null){
            printPathFromLeaf(n);
        }
        else if(n.left == null){
            printAllPaths(n.right);
        }
        else if(n.right == null){
            printAllPaths(n.left);
        }
        else{
            printAllPaths(n.right);
            printAllPaths(n.left);
        }
    }

    private void printPathFromLeaf(BinaryNode<E> n){
        ArrayList<E> vals = new ArrayList<>();
        while(n.parent != null){
            vals.add(n.element);
            n = n.parent;
        }
        System.out.print(n.element + " ");
        for(int i = vals.size() - 1; i >= 0; i--){
            System.out.print(vals.get(i) + " ");
        }
        System.out.println();
    }

    /**
     * Print contents of levels in zig zag order starting at maxLevel
     * @param maxLevel an int denoting the maximum level to print from
     * Time Complexity: O(n log n)
     */
    public void byLevelZigZag(int maxLevel) {
        ArrayList<ArrayList<E>> levels = new ArrayList<>();
        for(int i = 0; i <= maxLevel; i++){
            levels.add(new ArrayList<E>());
        }
        byLevelZigZag(this.root, maxLevel, levels);
        for(int i = 0; i < levels.size(); i++){
            if(i % 2 == 1){
                for(int j = levels.get(i).size() - 1; j >= 0; j--){
                    System.out.print(levels.get(i).get(j) + " ");
                }
            }
            else{
                for(E e : levels.get(i)){
                    System.out.print(e + " ");
                }
            }
            System.out.println();
        }
    }

    public void byLevelZigZag(BinaryNode<E> n, int maxLevel, ArrayList<ArrayList<E>> levels){
        if(maxLevel > -1){
            if(n != null){
                levels.get(maxLevel).add(n.element);
                byLevelZigZag(n.left, maxLevel - 1, levels);
                byLevelZigZag(n.right, maxLevel - 1, levels);
            }
        }
    }

    /**
     * Counts all non-null binary search trees embedded in tree
     * @return Count of embedded binary search trees
     * Time Complexity: O(n^2)
     */
    public Integer countBST() {
        if (root == null) return 0;
        return countBST(this.root) + (isBST(this.root) ? 1 : 0);
    }

    public Boolean isBST(BinaryNode<E> n){
        if(n.left == null && n.right == null){
            return true;
        }
        if(n.right == null){
            if(n.left.element.compareTo(n.element) < 0){
                return isBST(n.left);
            }
            return false;
        }
        if(n.left == null){
            if(n.right.element.compareTo(n.element) > 0){
                return isBST(n.right);
            }
            return false;
        }

        if(n.right.element.compareTo(n.element) > 0 && n.left.element.compareTo(n.element) < 0){
            return isBST(n.left) && isBST(n.right);
        }
        return false;
    }

    public Integer countBST(BinaryNode<E> n){
        if(n == null){
            return 0;
        }
        if(isBST(n)){
            return 1;
        }
        return countBST(n.left) + countBST(n.right);
    }

    /**
     * Insert into a bst tree; duplicates are allowed
     * @param x the item to insert.
     */
    public void bstInsert(E x) {

        root = bstInsert(x, root, null);
    }

    /**
     * Determines if item is in tree
     * @param item the item to search for.
     * @return true if found.
     */
    public boolean contains(E item) {

        return bstContains(item, root);
    }

    /**
     * Remove all paths from tree that sum to less than given value
     * @param sum: minimum path sum allowed in final tree
     * Time Complexity: O(n)
     */
    public void pruneK(Integer sum) {
        pruneK(sum, this.root);
    }

    private boolean hitSum(BinaryNode<E> n, Integer sum){
        if(n.element instanceof Integer){
            int s = 0;
            BinaryNode<E> a = n;
            while (s < sum && a.parent != null){
                s += (Integer) a.element;
                a = a.parent;
            }
            s += (Integer)a.element;
            return s >= sum;
        }
        return false;
    }

    private void pruneK(int sum, BinaryNode<E> n){
        if(n != null){
            if(isLeaf(n)){
                if(!hitSum(n, sum)){
                    deleteTillTwoChildren(n);
                }
            }
            else if(n.left == null){
                pruneK(sum, n.right);
            }
            else if(n.right == null){
                pruneK(sum, n.left);
            }
            else{
                pruneK(sum, n.left);
                pruneK(sum, n.right);
            }
        }
    }

    private void deleteTillTwoChildren(BinaryNode<E> n){
        if(n.parent != null){
            while(n.parent.left == null || n.parent.right == null){
                BinaryNode<E> a = n;
                n = n.parent;
                a.delete();
            }
            n.delete();
        }
    }

    private boolean isLeaf(BinaryNode<E> n){
        if(n == null){
            throw new RuntimeException("Not a leaf: it's a null pointer!");
        }
        return n.left == null && n.right == null;
    }

    /**
     * Build tree given inOrder and preOrder traversals.  Each value is unique
     * @param inOrder  List of tree nodes in inorder
     * @param preOrder List of tree nodes in preorder
     * Time Complexity: 0(n)
     */
    public void buildTreeTraversals(E[] inOrder, E[] preOrder) {
        this.root = null;
        List<E> inO = Arrays.asList(inOrder);
        List<E> preO = Arrays.asList(preOrder);
        this.root = buildTreeTraversals(inO, preO);
    }

    private BinaryNode<E> buildTreeTraversals(List<E> inOrder, List<E> preOrder){
        if(inOrder.size() <= 0) return null;
        BinaryNode<E> n = new BinaryNode<E>(preOrder.get(0), null, null, null);

        if(inOrder.size() > 1){
            List<E> leftInOrder = inOrder.subList(0, inOrder.indexOf(preOrder.get(0)));
            List<E> rightInOrder = inOrder.subList(leftInOrder.size() + 1, inOrder.size());
            List<E> leftPreOrder = preOrder.subList(1, leftInOrder.size() + 1);
            List<E> rightPreOrder = preOrder.subList(leftInOrder.size() + 1, preOrder.size());
            n.left = buildTreeTraversals(leftInOrder, leftPreOrder );
            n.right = buildTreeTraversals(rightInOrder, rightPreOrder);
        }
        if(n.left != null) n.left.parent = n;
        if(n.right != null) n.right.parent = n;
        return n;
    }

    private int indexOf(E[] arr, E val){
        for(int i = 0; i < arr.length; i++){
            if(arr[i].compareTo(val) == 0) return i;
        }
        return -1;
    }


    /**
     * Find the least common ancestor of two nodes
     * @param a first node
     * @param b second node
     * @return String representation of ancestor
     * Time Complexity: O(log n)
     */
    public String lca(E a, E b) {
        BinaryNode<E> ancestor = null;
        if (a.compareTo(b) < 0) {
            ancestor = lca(root, a, b);
        } else {
            ancestor = lca(root, b, a);
        }
        if (ancestor == null) return "none";
        else return ancestor.toString();
    }

    private BinaryNode<E> lca(BinaryNode<E> n, E a, E b){
        if(n == null){
            return null;
        }

        if(n.element.compareTo(a) > 0 && n.element.compareTo(b) > 0){
            return lca(n.right, a, b);
        }
        if(n.element.compareTo(a) < 0 && n.element.compareTo(b) < 0){
            return lca(n.left, a, b);
        }
        return n;
    }

    /**
     * Balance the tree
     * Time Complexity: O(n)
     */
    public void balanceTree() {
        ArrayList<BinaryNode<E>> nodes = new ArrayList<>();
        getNodes(this.root, nodes);

        this.root = balanceTree(nodes, 0, nodes.size() - 1);
        System.out.println("done");
    }

    private BinaryNode<E> balanceTree(ArrayList<BinaryNode<E>> nodes, int begin, int end){

        if(begin > end){
            return null;
        }
            int middle = (begin + end) / 2;

            BinaryNode<E> n = new BinaryNode<E>(nodes.get(middle).element, null, null, null);

            n.left = balanceTree(nodes, begin, middle - 1);
            if(n.left != null){
                n.left.parent = n;
            }

            n.right = balanceTree(nodes, middle + 1, end);
            if(n.right != null){
                n.right.parent = n;
            }

            return n;
    }

    private void getNodes(BinaryNode<E> n, ArrayList<BinaryNode<E>> nodes){
        if(n != null){
            getNodes(n.left, nodes);
            nodes.add(n);
            getNodes(n.right, nodes);
        }

    }

    /**
     * In a BST, keep only nodes between range
     * @param a lowest value
     * @param b highest value
     * Time Complexity O(n)
     */
    public void keepRange(E a, E b) {
        keepRange(this.root, a, b);
        //manually check root because the delete method I added to binary nodes only works on nodes with a parent
        if(!inRange(this.root, a, b)){
            if(this.root.left == null){
                this.root = this.root.right;
            }
            else if(this.root.right == null){
                this.root = this.root.left;
            }
            this.root.parent = null;
        }
     }

     public void keepRange(BinaryNode<E> n, E a, E b){
        if(n != null){
            keepRange(n.right, a, b);
            keepRange(n.left, a, b);
            if(!inRange(n, a, b)){
                n.delete();
            }
        }
     }

     public boolean inRange(BinaryNode<E> n, E a, E b){
         return n.element.compareTo(a) >= 0 && n.element.compareTo(b) <= 0;
     }

    //PRIVATE

     /**
     * Build a NON BST tree by preorder
     *
     * @param arr    nodes to be added
     * @param height maximum height of tree
     * @param parent parent of subtree to be created
     * @return new tree
     */
    private BinaryNode<E> buildTree(ArrayList<E> arr, int height, BinaryNode<E> parent) {
        if (arr.isEmpty()) return null;
        BinaryNode<E> curr = new BinaryNode<>(arr.remove(0), null, null, parent);
        if (height > 0) {
            curr.left = buildTree(arr, height - 1, curr);
            curr.right = buildTree(arr, height - 1, curr);
        }
        return curr;
    }

    /**
     * Internal method to insert into a subtree.
     * In tree is balanced, this routine runs in O(log n)
     *
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private BinaryNode<E> bstInsert(E x, BinaryNode<E> t, BinaryNode<E> parent) {
        if (t == null)
            return new BinaryNode<>(x, null, null, parent);

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) {
            t.left = bstInsert(x, t.left, t);
        } else {
            t.right = bstInsert(x, t.right, t);
        }

        return t;
    }


    /**
     * Internal method to find an item in a subtree.
     * This routine runs in O(log n) as there is only one recursive call that is executed and the work
     * associated with a single call is independent of the size of the tree: a=1, b=2, k=0
     *
     * @param x is item to search for.
     * @param t the node that roots the subtree.
     *          SIDE EFFECT: Sets local variable curr to be the node that is found
     * @return node containing the matched item.
     */
    private boolean bstContains(E x, BinaryNode<E> t) {
        curr = null;
        if (t == null)
            return false;

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0)
            return bstContains(x, t.left);
        else if (compareResult > 0)
            return bstContains(x, t.right);
        else {
            curr = t;
            return true;    // Match
        }
    }



    /**
     * Internal method to return a string of items in the tree in order
     * This routine runs in O(??)
     * @param t the node that roots the subtree.
     */
    private String toString2(BinaryNode<E> t) {
        if (t == null) return "";
        StringBuilder sb = new StringBuilder();
        sb.append(toString2(t.left));
        sb.append(t.element.toString() + " ");
        sb.append(toString2(t.right));
        return sb.toString();
    }

    // Basic node stored in unbalanced binary  trees
    private static class BinaryNode<AnyType> {
        AnyType element;            // The data in the node
        BinaryNode<AnyType> left;   // Left child
        BinaryNode<AnyType> right;  // Right child
        BinaryNode<AnyType> parent; //  Parent node

        // Constructors
        BinaryNode(AnyType theElement) {
            this(theElement, null, null, null);
        }

        BinaryNode(AnyType theElement, BinaryNode<AnyType> lt, BinaryNode<AnyType> rt, BinaryNode<AnyType> pt) {
            element = theElement;
            left = lt;
            right = rt;
            parent = pt;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Node:");
            sb.append(element);
            if (parent == null) {
                sb.append("<>");
            } else {
                sb.append("<");
                sb.append(parent.element);
                sb.append(">");
            }

            return sb.toString();
        }

        public void delete(){
            if(this.parent != null){
                if((this.parent.left != null) && this.parent.left.element == this.element){
                    this.parent.left = null;
                }
                else{
                    this.parent.right = null;
                }
            }
        }
    }


    // Test program
    public static void main(String[] args) {
        long seed = 436543;
        Random generator = new Random(seed);  // Don't use a seed if you want the numbers to be different each time
        final String ENDLINE = "\n";

        int val = 60;
        final int SIZE = 8;

        Integer[] v1 = {25, 10, 60, 55, 58, 56, 14, 63, 8, 50, 6, 9};
        ArrayList<Integer> v2 = new ArrayList<Integer>();
        for (int i = 0; i < SIZE * 2; i++) {
            int t = generator.nextInt(200);
            v2.add(t);
        }
        v2.add(val);
        Integer[] v3 = {200, 15, 3, 65, 83, 70, 90};
        ArrayList<Integer> v4 = new ArrayList<Integer>(Arrays.asList(v3));
        Integer[] v = {21, 8, 5, 6, 7, 19, 10, 40, 43, 52, 12, 60};
        ArrayList<Integer> v5 = new ArrayList<Integer>(Arrays.asList(v));
        Integer[] inorder = {4, 2, 1, 7, 5, 8, 3, 6};
        Integer[] preorder = {1, 2, 4, 3, 5, 7, 8, 6};


        Tree<Integer> tree1 = new Tree<Integer>(v1, "Tree1:");
        Tree<Integer> tree2 = new Tree<Integer>(v2, "Tree2:");
        Tree<Integer> tree3 = new Tree<Integer>(v3, "Tree3:");
        Tree<Integer> treeA = new Tree<Integer>(v4, "TreeA:", 2);
        Tree<Integer> treeB = new Tree<Integer>(v5, "TreeB", 3);
        Tree<Integer> treeC = new Tree<Integer>("TreeC");
        System.out.println(tree1.toString());
        System.out.println(tree1.toString2());

        System.out.println(treeA.toString());

        treeA.flip();
        System.out.println("Now flipped " + treeA.toString());

        System.out.println(tree2.toString());
        tree2.contains(val);  //Sets the current node inside the tree6 class.
        int succCount = 5;  // how many successors do you want to see?
        System.out.println("In Tree2, starting at " + val + ENDLINE);
        for (int i = 0; i < succCount; i++) {
            System.out.println("The next successor is " + tree2.successor());
        }

        System.out.println(tree1.toString());
        for (int mylevel = 0; mylevel < SIZE; mylevel += 2) {
            System.out.println("Number nodes at level " + mylevel + " is " + tree1.nodesInLevel(mylevel));
        }
        System.out.println("All paths from tree1");
        tree1.printAllPaths();

        System.out.print("Tree1 byLevelZigZag: ");
        tree1.byLevelZigZag(5);
        System.out.print("Tree2 byLevelZigZag (3): ");
        tree2.byLevelZigZag(3);
        treeA.flip();
        System.out.println(treeA.toString());
        System.out.println("treeA Contains BST: " + treeA.countBST());

        System.out.println(treeB.toString());
        System.out.println("treeB Contains BST: " + treeB.countBST());

        treeB.pruneK(60);
        treeB.changeName("treeB after pruning 60");
        System.out.println(treeB.toString());
        treeA.pruneK(220);
        treeA.changeName("treeA after pruning 220");
        System.out.println(treeA.toString());

        treeC.buildTreeTraversals(inorder, preorder);
        treeC.changeName("Tree C built from inorder and preorder traversals");
        System.out.println(treeC.toString());

        System.out.println(tree1.toString());
        System.out.println("tree1 Least Common Ancestor of (56,61) " + tree1.lca(56, 61) + ENDLINE);

        System.out.println("tree1 Least Common Ancestor of (6,25) " + tree1.lca(6, 25) + ENDLINE);
        System.out.println(tree3.toString());
        tree3.balanceTree();
        tree3.changeName("tree3 after balancing");
        System.out.println(tree3.toString());

        System.out.println(tree1.toString());
        tree1.keepRange(10, 50);
        tree1.changeName("tree1 after keeping only nodes between 10 and 50");
        System.out.println(tree1.toString());
        tree3.keepRange(3, 85);
        tree3.changeName("tree3 after keeping only nodes between 3  and 85");
        System.out.println(tree3.toString());


    }

}
