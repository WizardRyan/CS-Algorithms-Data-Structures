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
        int enqueues = 0;
        q.enqueue(new WordInfo(a, 0, a));
        //run until we find the word or we run out of options (I.E., the Queue is empty)
        while(!q.isEmpty() && !done){
            WordInfo ladder = q.dequeue();
            //evaluate  all words of the same length in the dictionary
            for(int i = 0; i < l.size(); i++){
                String word = l.get(i);
                //only evaluate words that are off by one and that we haven't already used
                if(diffByOne(word, ladder.word) && !blackListed(word)){

                    WordInfo wordInfo = new WordInfo(word, ladder.moves + 1, ladder.history + " " + word);

                    if(word.equals(b)){
                        done = true;
                        System.out.println(wordInfo + " total enqueues: " + enqueues);
                        System.out.println();
                        break;
                    }
                    else{
                        q.enqueue(wordInfo);
                        enqueues++;
                        this.blackList.add(word);
                    }
                }
            }
        }
        if(!done){
            System.out.print("Ladder Impossible: ");
            if(!l.contains(a) || !l.contains(b)){
                System.out.println("Word(s) given not found in this dictionary");
            }
            else{
                System.out.println("Ladder not found using words in this dictionary");
            }
            System.out.println();
        }
        this.blackList = new ArrayList<String>();
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
