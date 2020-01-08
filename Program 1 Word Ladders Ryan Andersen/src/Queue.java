class Queue <E>{
    private class Node<E>{
        public E data = null;
        public Node<E> next = null;

        public Node(){

        }
        public Node(E val){
            this.data = val;
        }
    }

    private Node<E> head = new Node<E>();
    private Node<E> tail = head;

    public void enqueue(E val){
        Node<E> newNode = new Node<>(val);
        this.tail.next = newNode;
        this.tail = newNode;
    }

    public E dequeue(){
        if(this.head.data == null && this.head.next == null){
            throw new RuntimeException("Cannot dequeue empty list");
        }
        if(this.head.next == null && !(this.head.data == null)){
            E data = this.head.data;
            init();
            return data;
        }
        else if(this.head.data == null){
            this.head = this.head.next;
        }

        E val = this.head.data;
        if(this.head.next == null){
            init();
        }
        else{
            this.head = this.head.next;
        }
        return val;
    }

    @Override
    public String toString() {
        Node<E> current = this.head;
        String result = "";
        while(current.next != null){
            result += current.data + "\n";
            current = current.next;
        }
        result += current.data + "\n";
        return result;
    }

    private void init(){
        this.head = new Node<E>();
        this.tail = this.head;
    }

    boolean isEmpty(){
        return this.head.data == null && this.head.next == null;
    }
}