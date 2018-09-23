package com.sinners.demo.sin;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Sin implements Sinnable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    protected Long id;
    protected String type;
    protected Integer weight;
    protected String description;

    public Sin() {
    }

    public Sin(String type) {
        this.type = type;
    }

    public Sin(String type, Integer weight) {
        this.weight = weight;
        this.type = type;
    }

    public Sin(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public Sin(String type, Integer weight, String description) {
        this.type = type;
        this.weight = weight;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public void sin() {

    }
}
