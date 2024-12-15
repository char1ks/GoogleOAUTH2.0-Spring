package com.example.SpringRedis_pet2.Repository;

import com.example.SpringRedis_pet2.Model.Fruit.Fruit;
import com.example.SpringRedis_pet2.Model.Fruit.Fruit_type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FruitRepository extends JpaRepository<Fruit, Integer> {

    List<Fruit> findByType(Fruit_type type); // Это будет использоваться для всех типов
}