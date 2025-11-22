package database;


import java.util.HashMap;
import java.util.HashSet;

public class Employee implements ObjEncodable {
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
    private HashSet<UserGroup> groupReferences = new HashSet<>();

    //Constructors

    public Employee(int id, double salary, String name, String adress) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.adress = adress;
    }

    public Employee(int id, double salary, String name, String adress,
                     String dateOfHire, String dateOfBirth, String department, String role) {

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

    public HashSet<UserGroup> getGroupReferences() {
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

    public boolean addGroup(UserGroup g) {
        return groupReferences.add(g);
    }

    public boolean removeGroup(UserGroup g) {
        if(!groupReferences.contains(g)) {
            return false;
        }
        groupReferences.remove(g);
        return true;
    }

    protected void removeTask(Task t) {
        tasks.remove(t.getName());
    }

    @Override
    public String serialize() {
        String inf =  String.format("id:%d,salary:%.2f,name:%s,address:%s,dateofhire:%s,dateofbirth:%s,department:%s,role:%s,",id,salary,name,adress,dateOfHire,dateOfBirth,department,role);
        StringBuilder taskList = new StringBuilder("tasks:{");
        for(Map.Entry<String,Task> entry: tasks.entrySet())
        {
            taskList.append(entry.getKey()+":{"+entry.getValue().serialize()+"},");
        }
        inf+="}"+taskList.substring(0,taskList.length()-1);
        return inf;
    }

    public static class EmployeeDecoder implements ObjDecoder<Employee>
    {
        public Employee deserialize(String data)
        {
            String[] vars = data.split(",(?=(?:[^{}]*\\{[^{}]*\\})*[^{}]*$)");
            int id=-1;
            double salary=-1;
            String name=null;
            String address=null;
            String dateofhire=null;
            String dateofbirth=null;
            String department=null;
            String role=null;
            HashMap<String,Task> tasks = new HashMap<>();

            for(String text : vars)
            {
                String[] splitted = text.split(":(?=(?:[^{}]*\\{[^{}]*\\})*[^{}]*$)");
                if(splitted[0].equals("name"))
                {
                    name = splitted[1];
                }
                if(splitted[0].equals("id"))
                {
                    id = Integer.parseInt(splitted[1]);
                }
                else if(splitted[0].equals("salary"))
                {
                    salary = Double.parseDouble(splitted[1]);
                }
                else if(splitted[0].equals("address"))
                {
                    address = splitted[1];
                }
                else if(splitted[0].equals("dateofhire"))
                {
                    dateofhire = splitted[1];
                }
                else if(splitted[0].equals("dateofbirth"))
                {
                    dateofbirth = splitted[1];
                }
                else if(splitted[0].equals("department"))
                {
                    department = splitted[1];
                }
                else if(splitted[0].equals("role"))
                {
                    role = splitted[1];
                }
                else if(splitted[0].equals("tasks"))
                {
                    for(String str : splitted[1].substring(0,splitted[1].length()-1).split(",(?=(?:[^{}]*\\{[^{}]*\\})*[^{}]*$)"))
                    {
                        String[] keyTask = str.split(":(?=(?:[^{}]*\\{[^{}]*\\})*[^{}]*$)");
                        String key = keyTask[0];
                        Task t = (new TaskDecoder()).deserialize(keyTask[1].substring(0,keyTask[1].length()-1));
                        tasks.put(key,t);
                    }
                }

            }
            return new Employee(id,salary,name,address,dateofhire,dateofbirth,department,role,tasks);
        }
    }



}
