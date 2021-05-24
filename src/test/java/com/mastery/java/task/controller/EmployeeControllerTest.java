package com.mastery.java.task.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import com.mastery.java.task.rest.EmployeeController;
import com.mastery.java.task.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService serviceMock;

    private final Employee testEmployee = new Employee();

    public void initTestEmployee(){
        testEmployee.setEmployeeId(101);
        testEmployee.setFirstName("Test");
        testEmployee.setLastName("Test");
        testEmployee.setDepartmentId((short) 2);
        testEmployee.setJobTitle("Test");
        testEmployee.setGender(Gender.Male);
        testEmployee.setDateOfBirth(new Date());
    }

    @Test
    public void shouldListAllUsersWhenGetMethodCalled() throws Exception {

        initTestEmployee();

        List<Employee> employeeList = Arrays.asList(testEmployee);

        when(serviceMock.getAllEmployees()).thenReturn(employeeList);

        mvc.perform(get("/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName", is(testEmployee.getFirstName())));
    }

    @Test
    public void shouldListUserByIdWhenGetMethodCalled() throws Exception {

        initTestEmployee();

        int id = testEmployee.getEmployeeId();

        when(serviceMock.getEmployee(id)).thenReturn(testEmployee);

        mvc.perform(get("/employees/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(testEmployee.getFirstName())));
    }

    @Test
    public void shouldThrowExceptionIfEmployeeNotFound() throws Exception {

        initTestEmployee();

        int id = testEmployee.getEmployeeId();

        Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee is not found with ID: " + id))
                .when(serviceMock).getEmployee(id);
        mvc.perform(get("/employees/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateUserWhenPostMethodCalled() throws Exception {



        when(serviceMock.createEmployee((ArgumentMatchers.any(Employee.class)))).thenReturn(testEmployee);

        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(post("/employees/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(testEmployee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(testEmployee.getFirstName())));
    }

    @Test
    public void shouldUpdateEmployeeByIdWhenPutMethodCalled() throws Exception {

        initTestEmployee();

        int id = testEmployee.getEmployeeId();

        when(serviceMock.updateEmployee(eq(id), ArgumentMatchers.any(Employee.class))).thenReturn(testEmployee);

        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(put("/employees/" + id)
                .content(mapper.writeValueAsString(testEmployee))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(testEmployee.getFirstName())));
    }

    @Test
    public void shouldThrowExceptionIfEmployeeForUpdateNotFound() throws Exception {

        initTestEmployee();

        int id = testEmployee.getEmployeeId();

        Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee is not found with ID: " + id))
                .when(serviceMock).updateEmployee(eq(id), ArgumentMatchers.any(Employee.class));

        ObjectMapper mapper = new ObjectMapper();

        mvc.perform(put("/employees/" + id)
                .content(mapper.writeValueAsString(testEmployee))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void shouldDeleteEmployeeByIdWhenDeleteMethodCalled() throws Exception {

        initTestEmployee();

        int id = testEmployee.getEmployeeId();

        doNothing().when(serviceMock).deleteEmployee(id);
        mvc.perform(delete("/employees/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldThrowExceptionIfNoEmployeeToDelete() throws Exception {

        initTestEmployee();

        int id = testEmployee.getEmployeeId();

        Mockito.doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee is not found with ID: " + id))
                .when(serviceMock).deleteEmployee(id);

        mvc.perform(delete("/employees/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
