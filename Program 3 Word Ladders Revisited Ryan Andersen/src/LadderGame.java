import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.util.ArrayList;

public class LadderGame {
    static int MaxWordSize = 15;
    ArrayList<String>[] allList;  // Array of ArrayLists of words of each length.
    Random random;
    private ArrayList<BlackWord> blackList = new ArrayList<BlackWord>();
    private ArrayList<String> bruteBlackList = new ArrayList<>();

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
        AVLTree<WordInfo> wordTree = new AVLTree<>();
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

        //start with the root word
        WordInfo first = new WordInfo(a, 0, a);
        first.calcPriority(b);
        wordTree.insert(first);

        boolean done = false;
        int enqueues = 0;
        //run until we find the word or we run out of options (I.E., the wordTree is empty)
        while(!wordTree.isEmpty() && !done){
            WordInfo ladder = wordTree.findMin();
            wordTree.deleteMin();
            //evaluate  all words of the same length in the dictionary
            for(int i = 0; i < l.size(); i++){
                String word = l.get(i);
                //only evaluate words that are off by one and that we haven't already used in a shorter ladder
                if(diffByOne(word, ladder.word) && !isBlackListed(word, ladder.moves + 1)){

                    WordInfo wordInfo = new WordInfo(word, ladder.moves + 1, ladder.history + " " + word);
                    wordInfo.calcPriority(b);

                    if(word.equals(b)){
                        done = true;
                        System.out.println(wordInfo + " total enqueues: " + enqueues);
                        System.out.println();
                        break;
                    }
                    else{
                        wordTree.insert(wordInfo);
                        enqueues++;
                        this.blackList.add(new BlackWord(word, wordInfo.moves));
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
        this.blackList = new ArrayList<BlackWord>();
    }

    public void playBrute(String a, String b) {
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
                if(diffByOne(word, ladder.word) && !isBruteBlackListed(word)){

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
                        this.bruteBlackList.add(word);
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
        this.bruteBlackList = new ArrayList<String>();
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

    private boolean isBlackListed(String word, int moves){
        for(BlackWord w : this.blackList){
            if (w.word.equals(word)){
                return moves >= w.whenUsed;
            }
        }
        return false;
    }

    private boolean isBruteBlackListed(String a){
        return this.bruteBlackList.contains(a);
    }

}
