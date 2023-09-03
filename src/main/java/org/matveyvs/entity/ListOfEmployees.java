package org.matveyvs.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ListOfEmployees {
    private String info;
    private List<Employee> employeeList;

    public ListOfEmployees(@JsonProperty("info") String info, @JsonProperty("employees") List<Employee> employeeList) {
        this.info = info;
        this.employeeList = employeeList;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    @Override
    public String toString() {
        return "ListOfEmployees{" +
                "info='" + info + '\'' +
                ", employeeList=" + employeeList +
                '}';
    }
}


