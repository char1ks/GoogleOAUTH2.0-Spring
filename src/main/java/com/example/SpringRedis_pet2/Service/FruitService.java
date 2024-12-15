package com.example.SpringRedis_pet2.Service;

import com.example.SpringRedis_pet2.Model.Fruit.Fruit;
import com.example.SpringRedis_pet2.Model.Fruit.Fruit_type;
import com.example.SpringRedis_pet2.Redis.RedisPublisher;
import com.example.SpringRedis_pet2.Repository.FruitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FruitService {

    @Autowired
    private FruitRepository operations;

    @Autowired
    private RedisPublisher publisher;

    // Метод для получения всех фруктов
    public List<Fruit> getAllFruits(){
        return operations.findAll();
    }
    // Метод для получения конкретного фрукта
    @Cacheable(value = "fruit", key = "#id")
    public Fruit getConcreteFruit(int id){
        return operations.findById(id).orElse(null);
    }
    // Метод для получения всех хороших фруктов
    public List<Fruit> getGoodFruits() {
        return operations.findByType(Fruit_type.GOOD);
    }

    // Метод для получения всех плохих фруктов
    public List<Fruit> getBadFruits() {
        return operations.findByType(Fruit_type.BAD);
    }

    // Метод для получения всех экзотических фруктов
    public List<Fruit> getExoticFruits() {
        return operations.findByType(Fruit_type.EXOTIC);
    }

    @CachePut(value = "fruit", key = "#result.id")
    public Fruit saveFruit(Fruit fruit){
        publisher.publish("Советуем вам посмотреть на новый продукт:"+fruit.getName());
        return operations.save(fruit);
    }
    @CacheEvict(value = "fruit", key = "#id")
    public void deleteFruit(int id){
        operations.deleteById(id);
    }
}
