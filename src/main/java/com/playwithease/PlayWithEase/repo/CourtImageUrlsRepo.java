package com.playwithease.PlayWithEase.repo;

import com.playwithease.PlayWithEase.model.CourtImageUrls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourtImageUrlsRepo extends JpaRepository<CourtImageUrls, Long> {

    public List<CourtImageUrls> findByCourtId(Long courtId);
}
