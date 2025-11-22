package Gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class EmployeeWriter {

    public static void main(String[] args) {
        // Example input
        String id = "123";
        String name = "John Doe";
        String address = "123 Main St";
        String salary = "50000";
        String dateOfHire = "2025-01-01";
        String dateOfBirth = "1990-05-15";
        String department = "IT";
        String role = "Developer";

        try {
            writeEmployeeToFile(id, name, address, salary, dateOfHire, dateOfBirth, department, role);
            System.out.println("Employee saved successfully!");
        } catch (IllegalArgumentException | FileNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void writeEmployeeToFile(String id, String name, String address, String salary,
                                           String dateOfHire, String dateOfBirth, String department, String role)
            throws FileNotFoundException {

        // Validate input
        if (id.isEmpty() || name.isEmpty() || address.isEmpty() || salary.isEmpty() ||
                dateOfHire.isEmpty() || dateOfBirth.isEmpty() || department.isEmpty() || role.isEmpty()) {
            throw new IllegalArgumentException("All fields must be provided!");
        }

        // Create the file
        File file = new File("employees.txt");

        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("ID Name Address Salary Date-of-Hire Date-of-Birth Department Role");
            writer.printf("%s %s %s %s %s %s %s %s%n",
                    id, name, address, salary, dateOfHire, dateOfBirth, department, role);
        }
    }
}

