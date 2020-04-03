public class WordInfo implements Comparable<WordInfo> {
    public String word;
    public int moves;
    public String history;
    public int priority;

    public WordInfo(String word, int moves, String history){
        this.word = word;
        this.moves = moves;
        this.history = history;
    }

    public String toString(){
        return "Word " + word    + " Moves " +moves  + " History ["+history +"]";
    }

    public void calcPriority(String b){
        int count = 0;
        for(int i = 0; i < this.word.length(); i++){
            if(b.charAt(i) != this.word.charAt(i)){
                count++;
            }
        }
        count += this.moves;
        this.priority = count;
    }

    @Override
    public int compareTo(WordInfo o) {
        if(this.priority < o.priority){
            return -1;
        }
        if(this.priority == o.priority){
            return 0;
        }
        return 1;
    }
}

