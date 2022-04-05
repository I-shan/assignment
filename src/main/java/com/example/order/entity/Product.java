package com.example.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;


@Entity
@Table(name = "PRODUCT")
public class Product {


    @Id
    @Positive
    @NotNull
    @Digits(integer = 10,fraction = 0)
    Integer productID;
    @NotBlank
    @Size(min=2,max = 200)
    String type;
    @NotBlank
    @Size(min=2,max = 200)
    String productName;


    Integer price;

    @JsonIgnore
    @ManyToMany(mappedBy = "products")
    List<Customer> customers;

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
}
