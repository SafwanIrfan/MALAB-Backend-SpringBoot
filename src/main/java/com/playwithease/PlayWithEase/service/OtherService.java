package com.playwithease.PlayWithEase.service;

import com.playwithease.PlayWithEase.model.Court;
import com.playwithease.PlayWithEase.model.PriceRequest;
import com.playwithease.PlayWithEase.model.PriceResponse;
import com.playwithease.PlayWithEase.repo.CourtsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;

@Service
public class OtherService {

    @Autowired
    private CourtsRepo courtsRepo;

    public PriceResponse calculatePrice(PriceRequest req) {

        Court court = courtsRepo.findById(req.getCourtId())
                .orElseThrow(() -> new RuntimeException("Court not found"));

        LocalTime start = LocalTime.parse(req.getStartTime());
        LocalTime end = LocalTime.parse(req.getEndTime());

        long minutes = Duration.between(start, end).toMinutes();

        if (minutes <= 0) {
            throw new IllegalArgumentException("Invalid time range");
        }

        double pricePerHour = court.getPricePerHour();
        double totalPrice = (minutes / 60.0) * pricePerHour;

        return new PriceResponse(totalPrice, minutes, pricePerHour);
    }

}
