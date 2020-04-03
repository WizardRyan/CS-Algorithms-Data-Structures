public class Task3 extends Task{
    public Task3(int ID, int start, int deadline, int duration) {
        super(ID,start,deadline,duration);
    }

    //duration then deadline if tie
    @Override
    public int compareTo(Task t2) {
        int comp = duration - t2.duration;
        if(comp == 0){
            return deadline-t2.deadline;
        }
        else{
            return comp;
        }
    }

}
