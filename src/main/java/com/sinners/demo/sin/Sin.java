package com.sinners.demo.sin;

import com.sinners.demo.user.User;

import javax.persistence.*;

@Entity
public class Sin implements Sinnable {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    protected Long id;
    protected String type;
    protected Integer weight;
    protected String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    protected User author;

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

    public Sin(String type, Integer weight, String description, User author) {
        this.type = type;
        this.weight = weight;
        this.description = description;
        this.author = author;
    }

    public String getAuthorName() {
        return author.getUsername();
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public void sin() {

    }
}
