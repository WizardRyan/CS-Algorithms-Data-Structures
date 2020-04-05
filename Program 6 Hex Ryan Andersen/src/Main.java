import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        final int BOARD_SIZE = 121;
        HexGame game = new HexGame(BOARD_SIZE);

        String FILE_NAME = "moves2.txt";

        try{
            File file = new File(FILE_NAME);
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                int move = Integer.parseInt(scanner.nextLine());
                game.playTurn(move);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
