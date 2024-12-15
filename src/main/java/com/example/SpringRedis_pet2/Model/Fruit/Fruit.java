package com.example.SpringRedis_pet2.Model.Fruit;

import com.example.SpringRedis_pet2.Model.Visitor.Visitor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "fruits")
public class Fruit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "color")
    private String color;

    @Column(name = "weight")
    private double weight;

    @Column(name = "price")
    private double price;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Fruit_type type;

    @JsonBackReference
    @ManyToMany(mappedBy = "favoriteFruits", fetch = FetchType.EAGER)
    private Set<Visitor> visitors; // Коллекция посетителей, которым нравится этот фрукт

    public Fruit() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Fruit_type getType() {
        return type;
    }

    public void setType(Fruit_type type) {
        this.type = type;
    }

    public Set<Visitor> getVisitors() {
        return visitors;
    }

    public void setVisitors(Set<Visitor> visitors) {
        this.visitors = visitors;
    }
}