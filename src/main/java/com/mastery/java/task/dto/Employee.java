package com.mastery.java.task.dto;

import javax.persistence.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue
    private Integer employeeId;

    private String firstName;
    private String lastName;
    private Short departmentId;
    private String jobTitle;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String dateOfBirth;

    public Employee() {
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Short getDepartmentId() {
        return departmentId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Gender getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", departmentId=" + departmentId +
                ", jobTitle='" + jobTitle + '\'' +
                ", gender=" + gender +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                '}';
    }
}
