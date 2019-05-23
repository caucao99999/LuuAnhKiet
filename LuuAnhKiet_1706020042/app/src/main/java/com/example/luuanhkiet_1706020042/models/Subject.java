package com.example.luuanhkiet_1706020042.models;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Subject implements Serializable {
    String description;
    String subject_code;
    String subject_name;
    int id;
    int credits;
    @Exclude
    String keyParent;
    @Exclude
    public String getKeyParent() {
        return keyParent;
    }
    @Exclude
    public void setKeyParent(String keyParent) {
        this.keyParent = keyParent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubject_code() {
        return subject_code;
    }

    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
