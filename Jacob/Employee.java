package main;


import java.util.HashMap;
import java.util.HashSet;

public class Employee {
    //Essential Fields
    private int id;
    private double salary;
    private String name;
    private String adress;

    //Unescessary Fields
    private String dateOfHire;
    private String dateOfBirth;
    private String department;
    private String role;

    //Lists contained by Employee
    private HashMap<String, Task> tasks = new HashMap<>();
    private HashSet<Group> groupReferences = new HashSet<>();

    //Constructors

    public Employee(int id, double salary, String name, String adress) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.adress = adress;
    }

    public Employee(int id, double salary, String name, String adress,
                     String dateOfHire, String dateOfBirth, String department, String role, int userLevel) {

        this(id, salary, name, adress);
        this.dateOfHire = dateOfHire;
        this.dateOfBirth = dateOfBirth;
        this.department = department;
        this.role = role;

    }

    //getters


    public int getId() {
        return id;
    }

    public double getSalary() {
        return salary;
    }

    public String getName() {
        return name;
    }

    public String getAdress() {
        return adress;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getDateOfHire() {
        return dateOfHire;
    }

    public String getDepartment() {
        return department;
    }

    public String getRole() {
        return role;
    }

    public HashMap<String, Task> getTasks() {
        return tasks;
    }

    public Task getTask(String name) {

        return tasks.get(name);

    }

    public HashSet<Group> getGroupReferences() {
        return groupReferences;
    }

    //adds taks
    public boolean addTask(Task t) {
        if(tasks.containsValue(t)) {
            return false;
        }

        tasks.put(t.getName(), t);
        return true;
    }
    //update task, returns a boolean if occured

    public boolean updateTask(Task task, String status) {

        if(!tasks.containsValue(task)) {
            return false;
        }

        if(!task.isStatusPresent(status)) {
            return false;
        }

        task.updateStatus(status);

        tasks.put(task.getName(), task);
        return true;
    }

    public boolean updateTask(Task task) {
        if(!tasks.containsValue(task)) {
            return false;
        }

        return task.updateStatus();
    }

    public boolean updateTask(Task task, int i) {
        if(!tasks.containsValue(task)) {
            return false;
        }

        return task.updateStatus(i);
    }

    //removal and addition of groups and tasks

    public boolean addGroup(Group g) {
        return groupReferences.add(g);
    }

    public boolean removeGroup(Group g) {
        if(!groupReferences.contains(g)) {
            return false;
        }
        groupReferences.remove(g);
        return true;
    }

    protected void removeTask(Task t) {
        tasks.remove(t.getName());
    }

    //


}
