package com.example.hp.operatestproject;

import java.util.ArrayList;

/**
 * Created by Denis Pavlovsky on 25.07.15.
 */
public class Product {
    public int id;
    public String title;
    public String icon;
    public ArrayList<String> screenshots;
    public String short_descr;
    public String description;

    public Product(int id, String title, String icon, ArrayList<String> screenshots, String short_descr, String description) {
        this.id = id;
        this.title = title;
        this.icon = icon;
        this.screenshots = screenshots;
        this.short_descr = short_descr;
        this.description = description;
    }

    public Product() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public ArrayList<String> getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(ArrayList<String> screenshots) {
        this.screenshots = screenshots;
    }

    public String getShort_descr() {
        return short_descr;
    }

    public void setShort_descr(String short_descr) {
        this.short_descr = short_descr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addScreenshot (String screenshot){
        if (screenshots == null) {
            screenshots = new ArrayList<>();
            screenshots.add(screenshot);
        } else {
            screenshots.add(screenshot);
        }

    }
}
