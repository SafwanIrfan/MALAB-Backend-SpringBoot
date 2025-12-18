package com.playwithease.PlayWithEase.controller;

import com.playwithease.PlayWithEase.model.*;
import com.playwithease.PlayWithEase.repo.courtStatsRepo.DailyStatsRepo;
import com.playwithease.PlayWithEase.repo.courtStatsRepo.MonthlyStatsRepo;
import com.playwithease.PlayWithEase.repo.courtStatsRepo.WeeklyStatsRepo;
import com.playwithease.PlayWithEase.repo.courtStatsRepo.YearlyStatsRepo;
import com.playwithease.PlayWithEase.service.CourtsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/owner")
public class StatsController {

    @Autowired
    private CourtsService courtsService;

    @Autowired
    private DailyStatsRepo dailyRepo;

    @Autowired
    private WeeklyStatsRepo weeklyRepo;

    @Autowired
    private MonthlyStatsRepo monthlyRepo;

    @Autowired
    private YearlyStatsRepo yearlyRepo;

    @GetMapping("/stats/daily/{courtId}/{date}")
    public DailyStats getDaily(@PathVariable Long courtId,
                               @PathVariable LocalDate date) {

        Court isCourt = courtsService.getCourtById(courtId);

        return dailyRepo.findByCourtAndDate(isCourt, date)
                .orElseThrow(() -> new RuntimeException("Daily data not found."));
    }

    @GetMapping("/stats/weekly/{courtId}/{year}/{week}")
    public WeeklyStats getWeekly(@PathVariable Long courtId,
                                 @PathVariable int year,
                                 @PathVariable int week) {

        Court isCourt = courtsService.getCourtById(courtId);

            return weeklyRepo.findByCourtAndYearAndWeek(isCourt, year, week)
                    .orElseThrow(() -> new RuntimeException("Weekly data not found."));
        }

    @GetMapping("/stats/monthly/{courtId}/{year}/{month}")
    public MonthlyStats getMonthly(@PathVariable Long courtId,
                                   @PathVariable int year,
                                   @PathVariable int month
                                 ) {

        Court isCourt = courtsService.getCourtById(courtId);

        return monthlyRepo.findByCourtAndYearAndMonth(isCourt, year, month)
                .orElseThrow(() -> new RuntimeException("Monthly data not found."));
    }

    @GetMapping("/stats/yearly/{courtId}/{year}")
    public YearlyStats getYearly(@PathVariable Long courtId,
                                 @PathVariable int year
    ) {

        Court isCourt = courtsService.getCourtById(courtId);

        return yearlyRepo.findByCourtAndYear(isCourt, year)
                .orElseThrow(() -> new RuntimeException("Yearly data not found."));
    }
}

