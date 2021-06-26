/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.Serializable;

/**
 *
 * @author Jen
 */
public class Animal implements Serializable {

    

    private int animalID;
    private String name;
    private int age;
    private boolean health_status;
    private boolean feeding_status;
    private String gender;
    private String specific_type;

    public Animal(String name, int age, boolean health_status, boolean feeding_status, String gender, String specific_type) {
        this.animalID = animalID;
        this.name = name;
        this.age = age;
        this.health_status = health_status;
        this.feeding_status = feeding_status;
        this.gender = gender;
        this.specific_type = specific_type;

    }

    public void setAnimalID(int animalID) {
        this.animalID = animalID;
    }

    public int getAnimalID() {
        return animalID;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;

    }

    public boolean getHealth() {
        return health_status;

    }

    public void setHealth(boolean health_status) {
        this.health_status = health_status;
    }

    public boolean getfeeding() {
        return feeding_status;
    }

    public void setFeeding(boolean feeding_status) {
        this.feeding_status = feeding_status;

    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return specific_type;
    }

    public void setType(String specific_type) {
        this.specific_type = specific_type;
    }

}
