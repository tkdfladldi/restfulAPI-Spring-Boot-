package com.example.park.myrestfulservice.controller;

import com.example.park.myrestfulservice.dao.UserDaoService;
import com.example.park.myrestfulservice.exception.UserNotFoundException;
import com.example.park.myrestfulservice.model.User;
import lombok.Getter;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URL;
import java.util.List;

@RestController
public class UserController {
    private UserDaoService service;

    public UserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping(path = "/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id){
        User user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundException(id);
        }

        EntityModel entityModel = EntityModel.of(user);

        WebMvcLinkBuilder linTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(linTo.withRel("all-users")); // all-users -> http://localhost:8088/users

        return entityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){

        User saveUser = service.save(user);
        URI location =  ServletUriComponentsBuilder.fromCurrentRequest()
                                                   .path("/{id}")
                                                   .buildAndExpand(saveUser.getId())
                                                   .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable int id){
        service.delete(id);
        return ResponseEntity.noContent().build(); // no content
    }


}