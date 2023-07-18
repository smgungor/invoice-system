package com.example.invoicesystem.service;

import com.example.invoicesystem.model.Customer;
import com.example.invoicesystem.repository.CustomerRepository;
import com.example.invoicesystem.response.CustomerResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    //Service for fetching customer list
    public ResponseEntity<?> getCustomers(){
        if (this.customerRepository.findAll()==null || this.customerRepository.findAll().isEmpty()) {
            return CustomerResponseHandler.generateResponseNot(
                    "There is no customer information in the database.",
                    HttpStatus.OK);
        }else if(this.customerRepository.findAll()!=null || !this.customerRepository.findAll().isEmpty()){
            return CustomerResponseHandler.generateResponse(
                    "Customer list brought.",
                    HttpStatus.OK,
                    this.customerRepository.findAll());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //Service for fetching customer list
    public ResponseEntity<?> getCustomer(Long subscriberId) {
        if (this.customerRepository.findById(subscriberId).equals(Optional.empty())) {
            return CustomerResponseHandler.generateResponseNot(
                    "No customer associated with this subscriberID!",
                    HttpStatus.OK);
        } else {
            Customer customer = this.customerRepository.findById(subscriberId).orElseThrow(()->new EntityNotFoundException("Customer Not Found"));
            return CustomerResponseHandler.generateResponse(
                    "Customer brought.",
                    HttpStatus.OK,
                    customer);
        }
    }

    //Service that registers the customer in the system
    public ResponseEntity<Object> createCustomer(Customer customer) {

        if (customer != null) {

            boolean validateControl = false;
            Customer customerToBeCreated = new Customer();

            if (Objects.nonNull(customer.getName()) && customer.getName().length()>0 && !customer.getName().isEmpty()) {
                customerToBeCreated.setName(customer.getName());
            } else {
                validateControl = true;
            }

            if (Objects.nonNull(customer.getSurname()) && customer.getSurname().length()>0 && !customer.getSurname().isEmpty()) {
                customerToBeCreated.setSurname(customer.getSurname());
            } else {
                validateControl = true;
            }

            if (validateControl) {
                return CustomerResponseHandler.generateResponseNot(
                        "There was an error in the entered user information, please check.",
                        HttpStatus.OK);
            } else {
                this.customerRepository.save(customerToBeCreated);
                return CustomerResponseHandler.generateResponse(
                        "User created with entered user information.",
                        HttpStatus.OK,
                        customerToBeCreated);
            }
        } else {
            return CustomerResponseHandler.generateResponseNot(
                    "User information is blank.",
                    HttpStatus.OK);
        }

    }

    //Service for customer information deletion
    public ResponseEntity<Object> deleteCustomer(Long subscriberId) {

        if (this.customerRepository.findById(subscriberId).equals(Optional.empty())) {
            return CustomerResponseHandler.generateResponseNot(
                    "No customer associated with this subscriberID!",
                    HttpStatus.OK);
        } else {
            Customer customerToBeDeleted = this.customerRepository.findById(subscriberId).orElseThrow(()->new EntityNotFoundException("Customer Not Found"));
            this.customerRepository.deleteById(subscriberId);
            return CustomerResponseHandler.generateResponse(
                    "Customer deleted.",
                    HttpStatus.OK,
                    customerToBeDeleted);
        }

    }

    //Service to update the customer
    @Transactional
    public ResponseEntity<Object> customerUpdate(String name, String surname, Long subscriberNo) {
        Customer customerToUpdate = this.customerRepository.findById(subscriberNo).orElseThrow(()->new EntityNotFoundException("Customer Not Found"));

        boolean validateControl = true;

        if (Objects.nonNull(name) && name.length()>0 && !customerToUpdate.getName().equals(name)){
            customerToUpdate.setName(name);
            validateControl = false;
        }
        if (Objects.nonNull(surname) && surname.length()>0 && !customerToUpdate.getSurname().equals(surname)){
            customerToUpdate.setSurname(surname);
            validateControl = false;
        }

        if (!validateControl) {
            return CustomerResponseHandler.generateResponse(
                    "Customer information updated.",
                    HttpStatus.OK,
                    customerToUpdate);
        } else {
            return CustomerResponseHandler.generateResponseNot(
                    "Customer information could not be updated.",
                    HttpStatus.OK);
        }
    }

}
