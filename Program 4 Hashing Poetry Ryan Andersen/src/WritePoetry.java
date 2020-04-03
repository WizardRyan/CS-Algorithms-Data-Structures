import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class WritePoetry {

    public String WritePoem(String file, String word, int poemLength, boolean shouldPrintHashTable){
        HashTable<String, WordFreqInfo> table = getHashTable(file);
        String poem = "-----------------Poem for " + file + "----------------\n";
        for(int i = 0; i < poemLength; i++){
            poem += word + " ";
            try{
                WordFreqInfo winfo = table.find(word);
                word = winfo.calcNextWord();
            }
            catch(Exception e){
                System.out.println(e + " word: " + word);
            }
        }
        if(shouldPrintHashTable){
            System.out.println("\n-----------Hash Table For " + file + "-------------");
            System.out.println(table);
        }
        return poem + "\n";
    }

    private HashTable<String, WordFreqInfo> getHashTable(String file){
        HashTable<String, WordFreqInfo> table = new HashTable<>();
        ArrayList<String> words = readFile(file);
            for(int i = 0; i < words.size(); i++){
                String word = words.get(i);
                WordFreqInfo winfo;
                if(!table.contains(word)){
                    winfo = new WordFreqInfo(word, 0);
                    table.insert(word, winfo);
                }
                else{
                    winfo = table.find(word);
                }
                winfo.occurCt++;
                if(i + 1 < words.size()){
                    winfo.updateFollows(words.get(i + 1));
                }
                else{
                    //we're at the end of the poem, wrap around
                    winfo.updateFollows(words.get(0));
                }
        }
        return table;
    }

    private ArrayList<String> readFile(String file){
        ArrayList<String> allWords = new ArrayList<>();
        try{
            Scanner scanner = new Scanner(new File(file));
            while(scanner.hasNextLine()){
                String line = scanner.nextLine() + " ";
                String[] words = line.replaceAll("[^a-zA-Z ]", "").toLowerCase().split(" ");
                allWords.addAll(Arrays.asList(words));
            }
        }
        catch(Exception e){
            System.out.println("File \"" + file + "\" not found!");
        }
        return allWords;
    }

}
