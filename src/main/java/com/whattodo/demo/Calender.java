package com.whattodo.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Entity
public class Calender {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String dayName;
    String dayNumber;
    boolean isHoliday;
    String month;
    String year;
    int week;

    public Calender(){}
    public Calender(int week){
    //this.week
    }

}
