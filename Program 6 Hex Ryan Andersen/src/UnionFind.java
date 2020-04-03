import java.util.ArrayList;

public class UnionFind {
    private ArrayList<Integer> data;

    UnionFind(int size){
        this.data = new ArrayList<>();
        for(int i = 0; i < size; i++){
            this.data.add(-1);
        }
    }

    public void union(int a, int b){
        a = find(a);
        b = find(b);
        if(a == b) return;
        int aValue = this.data.get(a);
        int bValue = this.data.get(b);
        // a is bigger as we're storing the size with a negative value
        if(aValue < bValue){
            aValue += bValue;
            this.data.set(a, aValue);
            this.data.set(b, a);
        }
        else{
            bValue += aValue;
            this.data.set(b, bValue);
            this.data.set(a, b);
        }
    }

    public int find(int val){
        ArrayList<Integer> touchedVals = new ArrayList<>();

        while(this.data.get(val) > 0){
            touchedVals.add(val);
            val = this.data.get(val);
        }

        //path compression
        for( int value : touchedVals){
            this.data.set(value, val);
        }

        return val;
    }

    public void printParent(int val){
        System.out.println("Parent of " + val + ": " + this.data.get(val));
    }
}
