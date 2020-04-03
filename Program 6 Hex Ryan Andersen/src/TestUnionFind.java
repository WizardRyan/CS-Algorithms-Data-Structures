import com.sun.security.jgss.GSSUtil;

public class TestUnionFind {

    public static void main(String[] args){
        UnionFind test = new UnionFind(121);

        System.out.println("Unioning 0 and 1");
        test.union(0, 1);
        //we expect 1
        test.printParent(0);
        //we expect -2, the code was written so when size is equal, the second value in the union becomes parent
        test.printParent(1);

        System.out.println("\nUnioning 0 and 3");
        test.union(0, 3);
        //we expect 1
        test.printParent(0);
        //we expect 1 (path compression happens during union)
        test.printParent(3);
        //we expect -3, should have 3 children now
        test.printParent(1);

        System.out.println("\nUnioning 4 and 5");
        test.union(4, 5);
        //should still be -3
        test.printParent(1);

        System.out.println("\nUnioning 1 and 5");
        test.union(1, 5);
        printAll(5, test);

        System.out.println("\nFinding 4");
        int result = test.find(4);
        System.out.println("Resulting find on 4: " + result);
        //should be 1 from path compression
        test.printParent(4);

        System.out.println("\nUnioning 6, 7, 8, 9, 10, 11");
        test.union(6, 7);
        test.union(6, 8);
        test.union(8, 9);
        test.union(10, 6);
        test.union(7, 11);
        printAll(11, test);

        System.out.println("\nUnioning 6 and 3");
        test.union(3, 6);
        printAll(11, test);

        System.out.println("\nFinding 2 (should still be a root and return itself)");
        result = test.find(2);
        System.out.println("Resulting find on 2: " + result);
        test.printParent(2);
    }

    public static void printAll(int max, UnionFind uf){
        for(int i = 0; i <= max; i++){
            uf.printParent(i);
        }
    }
}
