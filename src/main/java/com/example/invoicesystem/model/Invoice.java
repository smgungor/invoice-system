package com.example.invoicesystem.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "Invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long invoiceNo;
    private Long subscriberNo;
    private double invoiceAmount;
    private LocalDate invoiceProDate;
    private int paymentControl = 0;

    public Invoice() {}

    public Invoice(Long subscriberNo, double invoiceAmount, LocalDate invoiceProDate) {
        this.subscriberNo = subscriberNo;
        this.invoiceAmount = invoiceAmount;
        this.invoiceProDate = invoiceProDate;
    }

    public int getPaymentControl() {
        return paymentControl;
    }

    public void setPaymentControl(int paymentControl) {
        this.paymentControl = paymentControl;
    }

    public void setSubscriberNo(Long subscriberNo) {
        this.subscriberNo = subscriberNo;
    }

    public Long getInvoiceNo() {
        return invoiceNo;
    }

    public Long getSubscriberNo() {
        return subscriberNo;
    }

    public double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public LocalDate getInvoiceProDate() {
        return invoiceProDate;
    }

    public void setInvoiceProDate(LocalDate invoiceProDate) {
        this.invoiceProDate = invoiceProDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return invoiceNo.equals(invoice.invoiceNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoiceNo);
    }
}
