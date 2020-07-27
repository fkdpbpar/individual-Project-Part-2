/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author 30694
 */
public class Assignment_per_course {
    private int id;
    private int courses_id;
    private int assignments_id;

    public Assignment_per_course() {
    }

    public Assignment_per_course(int id, int courses_id, int assignments_id) {
        this.id = id;
        this.courses_id = courses_id;
        this.assignments_id = assignments_id;
    }

    public Assignment_per_course(int courses_id, int assignments_id) {
        this.courses_id = courses_id;
        this.assignments_id = assignments_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourses_id() {
        return courses_id;
    }

    public void setCourses_id(int courses_id) {
        this.courses_id = courses_id;
    }

    public int getAssignments_id() {
        return assignments_id;
    }

    public void setAssignments_id(int assignments_id) {
        this.assignments_id = assignments_id;
    }
    
    
}
