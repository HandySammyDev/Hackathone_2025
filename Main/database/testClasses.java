package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class testClasses {
    public static void main(String args[]) {
        testUpdateTasks_Constructor1_Success();
    }

    public static void testUpdateTasks_Constructor1_Success() {
        System.out.println("--->testUpdateTasks_Constructor1_Success");
        Employee e = CreateEmployee1Task();

        HashMap<String, Task> tasksMap = e.getTasks();
        System.out.println(tasksMap.values() + " " + tasksMap.keySet());
        Task prevTask = e.getTask("f");

        System.out.println("Exepcted currentStatus before update: 0, Actual: " + prevTask.getCurrentStatus());

        boolean isSuccess = e.updateTask(prevTask, "3");
        Task newTask = e.getTask("f");
        System.out.println("Value of isSuccess: " + isSuccess);
        System.out.println("Exepcted currentStatus after update, 2 Actual: " + newTask.getCurrentStatus());

        System.out.println("Was task updated: " + e.updateTask(newTask));

        System.out.println("Was task updated: " + e.updateTask(newTask, 1));
        System.out.println("Exepcted currentStatus after update, 1 Actual: " + newTask.getCurrentStatus());

        System.out.println(newTask.serialize());



    }

    public static Employee CreateEmployee1Task() {
        Employee e = new Employee(1, 1.0, "BOB", "4177 Seckinger Road");

        ArrayList<String> taskStatuses = new ArrayList<>();
        taskStatuses.add("1");
        taskStatuses.add("2");
        taskStatuses.add("3");

        Task t = new Task("f", taskStatuses);
        e.addTask(t);

        return e;

    }
}
