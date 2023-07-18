package com.example.invoicesystem.controller;

import com.example.invoicesystem.model.Customer;
import com.example.invoicesystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequestScope
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    //for fetching customer list
    @GetMapping("/get")
    public ResponseEntity<?> getCustomers() {
        return this.customerService.getCustomers();
    }

    //for fetching customer list
    @GetMapping("/get/{subscriberId}")
    public ResponseEntity<?> getCustomer(@PathVariable Long subscriberId) {
        return this.customerService.getCustomer(subscriberId);
    }

    //that registers the customer in the system
    @PostMapping("/save")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        return this.customerService.createCustomer(customer);
    }

    //for customer information deletion
    @DeleteMapping("/delete/{subscriberId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long subscriberId) {
        return this.customerService.deleteCustomer(subscriberId);
    }

    //Update the customer
    @PutMapping("/update/{subscriberId}")
    public ResponseEntity<?> updateCustomer(@RequestParam(required = false) String name,
                                            @RequestParam(required = false) String surname,
                                            @PathVariable Long subscriberId) {
        try{
            return this.customerService.customerUpdate(name, surname, subscriberId);
        }catch (Exception error){
            System.out.println("Error Log: "+error);
            return ResponseEntity.badRequest().body("Customer information could not be updated.");
        }

    }

}
