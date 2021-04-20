package com.openclassrooms.webapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
//permet d'éxécuter une requête HTTP et convertit json en Java :
import org.springframework.web.client.RestTemplate;

import com.openclassrooms.webapp.CustomProperties;
import com.openclassrooms.webapp.model.Employee;

import lombok.extern.slf4j.Slf4j;

// RestTemplate non seulement fait la requête à l’API mais en plus convertit le résultat JSON en objet Java

@Slf4j
@Component
public class EmployeeProxy {

    @Autowired
    private CustomProperties props; // récupère l'url de l'api

    /**
     * Get all employees
     * @return An iterable of all employees
     */
    public Iterable<Employee> getEmployees() {

        String baseApiUrl = props.getApiUrl();
        String getEmployeesUrl = baseApiUrl + "/employees";

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Employee>> response = restTemplate.exchange(
                getEmployeesUrl,
                HttpMethod.GET,
                null, // Null en lieu et place d’un objet HttpEntity, ainsi on laisse un comportement par défaut ;
                new ParameterizedTypeReference<Iterable<Employee>>() {}
                // ParameterizedTypeReference ni iterable sinon <Object>.class.
        );

        log.debug("Get Employees call " + response.getStatusCode().toString());

        return response.getBody();
    }

    /**
     * Get an employee by the id
     * @param id The id of the employee
     * @return The employee which matches the id
     */
    public Employee getEmployee(int id) {
        String baseApiUrl = props.getApiUrl();
        String getEmployeeUrl = baseApiUrl + "/employee/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Employee> response = restTemplate.exchange(
                getEmployeeUrl,
                HttpMethod.GET,
                null,
                Employee.class
        );

        log.debug("Get Employee call " + response.getStatusCode().toString());

        return response.getBody();
    }

    /**
     * Add a new employee
     * @param e A new employee (without an id)
     * @return The employee full filled (with an id)
     */
    public Employee createEmployee(Employee e) {
        String baseApiUrl = props.getApiUrl();
        String createEmployeeUrl = baseApiUrl + "/employee";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Employee> request = new HttpEntity<Employee>(e);
        ResponseEntity<Employee> response = restTemplate.exchange(
                createEmployeeUrl,
                HttpMethod.POST,
                request,
                Employee.class);

        log.debug("Create Employee call " + response.getStatusCode().toString());

        return response.getBody();
    }

    /**
     * Update an employee - using the PUT HTTP Method.
     * @param e Existing employee to update
     */
    public Employee updateEmployee(Employee e) {
        String baseApiUrl = props.getApiUrl();
        String updateEmployeeUrl = baseApiUrl + "/employee/" + e.getId();

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Employee> request = new HttpEntity<Employee>(e);
        ResponseEntity<Employee> response = restTemplate.exchange(
                updateEmployeeUrl,
                HttpMethod.PUT,
                request,
                Employee.class);

        log.debug("Update Employee call " + response.getStatusCode().toString());

        return response.getBody();
    }

    /**
     * Delete an employee using exchange method of RestTemplate
     * instead of delete method in order to log the response status code.
     * @param e The employee to delete
     */
    public void deleteEmployee(int id) {
        String baseApiUrl = props.getApiUrl();
        String deleteEmployeeUrl = baseApiUrl + "/employee/" + id;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Void> response = restTemplate.exchange(
                deleteEmployeeUrl,
                HttpMethod.DELETE,
                null,
                Void.class);

        log.debug("Delete Employee call " + response.getStatusCode().toString());
    }

}
