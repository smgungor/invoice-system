package com.example.invoicesystem.controller;

import com.example.invoicesystem.model.Invoice;
import com.example.invoicesystem.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    //for fetching invoice list
    @GetMapping("/get")
    public ResponseEntity<?> getAllInvoices() {
        return this.invoiceService.getAllInvoices();
    }

    //for fetching invoice list
    @GetMapping("/get/{invoiceNo}")
    public ResponseEntity<?> getInvoice(@PathVariable Long invoiceNo) {
        return this.invoiceService.getInvoice(invoiceNo);
    }

    //that registers the invoice in the system
    @PostMapping("/save")
    public ResponseEntity<?> createInvoice(@RequestBody Invoice invoice) {return this.invoiceService.createInvoice(invoice);}

    //for invoice information deletion
    @DeleteMapping("/delete/{invoiceNo}")
    public ResponseEntity<?> deleteInvoice(@PathVariable Long invoiceNo) {
        return this.invoiceService.deleteInvoice(invoiceNo);
    }

    //Billing service
    @GetMapping("/invoiceInquiry/{invoiceNo}")
    public ResponseEntity<?> invoiceInquiry(@PathVariable Long invoiceNo) {
        return this.invoiceService.invoiceInquiry(invoiceNo);
    }

}
