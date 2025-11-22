package main;

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
}
