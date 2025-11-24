package com.whattodo.demo;

public class Whattodov1Entry {
    private Long id;
    private String text;
    private boolean done;

    public Whattodov1Entry() {}

    public Whattodov1Entry(String text) {
        this.text = text;
        this.done = false;
    }

    // Getter und Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public boolean isDone() { return done; }
    public void setDone(boolean done) { this.done = done; }
}
