package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeDaoTest {

    @Mock
    private EmployeeRepository repositoryMock;
    @InjectMocks
    private EmployeeDao employeeDao;

    private final Employee testEmployee = new Employee();

    private void initTestEmployee(){
        testEmployee.setEmployeeId(101);
        testEmployee.setFirstName("Test");
        testEmployee.setLastName("Test");
        testEmployee.setDepartmentId((short) 2);
        testEmployee.setJobTitle("Test");
        testEmployee.setGender(Gender.Male);
        testEmployee.setDateOfBirth(new Date());
    }


    @Test
    public void shouldReturnAllEmployees(){
        initTestEmployee();

        List<Employee> employeeList = Arrays.asList(testEmployee);

        when(repositoryMock.findAll()).thenReturn(employeeList);
        Assertions.assertEquals(employeeList, employeeDao.getAllEmployees());
        verify(repositoryMock).findAll();
    }

    @Test
    public void shouldReturnEmployeeIfFound(){

        initTestEmployee();

        when(repositoryMock.findById(testEmployee.getEmployeeId())).thenReturn(Optional.of(testEmployee));
        employeeDao.getEmployee(testEmployee.getEmployeeId());
        verify(repositoryMock).findById(testEmployee.getEmployeeId());
    }

    @Test (expected = ResponseStatusException.class)
    public void shouldThrowExceptionIfEmployeeNotFound() throws ResponseStatusException{

        initTestEmployee();


        when(repositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        employeeDao.getEmployee(testEmployee.getEmployeeId());

    }

    @Test
    public void shouldReturnEmployeeIfEmployeeCreated(){

        initTestEmployee();

        when(repositoryMock.save(ArgumentMatchers.any(Employee.class))).thenReturn(testEmployee);
        employeeDao.createEmployee(testEmployee);
        verify(repositoryMock).save(testEmployee);
    }

    @Test
    public void shouldUpdateEmployeeIfFound(){

        initTestEmployee();

        Employee newTestEmployee = testEmployee;
        newTestEmployee.setFirstName("Alan");

        when(repositoryMock.findById(testEmployee.getEmployeeId())).thenReturn(Optional.of(testEmployee));
        employeeDao.updateEmployee(testEmployee.getEmployeeId(), newTestEmployee);
        verify(repositoryMock).save(newTestEmployee);
        verify(repositoryMock).findById(testEmployee.getEmployeeId());
    }

    @Test (expected = ResponseStatusException.class)
    public void shouldThrowExceptionIfEmployeeForUpdateNotFound() throws ResponseStatusException{

        initTestEmployee();

        Employee newTestEmployee = testEmployee;
        newTestEmployee.setFirstName("Alan");

        when(repositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        employeeDao.updateEmployee(testEmployee.getEmployeeId(), newTestEmployee);

    }

    @Test
    public void shouldDeleteEmployeeIfFound(){

        initTestEmployee();

        when(repositoryMock.findById(testEmployee.getEmployeeId())).thenReturn(Optional.of(testEmployee));
        employeeDao.deleteEmployee(testEmployee.getEmployeeId());
        verify(repositoryMock).deleteById(testEmployee.getEmployeeId());
    }

    @Test (expected = ResponseStatusException.class)
    public void shouldThrowExceptionIfNoEmployeeToDelete() throws ResponseStatusException{

        initTestEmployee();

        when(repositoryMock.findById(anyInt())).thenReturn(Optional.ofNullable(null));
        employeeDao.deleteEmployee(testEmployee.getEmployeeId());

    }
}
