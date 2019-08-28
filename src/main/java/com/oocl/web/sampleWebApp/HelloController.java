package com.oocl.web.sampleWebApp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/hello")
public class HelloController {
    List<Employee> employees = new ArrayList<Employee>();


    @GetMapping(value = "/")
    public String sayHello(){
        return  "hello";
    }
    @GetMapping(value = "/sayHello")
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String sayHello1(){
        return  "hello";
    }
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") String id){
        employees.add(new Employee("1","like",22,"male"));
        employees.add(new Employee("2","xiexinfang",22,"female"));
        for (Employee employee : employees){
            if (employee.getId().equals(id)){
                return ResponseEntity.ok(employee);
            }
        }
        // return ResponseEntity.notFound().build();
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    @PostMapping(value = "/")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody Employee employee) {
        employees.add(employee);
        return employee;

    }

}
