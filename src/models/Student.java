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
public class Student {

    private int id;
    private String first_name;
    private String last_name;
    private String date_of_birth;
    private int tuition_fees;

    public Student() {
    }

    public Student(int id, String first_name, String last_name, String date_of_birth, int tuition_fees) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.tuition_fees = tuition_fees;
    }
    
    public Student(String first_name, String last_name, String date_of_birth, int tuition_fees) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.tuition_fees = tuition_fees;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    
    
    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public int getTuition_fees() {
        return tuition_fees;
    }

    public void setTuition_fees(int tuition_fees) {
        this.tuition_fees = tuition_fees;
    }

}
