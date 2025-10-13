package com.whattodo.demo;


import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Whattodov1EntryController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/todos4")
    public List<String> todos() {
        return List.of("M1", "M2", "M3");
    }
    @GetMapping("/todos3")
    public String todos2() {
        System.out.println("Hello World"); // Optional, nur für die Konsole
        return "Hello World";               // Das geht an den Browser
    }




}
