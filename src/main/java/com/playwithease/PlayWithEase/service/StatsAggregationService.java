package com.playwithease.PlayWithEase.service;

import com.playwithease.PlayWithEase.model.*;
import com.playwithease.PlayWithEase.repo.courtStatsRepo.DailyStatsRepo;
import com.playwithease.PlayWithEase.repo.courtStatsRepo.MonthlyStatsRepo;
import com.playwithease.PlayWithEase.repo.courtStatsRepo.WeeklyStatsRepo;
import com.playwithease.PlayWithEase.repo.courtStatsRepo.YearlyStatsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;

@Service
public class StatsAggregationService {

    @Autowired
    private DailyStatsRepo dailyRepo;

    @Autowired
    private WeeklyStatsRepo weeklyRepo;

    @Autowired
    private MonthlyStatsRepo monthlyRepo;

    @Autowired
    private YearlyStatsRepo yearlyRepo;

    // Runs every night at 12:00 AM
    @Scheduled(cron = "0 0 0 * * *")
    public void aggregate() {

        LocalDate yesterday = LocalDate.now().minusDays(1);

        List<DailyStats> dailyList = dailyRepo.findAllByDate(yesterday);

        for (DailyStats d : dailyList) {
            Court court = d.getCourt();

            int year = yesterday.getYear();
            int month = yesterday.getMonthValue();
            int week = yesterday.get(WeekFields.ISO.weekOfWeekBasedYear());

            int bookings = d.getTotalBookings();
            double earnings = d.getMoneyEarned();

            updateWeekly(court, year, week, bookings, earnings);
            updateMonthly(court, year, month, bookings, earnings);
            updateYearly(court, year, bookings, earnings);
        }
    }

    private void updateWeekly(Court court, int year, int week, int b, double m) {
        WeeklyStats s = weeklyRepo.findByCourtAndYearAndWeek(court, year, week)
                .orElseGet(() -> {
                    WeeklyStats ws = new WeeklyStats();
                    ws.setCourt(court);
                    ws.setYear(year);
                    ws.setWeek(week);
                    return ws;
                });
        s.setTotalBookings(s.getTotalBookings() + b);
        s.setMoneyEarned(s.getMoneyEarned() + m);
        weeklyRepo.save(s);
    }

    private void updateMonthly(Court court, int year, int month, int b, double m) {
        MonthlyStats s = monthlyRepo.findByCourtAndYearAndMonth(court, year, month)
                .orElseGet(() -> {
                    MonthlyStats ms = new MonthlyStats();
                    ms.setCourt(court);
                    ms.setYear(year);
                    ms.setMonth(month);
                    return ms;
                });
        s.setTotalBookings(s.getTotalBookings() + b);
        s.setMoneyEarned(s.getMoneyEarned() + m);
        monthlyRepo.save(s);
    }

    private void updateYearly(Court court, int year, int b, double m) {
        YearlyStats s = yearlyRepo.findByCourtAndYear(court, year)
                .orElseGet(() -> {
                    YearlyStats ys = new YearlyStats();
                    ys.setCourt(court);
                    ys.setYear(year);
                    return ys;
                });
        s.setTotalBookings(s.getTotalBookings() + b);
        s.setMoneyEarned(s.getMoneyEarned() + m);
        yearlyRepo.save(s);
    }
}

