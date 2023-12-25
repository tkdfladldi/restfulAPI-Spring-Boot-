package com.example.park.myrestfulservice.controller;

import com.example.park.myrestfulservice.dao.UserDaoService;
import com.example.park.myrestfulservice.exception.UserNotFoundException;
import com.example.park.myrestfulservice.model.AdminUser;
import com.example.park.myrestfulservice.model.AdminUserV2;
import com.example.park.myrestfulservice.model.User;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private UserDaoService service;

    public AdminUserController(UserDaoService service) {
        this.service = service;
    }

    @GetMapping("/v1/users/{id}")
    public MappingJacksonValue retrieveUser4AdminV1(@PathVariable int id){
        User user = service.findOne(id);

        AdminUser adminUser = new AdminUser();

        if(user == null){
            throw new UserNotFoundException(id);
        }else{
            BeanUtils.copyProperties(user , adminUser);
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(adminUser);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

    @GetMapping("/v2/users/{id}")
    public MappingJacksonValue retrieveUser4AdminV2(@PathVariable int id){
        User user = service.findOne(id);

        AdminUserV2 adminUser = new AdminUserV2();

        if(user == null){
            throw new UserNotFoundException(id);
        }else{
            BeanUtils.copyProperties(user , adminUser);
            adminUser.setGrade("VIP");

        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "grade");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(adminUser);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUser4Admin(){
        List<User> users = service.findAll();

        List<AdminUser> adminUserList = users.stream()
                                             .map( user -> {AdminUser adminUser = new AdminUser();
                                                            BeanUtils.copyProperties(user , adminUser);
                                                            return  adminUser;
                                                            })
                                             .collect(Collectors.toList());

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "joinDate", "ssn");
        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(adminUserList);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }


}