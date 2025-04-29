package com.playwithease.PlayWithEase.repo;

import com.playwithease.PlayWithEase.model.ImageUrls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageUrlsRepo extends JpaRepository<ImageUrls, Long> {

    public List<ImageUrls> findByCourtId(Long courtId);
}
