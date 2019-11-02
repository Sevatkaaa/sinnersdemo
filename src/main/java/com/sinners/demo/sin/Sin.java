package com.sinners.demo.sin;

import com.sinners.demo.user.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(name = "user_likes",
            joinColumns = { @JoinColumn(name = "sin_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<User> likedBy = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Sin sin = (Sin) o;

        return id.equals(sin.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

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

    public Set<User> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(Set<User> likedBy) {
        this.likedBy = likedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void sin() {

    }
}
