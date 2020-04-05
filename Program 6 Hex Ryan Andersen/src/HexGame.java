import java.util.ArrayList;

public class HexGame {
    private UnionFind unionFind;
    private ArrayList<Cell> board;
    private boolean bluesTurn = true;
    private int sideLength;
    private int turnCount = 0;
    private boolean gameWon = false;

    private final int RIGHT;
    private final int LEFT;
    private final int TOP;
    private final int BOTTOM;

    private ArrayList<Integer> leftColumn;
    private ArrayList<Integer> rightColumn;
    private ArrayList<Integer> topRow;
    private  ArrayList<Integer> bottomRow;

    private enum Cell{
        EMPTY, BLUE, RED
    }

    HexGame(int boardSize){
        this.RIGHT = boardSize;
        this.LEFT = boardSize + 1;
        this.TOP = boardSize + 2;
        this. BOTTOM = boardSize + 3;

        this.unionFind = new UnionFind(boardSize + 4);

        this.board = new ArrayList<>();
        for(int i = 0; i < boardSize; i++){
            this.board.add(Cell.EMPTY);
        }

        this.sideLength = (int) Math.sqrt(boardSize);

        leftColumn = new ArrayList<>();
        rightColumn = new ArrayList<>();
        topRow = new ArrayList<>();
        bottomRow = new ArrayList<>();
        for(int i = 0; i < sideLength; i++){
            leftColumn.add(i * sideLength);
            topRow.add(i);
            rightColumn.add((i *sideLength) + sideLength - 1);
            bottomRow.add(boardSize - (i + 1));
        }
    }

    public void playTurn(int cell){

        System.out.println((this.bluesTurn ? "Blue" : "Red") + " chooses cell " + cell);

        //the code works off index 0, while players count starting on 1, so we subtract 1
        cell -= 1;
        if(this.board.get(cell) != Cell.EMPTY){
            System.out.println("Cell already taken! Move ignored.");
        }
        else if(this.gameWon){
            System.out.println("Game already won! Move ignored");
            this.bluesTurn = !this.bluesTurn;
        }
        else{
            this.turnCount++;
            placePiece(cell);
            if(someoneHasWon()){
                declareWinner();
            }
            this.bluesTurn = !this.bluesTurn;
        }
    }

    private boolean someoneHasWon(){
        return this.unionFind.find(this.TOP) == this.unionFind.find(this.BOTTOM) || this.unionFind.find(this.LEFT) == this.unionFind.find(this.RIGHT);
    }

    private void declareWinner(){
        String winner = this.bluesTurn ? "Blue" : "Red";
        this.gameWon = true;
        System.out.println("-------------->" + winner + " has won after " + this.turnCount + " turns! Here's the final board: ");
        printBoard();
    }

    private void placePiece(int cell){
        this.board.set(cell, (this.bluesTurn ? Cell.BLUE : Cell.RED));
        combineNeighbors(cell);
    }

    private void combineNeighbors(int cell){
        int[] neighbors = getNeighbors(cell);
        for (int neighbor : neighbors) {
            this.unionFind.union(cell, neighbor);
        }
    }

    private void printBoard(){
        String space = "";
        int increment = 1;
        for(int i = 0; i < this.board.size(); i++){
            String color = "";
            String text = "" + 0;
            Cell cell = this.board.get(i);
            final String ANSI_RESET = "\u001B[0m";

            if(cell == Cell.RED){
                color = "\u001B[31m";
                text = "R";
            }
            else if (cell == Cell.BLUE){
                color = "\u001B[36m";
                text = "B";
            }

            System.out.print(color + text + ANSI_RESET + " ");

            if(increment == this.sideLength) {
                System.out.println();
                space += " ";
                System.out.print(space);
                increment = 0;
            }
            increment++;
        }
        System.out.println();
    }

    private int[] getNeighbors(int cell){
        //checkTop and checkBottom handle corner cases so we check those first
        int[] neighbors;
        if(this.topRow.contains(cell)) neighbors = checkTop(cell);
        else if(this.bottomRow.contains(cell)) neighbors = checkBottom(cell);
        else if(this.leftColumn.contains(cell)) neighbors = checkLeft(cell);
        else if(this.rightColumn.contains(cell)) neighbors = checkRight(cell);
        else{
            neighbors = new int[]{cell -1, cell + 1, cell - this.sideLength, cell - this.sideLength + 1, cell + this.sideLength -1, cell + this.sideLength};
        }
        return filterNeighbors(neighbors);
    }

    //return only neighbors that are populated, and populated with the same color
    private int[] filterNeighbors(int[] neighbors){
        ArrayList<Integer> filteredNeighbors = new ArrayList<>();
        for (int cell : neighbors) {
            if (this.bluesTurn && isBlue(cell)) {
                filteredNeighbors.add(cell);
            } else if (!this.bluesTurn && isRed(cell)) {
                filteredNeighbors.add(cell);
            }
        }
        int[] arr = new int[filteredNeighbors.size()];
        for(int i = 0; i < filteredNeighbors.size(); i++){
            arr[i] = filteredNeighbors.get(i);
        }
        return arr;
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
        return new int[] {wall, cell -1, cell - this.sideLength - 1, cell - this.sideLength, cell + 1};
    }

    private int[] checkLeft(int cell){
        if(this.bluesTurn) return new int[]{this.LEFT, cell - this.sideLength, cell - this.sideLength + 1, cell + 1, cell + this.sideLength};
        return new int[]{cell - this.sideLength, cell - this.sideLength + 1, cell + 1, cell + this.sideLength};
    }

    private int[]checkRight(int cell){
        if(this.bluesTurn) return new int[]{this.RIGHT, cell - this.sideLength, cell - 1, cell + this.sideLength, cell + this.sideLength - 1};
        return new int[]{cell - this.sideLength, cell - 1, cell + this.sideLength, cell + this.sideLength - 1};
    }

    private boolean isBlue(int cell){
        if(cell == this.TOP || cell == this.BOTTOM) return false;
        return cell == this.LEFT || cell == this.RIGHT || this.board.get(cell) == Cell.BLUE ;
    }

    private boolean isRed(int cell){
        if(cell == this.LEFT || cell == this.RIGHT) return false;
        return cell == this.TOP || cell == this.BOTTOM || this.board.get(cell) == Cell.RED;
    }
}
