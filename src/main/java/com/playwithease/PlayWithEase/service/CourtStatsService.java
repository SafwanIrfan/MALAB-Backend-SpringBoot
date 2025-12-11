package com.playwithease.PlayWithEase.service;

import com.playwithease.PlayWithEase.model.Court;
import com.playwithease.PlayWithEase.model.DailyStats;
import com.playwithease.PlayWithEase.repo.courtStatsRepo.DailyStatsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CourtStatsService {

    @Autowired
    private DailyStatsRepo dailyRepo;

    public void updateDailyStats(Court court, double amount) {
        LocalDate today = LocalDate.now();

        DailyStats stats = dailyRepo.findByCourtAndDate(court, today)
                .orElseGet(() -> {
                    DailyStats ds = new DailyStats();
                    ds.setCourt(court);
                    ds.setDate(today);
                    return ds;
                });

        stats.setTotalBookings(stats.getTotalBookings() + 1);
        stats.setMoneyEarned(stats.getMoneyEarned() + amount);

        dailyRepo.save(stats);
    }

}
