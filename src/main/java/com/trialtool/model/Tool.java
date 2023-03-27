package com.trialtool.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "tool")
public class Tool {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Please enter the brand")
    @Column(name = "brand")
    private String brand;
    @NotEmpty(message = "Please enter the type")
    @Column(name = "type")
    private String type;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;
    @Column(name = "taken_on")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenOn;
    @Transient
    private boolean expired; //Hibernate shouldn`t see this field

    public Tool() {
    }

    public Tool(String brand, String type) {
        this.brand = brand;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getTakenOn() {
        return takenOn;
    }

    public void setTakenOn(Date takenOn) {
        this.takenOn = takenOn;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }
}
