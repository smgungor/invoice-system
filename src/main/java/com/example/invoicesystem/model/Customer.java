package com.example.invoicesystem.model;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "Users", uniqueConstraints = {@UniqueConstraint(columnNames = {"subscriberId"})})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long subscriberId;
    @NotNull
    private String name;
    @NotNull
    private String surname;

    public Customer() {}

    public Customer(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Long getSubscriberNo() {
        return subscriberId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return subscriberId.equals(customer.subscriberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subscriberId);
    }
}
