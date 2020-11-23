package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/index")
@CrossOrigin(origins = "*",maxAge = 3600)
@ResponseBody
public class Indexcontroller {

    @GetMapping("/test")
    public String test() {
        return "ok";
    }

}