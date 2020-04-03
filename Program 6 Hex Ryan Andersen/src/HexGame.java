import java.util.ArrayList;

public class HexGame {


    private UnionFind unionFind;
    private ArrayList<Cell> board;
    private boolean bluesTurn = true;
    private int sideLength;

    private final int RIGHT = 121;
    private final int LEFT = 122;
    private final int TOP = 123;
    private final int BOTTOM = 124;

    private ArrayList<Integer> leftColumn;
    private ArrayList<Integer> rightColumn;
    private ArrayList<Integer> topRow;
    private  ArrayList<Integer> bottomRow;

    private enum Cell{
        EMPTY, BLUE, RED
    }

    HexGame(int boardSize){
        this.unionFind = new UnionFind(boardSize + 4);
        this.board = new ArrayList<>();
        for(int i = 0; i < boardSize; i++){
            this.board.add(Cell.EMPTY);
        }

        leftColumn = new ArrayList<>();
        rightColumn = new ArrayList<>();
        topRow = new ArrayList<>();
        bottomRow = new ArrayList<>();

        this.sideLength = (int) Math.sqrt(boardSize);
        for(int i = 0; i < sideLength; i++){
            leftColumn.add(i * sideLength);
            topRow.add(i);
            rightColumn.add((i *sideLength) + sideLength - 1);
            bottomRow.add(boardSize - (i + 1));
        }
//        System.out.println(leftColumn);
//        System.out.println(topRow);
//        System.out.println(rightColumn);
//        System.out.println(bottomRow);
    }

    public void playTurn(int cell){
        cell -= 1;
        if(this.board.get(cell) != Cell.EMPTY){
            System.out.println("Cell already taken! Move ignored.");
        }
        else{
            int[] neighbors = getNeighbors(cell);
            for(int i = 0; i < neighbors.length; i++){
                this.unionFind.union(cell, neighbors[i]);
            }
            if(this.unionFind.find(this.TOP) == this.unionFind.find(this.BOTTOM)){
                System.out.println("RED WON!");
                return;
            }
            else if(this.unionFind.find(this.LEFT) == this.unionFind.find(this.RIGHT)){
                System.out.println("BLUE WON!");
                return;
            }
        }
        this.bluesTurn = !this.bluesTurn;
    }

    private int[] getNeighbors(int cell){
        //checkTop and checkBottom handle corner cases so we check those first
        if(this.topRow.contains(cell)) return checkTop(cell);
        if(this.bottomRow.contains(cell)) return checkBottom(cell);
        if(this.leftColumn.contains(cell)) return checkLeft(cell);
        if(this.rightColumn.contains(cell)) return checkRight(cell);
        return new int[]{cell -1, cell + 1, cell - this.sideLength, cell - this.sideLength + 1, cell + this.sideLength -1, cell + this.sideLength};
    }

    private int[] checkTop(int cell){
        int wall = this.TOP;
        //top left corner
        if(this.leftColumn.contains(cell)){
            if(this.bluesTurn) wall = this.LEFT;
            return new int[]{wall, cell + 1, cell + this.sideLength};
        }
        //top right corner
        else if(this.rightColumn.contains(cell)){
            if(this.bluesTurn) wall = this.RIGHT;
            return new int[] {wall, cell - 1, cell + this.sideLength, cell +this.sideLength - 1};
        }
        if(this.bluesTurn) return new int[] {cell - 1, cell + this.sideLength - 1, cell + this.sideLength, cell + 1};
        return new int[] {wall, cell - 1, cell + this.sideLength - 1, cell + this.sideLength, cell + 1};
    }

    private int[] checkBottom(int cell){
        int wall = this.BOTTOM;
        //bottom left corner
        if(this.leftColumn.contains(cell)){
            if(this.bluesTurn) wall = this.LEFT;
            return new int[]{wall, cell - this.sideLength, cell - this.sideLength + 1, cell + 1};
        }
        //bottom right corner
        else if(this.rightColumn.contains(cell)){
            if(this.bluesTurn) wall = this.RIGHT;
            return new int[]{wall, cell - 1, cell - this.sideLength};
        }
        if(this.bluesTurn) return new int[] {cell -1, cell + this.sideLength - 1, cell + this.sideLength, cell + 1};
        return new int[] {wall, cell -1, cell + this.sideLength - 1, cell + this.sideLength, cell + 1};
    }

    private int[] checkLeft(int cell){
        if(this.bluesTurn) return new int[]{this.LEFT, cell - this.sideLength, cell - this.sideLength + 1, cell + 1, cell + this.sideLength};
        return new int[]{cell - this.sideLength, cell - this.sideLength + 1, cell + 1, cell + this.sideLength};
    }

    private int[]checkRight(int cell){
        if(this.bluesTurn) return new int[]{this.RIGHT, cell - this.sideLength, cell - 1, cell + this.sideLength, cell + this.sideLength - 1};
        return new int[]{cell - this.sideLength, cell - 1, cell + this.sideLength, cell + this.sideLength - 1};
    }

    private void printArray(ArrayList<Integer> a){
        for(int i = 0; i < a.size(); i++){
            System.out.println(a.get(i));
        }
    }
}
