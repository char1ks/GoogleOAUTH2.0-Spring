package com.example.SpringRedis_pet2.Controller.REST;

import com.example.SpringRedis_pet2.Model.Fruit.Fruit;
import com.example.SpringRedis_pet2.Model.Visitor.Visitor;
import com.example.SpringRedis_pet2.Repository.VisitorRepository;
import com.example.SpringRedis_pet2.Service.FruitService;
import com.example.SpringRedis_pet2.Service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fruit/api")
public class FruitRESTController {

    @Autowired
    private FruitService operations;

    @Autowired
    private VisitorService operationsVisitor;

    @Autowired
    private VisitorRepository repository;
    @GetMapping("/all")
    public List<Fruit> allFruits(){
        return operations.getAllFruits();
    }
    @GetMapping("/all_exotic")
    public List<Fruit> exoticFruit(){
        return operations.getExoticFruits();
    }
    @GetMapping("/all_good")
    public List<Fruit> goodFruit(){
        return operations.getGoodFruits();
    }
    @GetMapping("/all_bad")
    public List<Fruit> badFruit(){
        return operations.getBadFruits();
    }
    @GetMapping("/concrete/{id}")
    public Fruit concreteFruit(@PathVariable("id")int id){
        return operations.getConcreteFruit(id);
    }
    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveFruit(@RequestBody Fruit fruit){
        operations.saveFruit(fruit);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @PutMapping("/buy/{fruit_id}")
    public ResponseEntity<HttpStatus> buyFruit(@PathVariable("fruit_id") int fruit_id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Проверяем, что пользователь аутентифицирован
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            Visitor visitor;

            // Проверяем, является ли principal экземпляром Visitor
            if (principal instanceof Visitor) {
                visitor = (Visitor) principal; // Приводим к Visitor
            } else if (principal instanceof DefaultOidcUser) {
                // Обработка случая, когда пользователь аутентифицирован через OAuth2
                DefaultOidcUser oidcUser = (DefaultOidcUser) principal;
                String givenName = (String) oidcUser.getAttributes().get("given_name");
                String familyName = (String) oidcUser.getAttributes().get("family_name");
                String username = "";

                // Формируем имя пользователя из имени и фамилии
                if (givenName != null) {
                    username += givenName;
                }
                if (familyName != null) {
                    if (!username.isEmpty()) {
                        username += " ";
                    }
                    username += familyName;
                }

                // Ищем пользователя по username
                visitor = repository.findByUsername(username);
                if (visitor == null) {
                    // Если Visitor не найден, создаем нового пользователя
                    visitor = new Visitor();
                    visitor.setUsername(username);
                    // Установите другие поля по умолчанию, если необходимо
                    operationsVisitor.saveVisitor(visitor);
                }
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Добавляем фрукт в список любимых
            Fruit fruit = operations.getConcreteFruit(fruit_id);
            fruit.getVisitors().add(visitor);
            visitor.getFavoriteFruits().add(fruit);
            operationsVisitor.saveVisitor(visitor);
            operations.saveFruit(fruit);
            return ResponseEntity.ok(HttpStatus.OK);
        }

        // Если пользователь не аутентифицирован, возвращаем статус 401 Unauthorized
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteFruit(@PathVariable("id")int id){
        operations.deleteFruit(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
