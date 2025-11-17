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

    @GetMapping("/todos1")
    public List<String> todos() {
        return List.of("M1", "M2", "M3");
    }


    @GetMapping("/todos2")
    public String todos2() {
        System.out.println("Hello World"); // Optional, nur für die Konsole
        return "Hello World";               // Das geht an den Browser
    }

    @GetMapping("/todos3")

    public List<Whattodov1Entry> getTodoEntries() {
        return List.of(new Whattodov1Entry("M1"), new Whattodov1Entry("M2"), new Whattodov1Entry("M3"));
    }

    @GetMapping("/todos4")

    public List<Whattodov1Entry> getCalender() {
        return List.of(new Calender(1));
    }




}
