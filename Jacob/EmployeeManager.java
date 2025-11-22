package main;

import java.util.HashMap;
import java.util.HashSet;

public class EmployeeManager {
    HashMap<String, UserGroup> systemGroups = new HashMap<>();
    HashMap<String, Employee> systemEmployees = new HashMap<>();
    HashMap<String, Task> currentTasks = new HashMap<>();
    //adders

    public void addUserGroup(UserGroup g) {
        systemGroups.put(g.groupName, g);
    }

    public void addEmployee(Employee e) {
        systemEmployees.put(e.getName(), e);
    }

    public boolean checkViability(HashSet<UserGroup> ugs) {
        for(UserGroup g: ugs) {
            if(g.getGroupLevel() == 1) {
                return true;
            }
        }

        return false;
    }

    public boolean createCurrentTask(Task t) {
        currentTasks.put(t.getName(), t);
        return true;
    }

    public HashMap<String, Employee> getSystemEmployees() {
        return systemEmployees;
    }

    public HashMap<String, Task> getCurrentTasks() {
        return currentTasks;
    }

    public HashMap<String, UserGroup> getSystemGroups() {
        return systemGroups;
    }

    public boolean addTaskCustomStatusesEmployee(Employee user, Employee e, Task t) {
        if(!systemEmployees.containsValue(user)) {
            return false;
        }

        if(!systemEmployees.containsValue(e)) {
            return false;
        }

        HashSet<UserGroup> mainGroups = user.getGroupReferences();
        if(!checkViability(mainGroups)) {
            return false;
        }

        return e.addTask(t);
    }

    public boolean addTaskCustomStatusesGroup(Employee user, UserGroup g, Task t) {
        if(!systemEmployees.containsValue(user)) {
            return false;
        }

        if(!systemGroups.containsValue(g)) {
            return false;
        }

        HashSet<UserGroup> mainGroups = user.getGroupReferences();
        if(!checkViability(mainGroups)) {
            return false;
        }

        g.addTask(t);

        return true;
    }

    public Boolean removeTask(Employee user, Employee e, Task t) {
        if(!systemEmployees.containsValue(user)) {
            return false;
        }

        if(!systemEmployees.containsValue(e)) {
            return false;
        }

        HashSet<UserGroup> mainGroups = user.getGroupReferences();
        if(!checkViability(mainGroups)) {
            return false;
        }

        e.removeTask(t);
        return true;
    }

    public Boolean removeTaskGroup(Employee user, UserGroup g, Task t) {
        if(!systemEmployees.containsValue(user)) {
            return false;
        }

        if(!systemEmployees.containsValue(g)) {
            return false;
        }

        HashSet<UserGroup> mainGroups = user.getGroupReferences();
        if(!checkViability(mainGroups)) {
            return false;
        }

        g.removeTask(t);
        return true;
    }

    public Boolean createGroup(Employee user, UserGroup g) {
        if(!systemEmployees.containsValue(user)) {
            return false;
        }

        if(!systemGroups.containsValue(g)) {
            return false;
        }

        HashSet<UserGroup> mainGroups = user.getGroupReferences();
        if(!checkViability(mainGroups)) {
            return false;
        }

        systemGroups.put(g.groupName, g);

        for(Employee e: g.getEmployees()) {
            e.addGroup(g);
        }

        return true;
    }

    public boolean createEmployee(Employee user, Employee e) {
        if(!systemEmployees.containsValue(user)) {
            return false;
        }

        HashSet<UserGroup> mainGroups = user.getGroupReferences();
        if(!checkViability(mainGroups)) {
            return false;
        }

        systemEmployees.put(e.getName(), e);

        return true;
    }

    public boolean removeEmployee(Employee user, Employee e) {
        if(!systemEmployees.containsValue(user)) {
            return false;
        }

        if(!systemEmployees.containsValue(e)) {
            return false;
        }

        HashSet<UserGroup> mainGroups = user.getGroupReferences();
        if(!checkViability(mainGroups)) {
            return false;
        }

        systemEmployees.remove(e.getName());
        return true;
    }

    public boolean removeGroup(Employee user, UserGroup g) {
        if(!systemEmployees.containsValue(user)) {
            return false;
        }

        if(!systemGroups.containsValue(g)) {
            return false;
        }

        HashSet<UserGroup> mainGroups = user.getGroupReferences();
        if(!checkViability(mainGroups)) {
            return false;
        }

        systemGroups.remove(g.groupName, g);

        for(Employee e: g.getEmployees()) {
            e.removeGroup(g);
        }

        return true;
    }

}
