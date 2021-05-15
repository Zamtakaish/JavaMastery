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

    public void getEmployee(int id){
        employeeDao.getEmployee(id);
    }

    public void createEmployee(Employee employee){
        employeeDao.createEmployee(employee);
    }

    public void updateEmployee(int id, Employee employee){
        employeeDao.updateEmployee(id, employee);
    }

    public void deleteEmployee(int id){
        employeeDao.deleteEmployee(id);
    }
}
