package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeDao employeeDao;


    public List<Employee> getAllEmployees(){
        return employeeDao.getAllEmployees();
    }

    public Employee getEmployee(int id){

        Optional<Employee> employee = employeeDao.getEmployee(id);

        if (employee.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee is not found with ID: " + id);
        }
        return employee.get();
    }

    public Employee createEmployee(Employee employee){
        if(employee.getFirstName() == null || employee.getLastName() == null || employee.getDepartmentId() == null
                || employee.getJobTitle() == null || employee.getGender() == null || employee.getDateOfBirth() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The format of provided employee is invalid.");
        }
        return employeeDao.createEmployee(employee);
    }

    public void updateEmployee(int id, Employee employee){

        if(employee.getFirstName() == null || employee.getLastName() == null || employee.getDepartmentId() == null
                || employee.getJobTitle() == null || employee.getGender() == null || employee.getDateOfBirth() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The format of provided employee is invalid.");
        }
        if (employeeDao.updateEmployee(id, employee) == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee is not found with ID: " + id);
        }
    }

    public void deleteEmployee(int id){

        if (employeeDao.deleteEmployee(id) == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee is not found with ID: " + id);
        }
    }
}
