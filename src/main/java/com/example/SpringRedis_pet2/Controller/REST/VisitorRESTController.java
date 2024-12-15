package com.example.SpringRedis_pet2.Controller.REST;

import com.example.SpringRedis_pet2.Model.Visitor.Visitor;
import com.example.SpringRedis_pet2.Service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visitor/api")
public class VisitorRESTController {

    @Autowired
    private VisitorService operations;

    @PostMapping("/save")
    public ResponseEntity<HttpStatus> saveVisitor(@RequestBody Visitor visitor){
        operations.saveVisitor(visitor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
