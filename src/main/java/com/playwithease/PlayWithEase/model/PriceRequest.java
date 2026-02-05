package com.playwithease.PlayWithEase.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PriceRequest {

    @NotNull
    private Long courtId;

    @NotBlank
    private String date;      // yyyy-MM-dd

    @NotBlank
    private String startTime; // HH:mm

    @NotBlank
    private String endTime;   // HH:mm

    // Constructors
    public PriceRequest() {
    }

    public PriceRequest(Long courtId, String date, String startTime, String endTime) {
        this.courtId = courtId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters & Setters
    public Long getCourtId() {
        return courtId;
    }

    public void setCourtId(Long courtId) {
        this.courtId = courtId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
