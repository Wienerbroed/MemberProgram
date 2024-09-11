package org.example.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Member {
    //Attributes
    private String name;
    private LocalDate birthDate;
    private String gender;
    private LocalDateTime joinDate;
    private LocalDateTime dateOfPayment;

    private Map<String, List<Double>> swimTimes = new HashMap<>();

    public Member(String name, LocalDate birthDate, String gender, LocalDateTime joinDate, LocalDateTime dateOfPayment) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.joinDate = joinDate;
        this.dateOfPayment = dateOfPayment;
    }

    //Getters
    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public LocalDateTime getDateOfPayment() {
        return dateOfPayment;
    }

    public Map<String, List<Double>> getSwimTimes() {
        return swimTimes;
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }

    public void setDateOfPayment(LocalDateTime dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public void setSwimTimes(Map<String, List<Double>> swimTimes) {
        this.swimTimes = swimTimes;
    }



}
