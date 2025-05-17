package com.playwithease.PlayWithEase.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CourtFavoriteId implements Serializable {
    private Long usersId;

    private Long courtId;

    public CourtFavoriteId() {}

    public CourtFavoriteId(Long usersId, Long courtId) {
        this.usersId = usersId;
        this.courtId = courtId;
    }



    // hashCode and equals are mandatory for composite keys
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CourtFavoriteId that)) return false;
        return Objects.equals(usersId, that.usersId) &&
                Objects.equals(courtId, that.courtId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usersId, courtId);
    }

    public Long getCourtId() {
        return courtId;
    }

    public void setCourtId(Long courtId) {
        this.courtId = courtId;
    }

    public Long getUsersId() {
        return usersId;
    }

    public void setUsersId(Long usersId) {
        this.usersId = usersId;
    }
}
