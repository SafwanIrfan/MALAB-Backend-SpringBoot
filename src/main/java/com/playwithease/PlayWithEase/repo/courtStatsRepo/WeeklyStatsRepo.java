package com.playwithease.PlayWithEase.repo.courtStatsRepo;

import com.playwithease.PlayWithEase.model.Court;
import com.playwithease.PlayWithEase.model.WeeklyStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeeklyStatsRepo extends JpaRepository<WeeklyStats, Long> {
    Optional<WeeklyStats> findByCourtAndYearAndWeek(Court court, int year, int week);
}


