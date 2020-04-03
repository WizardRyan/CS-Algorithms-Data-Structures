import java.util.ArrayList;

public class MinHeap<E extends Comparable<E>> {



    private class Node<E extends Comparable<E>>{
        public E data;
        public Node<E> left;
        public Node<E> right;
        public int npl;
        Node (E data){
            this.data = data;
            this.left = null;
            this.right = null;
            this.npl = 0;
        }
    }

    Node<E> root;

    MinHeap(){
       this.root = null;
    }

    public void insert(E data){
        Node<E> n = new Node<E>(data);
        if(this.root == null){
            this.root = n;
        }
        else{
            this.root = merge(this.root, n);
        }
    }

    public E deleteMin(){
        if(this.root == null){
            return null;
        }
        E min = this.root.data;
        this.root = merge(this.root.left, this.root.right);
        return min;
    }

    private Node<E> merge(Node<E> t1, Node<E> t2){
        Node<E> smaller;
        if(t1 == null){
            return t2;
        }
        else if (t2 == null){
            return t1;
        }

        if(t1.data.compareTo(t2.data) < 0){
            t1.right = merge(t1.right, t2);
            smaller = t1;
        }
        else{
            t2.right = merge(t2.right, t1);
            smaller = t2;
        }
        if (!isLeftist(smaller)) {
            //if not leftist, swap kids
            Node<E> temp = null;
            if(smaller.left != null){
                temp = smaller.left;
            }
            //if it's not leftist there must be a node on the right, no need to check for null
            smaller.left = smaller.right;
            smaller.right = temp;
        }
        updateNpl(smaller);
        return smaller;
    }

    private boolean isLeftist(Node<E> t){
        if(t.left == null && t.right == null){
            return true;
        }
        if(t.right == null){
            return true;
        }
        if(t.left == null){
            return false;
        }
        return t.left.npl <= t.right.npl && isLeftist(t.left) && isLeftist(t.right);
    }

    private void updateNpl(Node<E> n){
        if(n.left == null || n.right == null){
            n.npl = 0;
        }
        else{
            n.npl = n.left.npl < n.right.npl ? n.left.npl : n.right.npl;
        }
    }
}
