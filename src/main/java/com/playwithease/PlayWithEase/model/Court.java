package com.playwithease.PlayWithEase.model;

import jakarta.persistence.*;


import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Court {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private String description;
    private String location;
    private BigDecimal pricePerHour;
//    private double latitude;
//    private double longitude;

    @OneToMany(mappedBy = "court", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageUrls> imageUrls;

    @OneToMany(mappedBy = "court", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookedSlots> bookedSlots;

    @OneToMany(mappedBy = "court", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Timings> timings;

    @OneToMany(mappedBy = "court", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourtsFav> courtsFavorites;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation(){
        return location;
    }
    public void setLocation(String location){
        this.location = location;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
    }

    public List<ImageUrls> getImageUrls(){
        return imageUrls;
    }
    public void setImageUrls(List<ImageUrls> imageUrls){
        this.imageUrls = imageUrls;
    }

    public List<BookedSlots> getBookedSlots() {
        return bookedSlots;
    }
    public void setBookedSlots(List<BookedSlots> bookedSlots) {
        this.bookedSlots = bookedSlots;
    }

    public List<Timings> getTimings() {
        return timings;
    }

    public void setTimings(List<Timings> timings) {
        this.timings = timings;
    }

    public List<CourtsFav> getCourtsFavorites() {
        return courtsFavorites;
    }

    public void setCourtsFavorites(List<CourtsFav> courtsFavorites) {
        this.courtsFavorites = courtsFavorites;
    }



}


