package main;

import java.util.ArrayList;

public class Task {

    private String name;
    private ArrayList<String> statuses = new ArrayList<>();
    private int statusAmount;
    private int currentStatus = 0;
    private boolean isDone = false;

    public Task(String name, ArrayList<String> statuses){
        this.name = name;
        this.statuses = statuses;
        statusAmount = statuses.size();
    }

    public String getName() {
        return name;
    }

    public int getCurrentStatus() {
        return currentStatus;
    }

    public int getStatusAmount() {
        return statusAmount;
    }

    public ArrayList<String> getStatuses() {
        return statuses;
    }

    //Notes if status is contained

    public boolean isStatusPresent(String status) {
        if(!statuses.contains(status)) {
            return false;
        }
        else {
            return true;
        }
    }

    //methods for updating the status of a taks
    protected boolean updateStatus(String statusName) {
        int i;

        if(isDone == true) {
            return false;
        }

        for(i = 0; i < statusAmount; ++i) {
            if(statusName.equals(statuses.get(i))) {
                currentStatus = i;
                if(currentStatus == statusAmount - 1) {
                    isDone = true;
                    return true;

                }
                return true;
            }
        }
        return false;
    }

    protected boolean updateStatus() {

        if(isDone == true) {
            return false;
        }

        currentStatus += 1;

        if(currentStatus == statusAmount - 1) {
            isDone = true;
        }

        return true;
    }




}
