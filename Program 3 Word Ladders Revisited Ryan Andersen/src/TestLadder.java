
public class TestLadder {
    public static void main(String[] args) {

        LadderGame g = new LadderGame("dictionary.txt");

        playBoth(g,"kiss", "woof");
        playBoth(g,"cock", "numb");
        playBoth(g,"jura", "such");
        playBoth(g,"stet", "whey");
        playBoth(g,"rums", "numb");
        playBoth(g,"fire", "sail");
    }


    private static void playBoth(LadderGame g, String a , String b){
        System.out.println("--------------------------");
        System.out.println("Brute force solution: ");
        g.playBrute(a, b);
        System.out.println("A* solution: ");
        g.play(a, b);
    }

}