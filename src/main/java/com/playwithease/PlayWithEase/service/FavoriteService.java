package com.playwithease.PlayWithEase.service;

import com.playwithease.PlayWithEase.model.Court;
import com.playwithease.PlayWithEase.model.CourtFavoriteId;
import com.playwithease.PlayWithEase.model.CourtsFav;
import com.playwithease.PlayWithEase.model.Users;
import com.playwithease.PlayWithEase.repo.FavoriteRepo;
import com.playwithease.PlayWithEase.repo.UsersRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepo favRepo;

    @Autowired
    private UsersRepo usersRepo;

    @Autowired
    private CourtsService courtsService;

    public void addFavorite(Long courtId, Long usersId) {
        if (courtId != null && usersId != null) {
            CourtsFav favorite = new CourtsFav();
            CourtFavoriteId favoriteId = new CourtFavoriteId(usersId, courtId);
            favorite.setId(favoriteId);

            Users users = usersRepo.findById(usersId)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            Court court = courtsService.getCourtById(courtId);
            favorite.setUsers(users);
            favorite.setCourt(court);
            favRepo.save(favorite);

        }
    }

    @Transactional
    public void deleteFavorite(Long courtId, Long usersId) {
        System.out.println("CourtId : " + courtId);
        System.out.println("UsersId : " + usersId);
        favRepo.deleteByUsersIdAndCourtId(usersId, courtId);
    }

    public List<CourtsFav> getFavoriteByUsersId(Long usersId) {
        return favRepo.findByUsersId(usersId);
    }
}

