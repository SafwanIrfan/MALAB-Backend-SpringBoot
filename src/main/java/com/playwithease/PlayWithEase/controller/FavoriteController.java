package com.playwithease.PlayWithEase.controller;

import com.playwithease.PlayWithEase.model.CourtsFav;
import com.playwithease.PlayWithEase.repo.FavoriteRepo;
import com.playwithease.PlayWithEase.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class FavoriteController {

    @Autowired
    private FavoriteService favService;

    @Autowired
    private FavoriteRepo favRepo;



    @PostMapping("court/{courtId}/user/{usersId}/fav")
    public void addFavorite(@PathVariable Long courtId, @PathVariable Long usersId) {
        if (favRepo.existsByUsersIdAndCourtId(usersId, courtId)) {
            favService.deleteFavorite(courtId, usersId);
            System.out.println("DELETED SUCCESSFULLY");
        }
        else {
            favService.addFavorite(courtId, usersId);
        }
    }

    @GetMapping("user/{usersId}/getfav")
    public ResponseEntity<List<CourtsFav>> getFavoriteByUsersId(@PathVariable Long usersId){
        return new ResponseEntity<>(favService.getFavoriteByUsersId(usersId),HttpStatus.OK);
    }


}
