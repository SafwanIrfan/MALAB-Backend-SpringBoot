package com.playwithease.PlayWithEase.controller;

import com.playwithease.PlayWithEase.model.PriceRequest;
import com.playwithease.PlayWithEase.model.PriceResponse;
import com.playwithease.PlayWithEase.service.OtherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/other")
public class OtherController {

    @Autowired
    private OtherService otherService;

    @PostMapping("/calculate-price")
    public PriceResponse calculatePrice(@RequestBody PriceRequest req) {
        return otherService.calculatePrice(req);
    }
}
