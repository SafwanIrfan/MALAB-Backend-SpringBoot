package com.playwithease.PlayWithEase.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "court_favorites")
public class CourtsFav {

    @EmbeddedId
    private CourtFavoriteId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courtId") // maps to courtId in the embedded ID
    @JoinColumn(name = "court_id")
    @JsonIgnore
    private Court court;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usersId") // maps to usersId in the embedded ID
    @JoinColumn(name = "users_id")
    @JsonIgnore
    private Users users;

    public CourtFavoriteId getId() {
        return id;
    }
    public void setId(CourtFavoriteId id) {
        this.id = id;
    }

    public Court getCourt() {
        return court;
    }

    public void setCourt(Court court) {
        this.court = court;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}


