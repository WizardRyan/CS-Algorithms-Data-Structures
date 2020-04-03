import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TestSched {


    public static void readTasks(String filename, ArrayList<Task> task1, ArrayList<Task> task2, ArrayList<Task> task3) {
        File f = new File(filename);
        try {
            Scanner reader = new Scanner(f);
            int taskID  = 0;
            while(reader.hasNextLine()){
                String[] stringData = reader.nextLine().split("\\s+");
                int[] data = new int[stringData.length];
                for(int i = 0; i < stringData.length; i++){
                    data[i] = Integer.parseInt(stringData[i]);
                }
                taskID++;
                task1.add(new Task1(taskID, data[0], data[1], data[2]));
                task2.add(new Task2(taskID, data[0], data[1], data[2]));
                task3.add(new Task3(taskID, data[0], data[1], data[2]));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        Scheduler s = new Scheduler();
        MinHeap<Task> schedule = new MinHeap<>();
        String [] files = {"taskset1.txt","taskset2.txt","taskset3.txt","taskset4.txt","taskset5.txt" };
        for (String f : files) {
            ArrayList<Task> t1 = new ArrayList();    // elements are Task1
            ArrayList<Task> t2 = new ArrayList();    // elements are Task2
            ArrayList<Task> t3 = new ArrayList();    // elements are Task3
            readTasks(f, t1, t2, t3);
            s.makeSchedule("Deadline Priority "+ f, t1);
            s.makeSchedule("Startime Priority " + f, t2);
            s.makeSchedule("Wild and Crazy priority (it's just duration) " + f, t3);
       }

    }
}
