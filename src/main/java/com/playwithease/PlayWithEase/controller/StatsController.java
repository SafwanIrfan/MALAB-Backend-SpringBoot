package com.playwithease.PlayWithEase.controller;

import com.playwithease.PlayWithEase.model.Court;
import com.playwithease.PlayWithEase.model.WeeklyStats;
import com.playwithease.PlayWithEase.repo.courtStatsRepo.MonthlyStatsRepo;
import com.playwithease.PlayWithEase.repo.courtStatsRepo.WeeklyStatsRepo;
import com.playwithease.PlayWithEase.repo.courtStatsRepo.YearlyStatsRepo;
import com.playwithease.PlayWithEase.service.CourtsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owner")
public class StatsController {

    @Autowired
    private CourtsService courtsService;

    @Autowired
    private WeeklyStatsRepo weeklyRepo;

    @Autowired
    private MonthlyStatsRepo monthlyRepo;

    @Autowired
    private YearlyStatsRepo yearlyRepo;

    @GetMapping("/stats/weekly/{courtId}/{year}/{week}")
    public WeeklyStats getWeekly(@PathVariable Long courtId,
                                 @PathVariable int year,
                                 @PathVariable int week) {

        Court isCourt = courtsService.getCourtById(courtId);

            return weeklyRepo.findByCourtAndYearAndWeek(isCourt, year, week)
                    .orElseThrow(() -> new RuntimeException("Weekly data not found."));
        }
}

