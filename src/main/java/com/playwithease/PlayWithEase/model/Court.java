package com.playwithease.PlayWithEase.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.playwithease.PlayWithEase.model.Enums.CourtStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;


import java.math.BigDecimal;
import java.util.List;

@Entity
public class Court {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String courtName;
    private String description;
    private String city;
    private String area;
    private BigDecimal pricePerHour;
    private Long totalBookings = 0L;
    private Double moneyEarned = 0.0;

    @Enumerated(EnumType.STRING) // saves actual value instead of index
    private CourtStatus courtStatus;

    @JsonIgnore // To remove large json lines
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Users owner;

    @OneToMany(mappedBy = "court", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourtImageUrls> courtImageUrls;

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

    public String getCourtName(){
        return courtName;
    }
    public void setCourtName(String courtName){
        this.courtName = courtName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }
    public void setArea(String area) {
        this.area = area;
    }

    public Long getTotalBookings() {
        return totalBookings;
    }

    public void setTotalBookings(Long totalBookings) {
        this.totalBookings = totalBookings;
    }

    public BigDecimal getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(BigDecimal pricePerHour) {
        this.pricePerHour = pricePerHour;
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

    public List<CourtImageUrls> getCourtImageUrls() {
        return courtImageUrls;
    }

    public void setCourtImageUrls(List<CourtImageUrls> courtImageUrls) {
        this.courtImageUrls = courtImageUrls;
    }


    public Users getOwner() {
        return owner;
    }
    public void setOwner(Users owner) {
        this.owner = owner;
    }

    public CourtStatus getCourtStatus() {
        return courtStatus;
    }
    public void setCourtStatus(CourtStatus courtStatus) {
        this.courtStatus = courtStatus;
    }

    public Double getMoneyEarned(){
        return moneyEarned;
    }
    public void setMoneyEarned(Double moneyEarned){
        this.moneyEarned = moneyEarned;
    }

}


