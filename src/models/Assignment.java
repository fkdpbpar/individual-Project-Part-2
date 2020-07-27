/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Date;



/**
 *
 * @author 30694
 */
public class Assignment {
    private int id;
    private String title;
    private String description;
    private String sub_date_time;
    private int total_mark; 

    public Assignment() {
    }

    public Assignment(int id, String title, String description, String sub_date_time, int total_mark) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.sub_date_time = sub_date_time;
        this.total_mark = total_mark;
    }
    
    public Assignment(String title, String description, String sub_date_time, int total_mark) {
        this.title = title;
        this.description = description;
        this.sub_date_time = sub_date_time;
        this.total_mark = total_mark;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSub_date_time() {
        return sub_date_time;
    }

    public void setSub_date_time(String sub_date_time) {
        this.sub_date_time = sub_date_time;
    }

    public int getTotal_mark() {
        return total_mark;
    }

    public void setTotal_mark(int total_mark) {
        this.total_mark = total_mark;
    }
    
    
}
