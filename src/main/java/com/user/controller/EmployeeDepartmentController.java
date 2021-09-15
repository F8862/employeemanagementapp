package com.user.controller;

import com.user.model.Department;
import com.user.model.Employee;
import com.user.repository.DepartmentRepository;
import com.user.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeDepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @PostMapping("/employee/department")
    public ResponseEntity<Department> create(@RequestBody Department department) {
        Department savedDepartment = departmentRepository.save(department);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedDepartment.getId()).toUri();

        return ResponseEntity.created(location).body(savedDepartment);
    }
//retrieve all employees in the department
    @GetMapping("/employee/department/{id}")
    public List<Employee> retrieveAllEmployee(@PathVariable long id) throws Exception {
        Optional<Department> departmentOptional=departmentRepository.findById(id);
        if (!departmentOptional.isPresent()){
            throw new Exception("Department not found!!!");
        }
        //Optional<Employee> employeeOptional=employeeRepository.findById(id);
        return departmentOptional.get().getEmployees();
    }

    @DeleteMapping("/employee/department/{id}")

    public ResponseEntity<Department> deleteDepartment(@PathVariable Long id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        departmentRepository.delete(optionalDepartment.get());

        return ResponseEntity.noContent().build();
    }

}
