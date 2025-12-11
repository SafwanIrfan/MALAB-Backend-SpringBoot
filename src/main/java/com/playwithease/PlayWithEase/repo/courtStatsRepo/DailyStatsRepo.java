package com.playwithease.PlayWithEase.repo.courtStatsRepo;

import com.playwithease.PlayWithEase.model.Court;
import com.playwithease.PlayWithEase.model.DailyStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyStatsRepo extends JpaRepository<DailyStats, Long> {
    Optional<DailyStats> findByCourtAndDate(Court court, LocalDate date);
    List<DailyStats> findAllByDate(LocalDate date);
}
