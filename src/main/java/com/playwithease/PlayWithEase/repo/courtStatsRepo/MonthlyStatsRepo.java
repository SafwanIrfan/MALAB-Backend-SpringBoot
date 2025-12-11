package com.playwithease.PlayWithEase.repo.courtStatsRepo;

import com.playwithease.PlayWithEase.model.Court;
import com.playwithease.PlayWithEase.model.MonthlyStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonthlyStatsRepo extends JpaRepository<MonthlyStats, Long> {
    Optional<MonthlyStats> findByCourtAndYearAndMonth(Court court, int year, int month);
}

