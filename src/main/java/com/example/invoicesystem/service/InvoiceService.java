package com.example.invoicesystem.service;

import com.example.invoicesystem.model.Customer;
import com.example.invoicesystem.model.Invoice;
import com.example.invoicesystem.repository.CustomerRepository;
import com.example.invoicesystem.repository.InvoiceRepository;
import com.example.invoicesystem.response.CustomerResponseHandler;
import com.example.invoicesystem.response.InvoiceResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, CustomerRepository customerRepository) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
    }

    //Service for fetching invoice list
    public ResponseEntity<?> getAllInvoices() {
        if (this.invoiceRepository.findAll() == null || this.invoiceRepository.findAll().isEmpty()){
            return InvoiceResponseHandler.generateResponseNot(
                    "There is no invoice in the database.",
                    HttpStatus.OK);
        } else if(this.invoiceRepository.findAll() != null && !this.invoiceRepository.findAll().isEmpty()) {
            return InvoiceResponseHandler.generateResponse(
                    "Invoice list brought.",
                    HttpStatus.OK,
                    this.invoiceRepository.findAll());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    //Service for fetching invoice list
    public ResponseEntity<?> getInvoice(Long invoiceNo) {
        if (this.invoiceRepository.findById(invoiceNo).equals(Optional.empty())) {
            return InvoiceResponseHandler.generateResponseNot(
                    "No Invoice Found With This Invoice Number.",
                    HttpStatus.OK);
        } else {
            Invoice invoice = this.invoiceRepository.findById(invoiceNo).orElseThrow(()->new EntityNotFoundException("Invoice Not Found"));
            return InvoiceResponseHandler.generateResponse(
                    "Invoice Brought",
                    HttpStatus.OK,
                    invoice);
        }
    }

    //Service that registers the invoice in the system
    public ResponseEntity<Object> createInvoice(Invoice invoice) {

        if (invoice != null) {

            boolean validateControl = false;
            String message = "";
            Invoice invoiceToBeCreated = new Invoice();

            if (customerRepository.findById(invoice.getSubscriberNo()) != null){

                invoiceToBeCreated.setSubscriberNo(invoice.getSubscriberNo());

                if (invoice.getInvoiceAmount() >= 0) {
                    invoiceToBeCreated.setInvoiceAmount(invoice.getInvoiceAmount());
                } else {
                    message += "->Check the entered amount is incorrect. Cannot be less than zero. ";
                    validateControl = true;
                }

                try {
                    //if invoice date will be created by admin
                    /*Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(invoice.getInvoiceProDate().toString());
                    Date now = new Date();
                    if (now.compareTo(date1) > 0) {
                        invoiceToBeCreated.setInvoiceProDate(invoice.getInvoiceProDate());
                    } else if(now.compareTo(date1) < 0) {
                        validateControl = true;
                        message += "->Wrong date entered";
                    }*/

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                    LocalDate now = LocalDate.now();
                    invoiceToBeCreated.setInvoiceProDate(now);

                } catch (Exception error) {
                    validateControl = true;
                    message += "->"+error;
                }

                if (validateControl) {
                    return InvoiceResponseHandler.generateResponseNot(
                            message,
                            HttpStatus.OK);
                } else {
                    this.invoiceRepository.save(invoiceToBeCreated);
                    return InvoiceResponseHandler.generateResponse(
                            "Invoice created.",
                            HttpStatus.OK,
                            invoiceToBeCreated);
                }

            } else {
                return InvoiceResponseHandler.generateResponseNot(
                        "There is no user for the invoice to be created.",
                        HttpStatus.OK);
            }
        } else {
            return InvoiceResponseHandler.generateResponseNot(
                    "Invoice information is blank",
                    HttpStatus.OK);
        }
    }

    //Service for invoice information deletion
    public ResponseEntity<Object> deleteInvoice(Long invoiceNo) {
        if (this.invoiceRepository.findById(invoiceNo).equals(Optional.empty())) {
            return InvoiceResponseHandler.generateResponseNot(
                    "No Invoice Found With This Invoice Number.",
                    HttpStatus.OK);
        } else {
            Invoice invoiceToBeDeleted = this.invoiceRepository.findById(invoiceNo).orElseThrow(()->new EntityNotFoundException("Invoice Not Found"));
            this.invoiceRepository.deleteById(invoiceNo);
            return InvoiceResponseHandler.generateResponse(
                    "Invoice Deleted.",
                    HttpStatus.OK,
                    invoiceToBeDeleted);
        }
    }

    //Billing service
    public ResponseEntity<Object> invoiceInquiry(Long invoiceNo) {

        Invoice invoice = this.invoiceRepository.findById(invoiceNo).orElseThrow(()->new EntityNotFoundException("Invoice Not Found"));

        invoice.getPaymentControl();
        if (invoice.getPaymentControl() == 0) {
            return InvoiceResponseHandler.generateResponseNot(
                    "Inquiry invoice not paid.",
                    HttpStatus.OK);
        } else {
            return InvoiceResponseHandler.generateResponse(
                    "Inquiry invoice paid" + " Amount Paid: " + invoice.getInvoiceAmount(),
                    HttpStatus.OK,
                    invoice);
        }
    }
}
