package com.playwithease.PlayWithEase.model;

public class PriceResponse {

    private double totalPrice;
    private long durationMinutes;
    private double pricePerHour;

    public PriceResponse(double totalPrice, long durationMinutes,
                         double pricePerHour) {
        this.totalPrice = totalPrice;
        this.durationMinutes = durationMinutes;
        this.pricePerHour = pricePerHour;
    }

    // Getters
    public double getTotalPrice() {
        return totalPrice;
    }

    public long getDurationMinutes() {
        return durationMinutes;
    }

    public double getPricePerHour() {
        return pricePerHour;
    }

    //Setters
    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public void setDurationMinutes(long durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

}
