package com.example.invoicesystem.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Payments")
public class Payment{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double paymentAmount;
    private LocalDate paymentDate;
    private Long subscriberNo;

    public Payment() {}

    public Payment(Long id, double paymentAmount, LocalDate paymentDate, Long subscriberNo) {
        this.id = id;
        this.paymentAmount = paymentAmount;
        this.paymentDate = paymentDate;
        this.subscriberNo = subscriberNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Long getSubscriberNo() {
        return subscriberNo;
    }

}
