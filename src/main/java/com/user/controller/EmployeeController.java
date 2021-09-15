package com.user.controller;

import com.user.model.Employee;
import com.user.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @PostMapping("/employee")
    public Employee addEmployee(@RequestBody Employee employee){
        employeeRepository.save(employee);
        return employee;
    }
    @GetMapping("/employee")
    public List<Employee> getAllEmployees(){
        List<Employee> employees=employeeRepository.findAll();
        return employees;
    }
    @GetMapping("/employee/{id}")
    public Optional<Employee> getEmployee(@PathVariable Long id){
        return  employeeRepository.findById(id);
       /*Optional<Employee> employee= employeeRepository.findById(id);
       return employee.get().getDepartment();
       */

    }
    @DeleteMapping("/employee/{id}")
    public void deleteEmployee(@PathVariable Long id){
        employeeRepository.deleteById(id);

    }
     @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(@RequestHeader("id") Long id, @RequestBody Employee employee){
        Optional<Employee> employeeData=employeeRepository.findById(id);
        if (employeeData.isPresent()){
            Employee employee1=employeeData.get();
            employee1.setName(employee.getName());
            employee1.setAddress(employee1.getAddress());
            employee1.setContact(employee1.getContact());
            return new ResponseEntity(employeeRepository.save(employee1), HttpStatus.OK);

        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
