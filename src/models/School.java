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
public class School {

    private int id;
    private int courses_id;
    private int trainers_id;
    private int students_id;
    private int assignments_id;

    public School() {
    }

    public School(int id, int courses_id, int trainers_id, int students_id, int assignments_id) {
        this.id = id;
        this.courses_id = courses_id;
        this.trainers_id = trainers_id;
        this.students_id = students_id;
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

    public int getTrainers_id() {
        return trainers_id;
    }

    public void setTrainers_id(int trainers_id) {
        this.trainers_id = trainers_id;
    }

    public int getStudents_id() {
        return students_id;
    }

    public void setStudents_id(int students_id) {
        this.students_id = students_id;
    }

    public int getAssignments_id() {
        return assignments_id;
    }

    public void setAssignments_id(int assignments_id) {
        this.assignments_id = assignments_id;
    }
    
    
}
