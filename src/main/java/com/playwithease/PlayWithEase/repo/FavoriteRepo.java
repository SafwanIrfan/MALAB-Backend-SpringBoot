package com.playwithease.PlayWithEase.repo;

import com.playwithease.PlayWithEase.model.CourtFavoriteId;
import com.playwithease.PlayWithEase.model.CourtsFav;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepo extends JpaRepository<CourtsFav, CourtFavoriteId> {
    boolean existsByUsersIdAndCourtId(Long usersId, Long courtId);
    void deleteByUsersIdAndCourtId(Long usersId,Long courtId);
    List<CourtsFav> findByUsersId(Long usersId);
}
