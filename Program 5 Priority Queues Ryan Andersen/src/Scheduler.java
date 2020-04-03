import java.util.ArrayList;

public class Scheduler {

    public void makeSchedule(String type, ArrayList<Task> tasks){
        MinHeap<Task> schedule = new MinHeap<>();
        //split each task into 1 unit chunks and push onto schedule
        for(int i = 0; i < tasks.size(); i++){
            for(int j = 0; j < tasks.get(i).duration; j++){
                if(tasks.get(i) instanceof Task1){
                    schedule.insert(new Task1(tasks.get(i).ID, tasks.get(i).start, tasks.get(i).deadline, 1));
                }
                if(tasks.get(i) instanceof Task2){
                    schedule.insert(new Task2(tasks.get(i).ID, tasks.get(i).start, tasks.get(i).deadline, 1));
                }
                if(tasks.get(i) instanceof Task3){
                    schedule.insert(new Task3(tasks.get(i).ID, tasks.get(i).start, tasks.get(i).deadline, 1));
                }
            }
        }

        //pull from the schedule until it's empty while keeping track of what's late
        System.out.println(type);
        Task currentTask = schedule.deleteMin();
        int countNumLate = 0;
        int countTotalLate = 0;
        for(int currentTime = 1; currentTask != null; currentTime++){
            tasks.get(currentTask.ID - 1).duration -= 1;
            boolean taskCompleted = tasks.get(currentTask.ID - 1).duration == 0;
            boolean late = taskCompleted && tasks.get(currentTask.ID - 1).deadline < currentTime;
            if(taskCompleted && late){
                countNumLate++;
                countTotalLate +=  (currentTime - tasks.get(currentTask.ID - 1).deadline);
            }
            System.out.print("Time: " + currentTime + " Task: " + currentTask.ID  + (taskCompleted  ? (late ? " ** Late" : " **") : "") + "\n");
            currentTask = schedule.deleteMin();
        }
        System.out.println("Tasks late: " + countNumLate + " Total Late: " + countTotalLate + "\n");
    }
}
