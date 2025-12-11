package com.playwithease.PlayWithEase.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.apache.catalina.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Entity
public class BookedSlots {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "court_id", nullable = false)
    @JsonIgnore
    private Court court;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    @JsonIgnore
    private Users users;

    private String courtName;
    private String day;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalTime bookedTime;
    private LocalDate bookedDate;
    private String bookedDay;
    private String status;
    private Long amount = 0L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public Users getUsers(){
        return users;
    }
    public void setUsers(Users users){
        this.users = users;
    }

    public String getCourtName() {
        return courtName;
    }
    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalTime getBookedTime() {
        return bookedTime;
    }

    public void setBookedTime(LocalTime bookedTime) {
        this.bookedTime = bookedTime;
    }

    public LocalDate getBookedDate(){
        return bookedDate;
    }
    public void setBookedDate(LocalDate bookedDate){
        this.bookedDate = bookedDate;
    }

    public String getBookedDay(){
        return bookedDay;
    }
    public void setBookedDay(String bookedDay){
        this.bookedDay = bookedDay;
    }

    public Long getAmount() {
        return amount;
    }
    public void setAmount(Long amount) {
        this.amount = amount;
    }
}


