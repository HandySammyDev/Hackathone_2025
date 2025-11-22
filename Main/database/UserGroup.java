package database;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class UserGroup implements ObjEncodable
{
    private Database<Employee> loginDB;
    String groupName;
    private int groupLevel = 0;
    private HashSet<Employee> employees = new HashSet<>();
    private HashMap<String, Task> taskMap;

    public UserGroup(Database<Employee> loginDB, String groupName, int grouplevel,HashSet<Employee> emps, HashMap<String,Task> taskMap)
    {
        this.loginDB = loginDB;
        this.groupLevel = grouplevel;
        this.employees = emps;
        this.taskMap = taskMap;
        this.groupName = groupName;
    }

    public void addTask(Task t) {
        taskMap.put(t.getName(), t);
    }
    public void addEmployee(Employee e)
    {
        employees.add(e);
        e.addGroup(this);
    }

    public HashSet<Employee> getEmployees() {
        return employees;
    }

    public void removeEmployee(Employee e)
    {
        employees.remove(e);
        e.removeGroup(this);
    }

    public void removeTask(Task t)
    {
        taskMap.remove(t.getName());
    }

    public boolean updateTask(String taskName, String status)
    {
        Task t = taskMap.get(taskName);
        if(t==null)
            return false;
        return t.updateStatus(status);
    }

    public boolean updateTask(String taskName)
    {
        Task t = taskMap.get(taskName);
        if(t==null)
            return false;
        return t.updateStatus();
    }

    public int getGroupLevel() {
        return groupLevel;
    }

     public String serialize()
    {
        String baseInf = String.format("groupName:%s,groupLevel:%d,",groupName,groupLevel);
        StringBuilder emps = new StringBuilder("employees:");
        for(Employee e : employees) {
            emps.append(loginDB.getUsername(e)).append("*");
        }
        baseInf+=emps.substring(0,emps.length()-1)+",";

        StringBuilder taskList = new StringBuilder("tasks:");
        for(Map.Entry<String,Task> entry: taskMap.entrySet())
        {
            taskList.append(entry.getKey()+":{"+entry.getValue().serialize()+"},");
        }
        baseInf+=taskList.substring(0,taskList.length()-1);
        return baseInf;
    }

    class UserGroupDecoder implements ObjDecoder<UserGroup>
    {
        public UserGroup deserialize(String serialized) {
            // Initialize variables
            String groupName = null;
            int groupLevel = 0;
            HashSet<Employee> employees = new HashSet<>();
            HashMap<String, Task> taskMap = new HashMap<>();

            // Split the serialized string by commas
            String[] parts = serialized.split(",");

            // Extract group name and group level
            for (String part : parts) {
                if (part.startsWith("groupName:")) {
                    groupName = part.split(":")[1];
                } else if (part.startsWith("groupLevel:")) {
                    groupLevel = Integer.parseInt(part.split(":")[1]);
                } else if (part.startsWith("employees:")) {
                    // Extract employee usernames
                    String employeeData = part.substring("employees:".length());
                    String[] employeeUsernames = employeeData.split("\\*");
                    // In a real implementation, we'd look up employees in the DB by their usernames
                    for (String username : employeeUsernames) {
                        // Here, we'll assume that we have a way to get Employee objects by username.
                        // Since loginDB is null, you could either mock this or assume Employee objects are passed.
                        // We will leave this part empty for now and assume Employee constructor is public.
                        employees.add(loginDB.getUserFromUsername(username)); // This is a placeholder
                    }
                } else if (part.startsWith("tasks:")) {
                    // Extract tasks
                    String taskData = part.substring("tasks:".length());
                    String[] taskEntries = taskData.split("},");
                    for (String taskEntry : taskEntries) {
                        if (taskEntry.endsWith("}")) {
                            taskEntry = taskEntry.substring(0, taskEntry.length() - 1); // Remove last }
                        }
                        String[] taskParts = taskEntry.split(":\\{");
                        if (taskParts.length == 2) {
                            String taskName = taskParts[0];
                            String taskSerialization = taskParts[1];
                            // Deserialize the task object (assuming Task has a `deserialize` method)
                            Task task = (new Task.TaskDecoder()).deserialize(taskSerialization);
                            taskMap.put(taskName, task);
                        }
                    }
                }
            }

            // Return the reconstructed UserGroup object
            return new UserGroup(null, groupName, groupLevel, employees, taskMap); // loginDB is null as per your request
    }}




}
