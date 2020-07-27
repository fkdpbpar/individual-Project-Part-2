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
public class Student_per_course {
    private int id;
    private int courses_id;
    private int students_id;

    public Student_per_course() {
    }

    public Student_per_course(int id, int courses_id, int students_id) {
        this.id = id;
        this.courses_id = courses_id;
        this.students_id = students_id;
    }

    public Student_per_course(int courses_id, int students_id) {
        this.courses_id = courses_id;
        this.students_id = students_id;
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

    public int getStudents_id() {
        return students_id;
    }

    public void setStudents_id(int students_id) {
        this.students_id = students_id;
    }
    
    
}
