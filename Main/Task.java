import java.util.ArrayList;

public class Task {

    private ArrayList<String> statuses = new ArrayList<>();
    private int status;
    private boolean isDone;

    public Task(String name, ArrayList<String> statuses){
        this.statuses = statuses;
    }
}
