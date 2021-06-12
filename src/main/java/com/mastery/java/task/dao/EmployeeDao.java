package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.dto.Gender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeDao {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public List<Employee> getAllEmployees(){

        List<Employee> employeeList = new ArrayList<>();

        try{
            connect();
            resultSet = statement.executeQuery("SELECT * FROM employee");
            while (resultSet.next()){
                Employee employee = new Employee();
                setEmployee(employee);
                employeeList.add(employee);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to connect to database.");
        }
        finally {
            close(connection, statement, resultSet);
        }
        return employeeList;
    }

    public Optional<Employee> getEmployee(int id){

        Employee employee = null;

        try{
            connect();
            resultSet = statement.executeQuery("SELECT * FROM employee WHERE employee_id = " + id);
            if (resultSet.next()){
                employee = new Employee();
                setEmployee(employee);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to connect to database.");
        }
        finally {
            close(connection, statement, resultSet);
        }
        return Optional.ofNullable(employee);
    }

    public Employee createEmployee(Employee employee){

        try{
            connect();
            statement.executeUpdate("INSERT INTO employee " +
                    "VALUES " +
                    "(DEFAULT, " +
                    "'" + employee.getFirstName() + "', " +
                    "'" + employee.getLastName() + "', " +
                    employee.getDepartmentId() + ", " +
                    "'" + employee.getJobTitle() + "', " +
                    "'" + employee.getGender() + "', " +
                    "'" + employee.getDateOfBirth() + "')",
                    statement.RETURN_GENERATED_KEYS);

            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()){
                employee.setEmployeeId((int) resultSet.getLong(1));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to connect to database.");
        }
        finally {
            close(connection, statement, resultSet);
        }
        return employee;
    }

    public int updateEmployee(int id, Employee employee){

        int status;

        try{
            connect();
            status = statement.executeUpdate("UPDATE employee " +
                    "SET first_name = '" + employee.getFirstName() + "', " +
                    "last_name = '" + employee.getLastName() + "', " +
                    "department_id = " + employee.getDepartmentId() + ", " +
                    "job_title = '" + employee.getJobTitle() + "', " +
                    "gender = '" + employee.getGender() + "', " +
                    "date_of_birth = '" + employee.getDateOfBirth() + "' "+
                    "WHERE employee_id = " + id);
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to connect to database.");
        }
        finally {
            close(connection, statement, resultSet);
        }
        return status;
    }

    public int deleteEmployee(int id){

        int status;

        try{
            connect();
            status = statement.executeUpdate("DELETE FROM employee " +
                    "WHERE employee_id = " + id);
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to connect to database.");
        }
        finally {
            close(connection, statement, resultSet);
        }
        return status;
    }

    private void connect() throws SQLException {
        connection = DriverManager.getConnection(url, username, password);
        statement = connection.createStatement();
    }

    private void setEmployee(Employee employee) throws SQLException {
        employee.setEmployeeId(resultSet.getInt("employee_id"));
        employee.setFirstName(resultSet.getString("first_name"));
        employee.setLastName(resultSet.getString("last_name"));
        employee.setDepartmentId(resultSet.getShort("department_id"));
        employee.setJobTitle(resultSet.getString("job_title"));
        employee.setGender(Gender.valueOf(resultSet.getString("gender")));
        employee.setDateOfBirth(resultSet.getDate("date_of_birth"));
    }

    private void close (Connection connection, Statement statement, ResultSet resultSet){
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {e.printStackTrace();}
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {e.printStackTrace();}
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {e.printStackTrace();}
        }
    }
}
