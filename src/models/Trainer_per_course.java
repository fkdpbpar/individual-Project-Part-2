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
public class Trainer_per_course {
    private int id;
    private int courses_id;
    private int trainers_id;

    public Trainer_per_course() {
    }

    public Trainer_per_course(int id, int courses_id, int trainers_id) {
        this.id = id;
        this.courses_id = courses_id;
        this.trainers_id = trainers_id;
    }

    public Trainer_per_course(int courses_id, int trainers_id) {
        this.courses_id = courses_id;
        this.trainers_id = trainers_id;
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
    
    
}
