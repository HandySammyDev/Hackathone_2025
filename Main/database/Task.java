package main;

import java.util.ArrayList;

public class Task {

    private String name;
    private ArrayList<String> statuses = new ArrayList<>();
    private int statusAmount;
    private int currentStatus;


    public Task(String name, ArrayList<String> statuses){
        this(name);
        this.statuses = statuses;
        statusAmount = statuses.size();
        currentStatus = 0;
    }

    public Task(String name){
        this.name = name;
        statuses.add("Open");
        statuses.add("In-Progress");
        statuses.add("Complete");
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

        for(i = 0; i < statusAmount; ++i) {

            if(statusName.equals(statuses.get(i))) {
                currentStatus = i;
                return true;
            }
        }
        return false;
    }

    protected boolean updateStatus() {


        if(currentStatus >= statusAmount - 1) {
            return false;
        }

        currentStatus += 1;

        return true;
    }

    protected boolean updateStatus(int i) {


        if((i < 0) || (i >= this.statusAmount - 1)) {
            return false;
        }

        currentStatus = i;
        return true;
    }

    //Serialize

    public String serialize() {
        int i;
        String cereal = "Name:" + name;
        cereal += "," + "statusAmount:" + statusAmount;
        cereal += "," + "currentStatus:" + currentStatus + ",";
        cereal += "statuses:";

        for(i = 0; i < statusAmount; ++i) {
            if(i == statusAmount - 1) {
                cereal += statuses.get(i);
            }
            else {
                cereal +=  statuses.get(i) + "*";
            }
        }

        return cereal;
    }

    public static class TaskDecoder implements ObjDecoder<Task>
    {
        public Task deserialize(String data)
        {
            String[] split = data.split(",(?=(?:[^{}]*\\{[^{}]*\\})*[^{}]*$)");
            String name = null;
            ArrayList<String> statuses = new ArrayList<>();
            int statusAmount=-1;
            int currentStatus = 0;
            for(String str : split)
            {


                String[] args = str.split(":");
                if(args[0].equals("name"))
                    name = args[1];
                else if(args[0].equals("statusAmount"))
                    statusAmount = Integer.parseInt(args[1]);
                else if(args[0].equals("currentStatus"))
                    currentStatus = Integer.parseInt(args[1]);
                else if(args[0].equals("statuses"))
                {
                    for(String stat : args[1].split("\\*"))
                    {
                        statuses.add(stat);
                    }
                }
            }
            return new Task(name,statuses);
        }

    }


}
