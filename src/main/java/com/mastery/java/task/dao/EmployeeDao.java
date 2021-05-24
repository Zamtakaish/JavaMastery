package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee is not found with ID: " + id);
        }
        return employee.get();
    }

    public Employee createEmployee(Employee employee){
        return repository.save(employee);
    }

    public Employee updateEmployee(int id, Employee employee){
        isEmployeeExist(id);
        return repository.save(employee);
    }

    public void deleteEmployee(int id){
        isEmployeeExist(id);
        repository.deleteById(id);
    }

    private void isEmployeeExist(int id) {
        if (repository.findById(id).isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee is not found with ID: " + id);
        }
    }

}
