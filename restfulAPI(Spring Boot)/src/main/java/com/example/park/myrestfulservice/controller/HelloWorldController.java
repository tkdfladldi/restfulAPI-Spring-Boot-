package com.example.park.myrestfulservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;

    @GetMapping(path = "/hello-world")
    public String helloWorld(){

        return "Hello World";
    }
    @GetMapping(path = "/hello-bean")
    public HelloworldBean helloworldBean(){
        return new HelloworldBean("hii");
    }

    @GetMapping(path = "/hello-world-bean/path-variable/{name}")
    public HelloworldBean helloWorldBeanPathVariable(@PathVariable String name){
        return new HelloworldBean(String.format("hello World, %s",name  ));
    }


    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized(@RequestHeader(name="Accept-Language", required = false) Locale locale){

        return messageSource.getMessage("greeting.message" , null , locale);
    }


}