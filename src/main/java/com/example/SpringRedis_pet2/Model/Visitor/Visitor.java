package com.example.SpringRedis_pet2.Model.Visitor;

import com.example.SpringRedis_pet2.Model.Fruit.Fruit;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "visitors")
public class Visitor implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Visitor_Role role;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "visitor_fruit", // Название промежуточной таблицы
            joinColumns = @JoinColumn(name = "visitor_id"), // Столбец, связывающий с Visitor
            inverseJoinColumns = @JoinColumn(name = "fruit_id") // Столбец, связывающий с Fruit
    )
    @JsonBackReference
    private Set<Fruit> favoriteFruits; // Коллекция фруктов, которые нравятся посетителю

    public Visitor() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Visitor_Role getRole() {
        return role;
    }

    public void setRole(Visitor_Role role) {
        this.role = role;
    }

    public Set<Fruit> getFavoriteFruits() {
        return favoriteFruits;
    }

    public void setFavoriteFruits(Set<Fruit> favoriteFruits) {
        this.favoriteFruits = favoriteFruits;
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}