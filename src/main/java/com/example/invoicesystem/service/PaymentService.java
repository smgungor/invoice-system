package com.example.invoicesystem.service;

import com.example.invoicesystem.model.Payment;
import com.example.invoicesystem.repository.PaymentRepository;
import com.example.invoicesystem.response.InvoiceResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    //Performs querying based on subscriberNo.
    public ResponseEntity<Object> invoiceInquiry(Long subscriberId) {
        List<Payment> payments = this.paymentRepository.findBySubscriberNo(subscriberId);
        if (payments.size()>0) {
            return InvoiceResponseHandler.generateResponse(
                    "The customer has no unpaid invoices.",
                    HttpStatus.OK,
                    payments.get(0));
        } else {
            return InvoiceResponseHandler.generateResponseNot(
                    "There is no such paid invoice.",
                    HttpStatus.OK);
        }

    }

}
