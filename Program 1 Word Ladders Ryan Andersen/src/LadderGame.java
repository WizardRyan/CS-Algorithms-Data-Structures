import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.util.ArrayList;

public class LadderGame {
    static int MaxWordSize = 15;
    ArrayList<String>[] allList;  // Array of ArrayLists of words of each length.
    Random random;
    private ArrayList<String> blackList = new ArrayList<String>();

    public LadderGame(String file) {
        random = new Random();
        allList = new ArrayList[MaxWordSize];
        for (int i = 0; i < MaxWordSize; i++)
            allList[i] = new ArrayList<String>();

        try {
            Scanner reader = new Scanner(new File(file));
            while (reader.hasNext()) {
                String word = reader.next();
                if (word.length()< MaxWordSize) allList[word.length()].add(word);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void play(String a, String b) {
        Queue<WordInfo> q  = new Queue<>();
        if (a.length() != b.length()){
            System.out.println("Ladder Impossible: Words are not the same length");
            return;
         }
        if (a.length()  >= MaxWordSize) return;
        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> l = allList[a.length()];
        list = (ArrayList<String>) l.clone();
        System.out.println("Seeking a solution from " + a + " ->" + b + " Size of List " + list.size());
        
        // Solve the word ladder problem
        boolean done = false;
        int count = 0;
        q.enqueue(new WordInfo(a, 0, a));
        while(!q.isEmpty() && !done){
            WordInfo ladder = q.dequeue();
            for(int i = 0; i < l.size(); i++){
                if(diffByOne(l.get(i), ladder.word) && !blackListed(l.get(i))){
                    if(l.get(i).equals(b)){
                        done = true;
                        System.out.println(new WordInfo(b, ladder.moves + 1, ladder.history + " " + b) + " total enqueues: " + count);
                        System.out.println();
                        break;
                    }
                    q.enqueue(new WordInfo(l.get(i), ladder.moves + 1, ladder.history + " " + l.get(i)));
                    count++;
                    this.blackList.add(l.get(i));
                }
            }
        }
        if(!done){
            System.out.print("Ladder Impossible: ");
            if(!l.contains(a) || !l.contains(b)){
                System.out.println("Word(s) given not found in dictionary");
            }
            else{
                System.out.println("Ladder not found using words in this dictionary");
            }
            System.out.println();
        }
    }


    public void play(int len) {
       if (len >= MaxWordSize) return;
        ArrayList<String> list = allList[len];
        String a = list.get(random.nextInt(list.size()));
        String b = list.get(random.nextInt(list.size()));
        play(a, b);

    }

    private boolean diffByOne(String a, String b){
        int count = 0;
        for(int i = 0; i < a.length(); i++){
            if(a.charAt(i) != b.charAt(i)){
                count++;
            }
        }
        return count == 1;
    }

    private boolean blackListed(String a){
        return this.blackList.contains(a);
    }
}
