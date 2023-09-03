package org.matveyvs.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {
    private String id;
    private String name;
    private Integer salary;
    private Integer tax;

    public Employee(@JsonProperty("id") String id, @JsonProperty("name") String name,
                    @JsonProperty("salary") Integer salary, @JsonProperty("tax") Integer tax) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.tax = tax;
    }

    public Integer getSalary() {
        return salary;
    }

    public Integer getTax() {
        return tax;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", tax=" + tax +
                '}';
    }
}

