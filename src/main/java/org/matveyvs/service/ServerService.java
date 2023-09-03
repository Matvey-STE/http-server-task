package org.matveyvs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.matveyvs.entity.Employee;
import org.matveyvs.entity.ListOfEmployees;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ServerService {
    public static Optional<ListOfEmployees> readFromJson(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        ListOfEmployees listOfEmployees;
        try {
            listOfEmployees = objectMapper.readValue(new File(path), ListOfEmployees.class);
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.ofNullable(listOfEmployees);
    }

    public static boolean removeFile(String path) {
        File fileToDelete = new File(path);
        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
                System.out.println("Temporary JSON file deleted successfully.");
                return true;
            } else {
                System.err.println("Failed to delete the file.");
            }
        } else {
            System.err.println("File does not exist.");
        }
        return false;
    }

    public static Integer getTotalIncome(List<Employee> listOfEmployee) {
        return listOfEmployee.stream().mapToInt(Employee::getSalary).sum();
    }

    public static Integer getTotalTax(List<Employee> listOfEmployee) {
        return listOfEmployee.stream().mapToInt(Employee::getTax).sum();
    }

    public static Integer getTotalProfit(List<Employee> listOfEmployee) {
        return getTotalIncome(listOfEmployee) - getTotalTax(listOfEmployee);
    }
}
