package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EmployeeDao {

    @Autowired
    private EmployeeRepository repository;

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee getEmployee(int id) {
        Optional<Employee> employee = repository.findById(id);
        if (employee.isEmpty()){
            throw new RuntimeException("Employee is not found with ID: " + id);
        }
        return employee.get();
    }

    public void createEmployee(Employee employee){
        repository.save(employee);
    }

    public void updateEmployee(int id, Employee employee){
        repository.save(employee);
    }

    public void deleteEmployee(int id){
        repository.deleteById(id);
    }
}
