package com.user.controller;

import com.user.model.Department;
import com.user.model.Employee;
import com.user.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DepartmentController {
    @Autowired
    private DepartmentRepository departmentRepository;
    @PostMapping("/department")
    public Department addDepartment(@RequestBody Department department){
        departmentRepository.save(department);
        return department;
    }
    @GetMapping("/department")
    public List<Department> getAllDepartments(){
        List<Department> employees=departmentRepository.findAll();
        return employees;
    }
    @GetMapping("/department/{id}")
    public Optional<Department> getDepartment(@RequestHeader("id") Long id){
        return  departmentRepository.findById(id);

    }
    @DeleteMapping("/department/{id}")
    public void deleteDepartment(@RequestHeader("id") Long id){
        departmentRepository.deleteById(id);

    }
    @PutMapping("/department/{id}")
    public ResponseEntity<Department> updateDepartment(@RequestHeader("id") Long id, @RequestBody Department department){
        Optional<Department> employeeData=departmentRepository.findById(id);
        if (employeeData.isPresent()){
            Department department1=employeeData.get();
            department1.setDepartment_name(department.getDepartment_name());

            return new ResponseEntity(departmentRepository.save(department1), HttpStatus.OK);

        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
