package com.whattodo.demo;

import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;

    // 🔥 WICHTIG: Hier passen wir an die DB an.
    // Wenn deine DB-Spalte "is_done" heißt, passt das:
    @Column(name = "is_done")
    private Boolean done;

    @Column(name = "is_important")
    private Boolean important;

    @Column(name = "task_name")
    private String taskName;

    // optional falls du das schon in DB hast:
    // @Column(name="category")
    private String category;

    // @Column(name="color")
    private String color;

    // Datum: nur einbauen, wenn es wirklich in DB existiert!
    // @Column(name="date")
    // private java.time.LocalDate date;

    public Task() {}

    public Long getId() { return id; }

    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }

    public boolean isDone() { return Boolean.TRUE.equals(done); }
    public void setDone(boolean done) { this.done = done; }
    public Boolean getDoneRaw() { return done; }

    public boolean isImportant() { return Boolean.TRUE.equals(important); }
    public void setImportant(boolean important) { this.important = important; }
    public Boolean getImportantRaw() { return important; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

}
