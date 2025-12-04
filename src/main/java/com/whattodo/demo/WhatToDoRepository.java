package com.whattodo.demo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WhatToDoRepository extends CrudRepository<Task, Long> { }