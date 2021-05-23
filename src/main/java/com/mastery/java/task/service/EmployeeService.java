package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;

    public List<Employee> getAllEmployees(){
        return employeeDao.getAllEmployees();
    }

    public Employee getEmployee(int id){
        return employeeDao.getEmployee(id);
    }

    public Employee createEmployee(Employee employee){
        return employeeDao.createEmployee(employee);
    }

    public Employee updateEmployee(int id, Employee employee){
        return employeeDao.updateEmployee(id, employee);
    }

    public void deleteEmployee(int id){
        employeeDao.deleteEmployee(id);
    }
}
