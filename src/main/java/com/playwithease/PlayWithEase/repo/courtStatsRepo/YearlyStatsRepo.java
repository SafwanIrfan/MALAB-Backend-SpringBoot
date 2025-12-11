package com.playwithease.PlayWithEase.repo.courtStatsRepo;

import com.playwithease.PlayWithEase.model.Court;
import com.playwithease.PlayWithEase.model.YearlyStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface YearlyStatsRepo extends JpaRepository<YearlyStats, Long> {
    Optional<YearlyStats> findByCourtAndYear(Court court, int year);
}
