public class TestHeap {
    public static void  main(String[] args){
        MinHeap<Integer> test = new MinHeap<>();
        test.insert(3);
        test.insert(4);
        test.insert(2);
        test.insert(7);
        System.out.println(test.deleteMin());
        System.out.println(test.deleteMin());
        System.out.println(test.deleteMin());
        System.out.println(test.deleteMin());
    }
}
