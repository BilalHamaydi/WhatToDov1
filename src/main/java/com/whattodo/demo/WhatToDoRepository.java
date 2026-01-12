package com.whattodo.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WhatToDoRepository extends JpaRepository<Task, Long> {
    List<Task> findByDate(LocalDate date);
}
