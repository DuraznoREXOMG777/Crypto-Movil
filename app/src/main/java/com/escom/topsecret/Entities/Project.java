package com.escom.topsecret.Entities;

/**
 * Top Secret Research Project Entity
 * It's the main object of the project.
 */
public class Project {
    private String title;
    private String area;
    private String date;
    private String description;

    public Project(String title, String area, String date, String description) {
        this.title = title;
        this.area = area;
        this.date = date;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title= name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
