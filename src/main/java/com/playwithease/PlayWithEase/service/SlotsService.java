package com.playwithease.PlayWithEase.service;

import com.playwithease.PlayWithEase.model.BookedSlots;
import com.playwithease.PlayWithEase.model.Court;

import com.playwithease.PlayWithEase.model.Users;
import com.playwithease.PlayWithEase.repo.BookedSlotsRepo;
import com.playwithease.PlayWithEase.repo.CourtsRepo;

import com.playwithease.PlayWithEase.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SlotsService {

    @Autowired
    CourtsRepo courtsRepo;

    @Autowired
    CourtsService courtsService;

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    BookedSlotsRepo bookedSlotsRepo;

//    public List<BookedSlots> getSlots(Long courtId){
//        return bookedSlotsRepo.findByCourtIdOrdered(courtId);
//    }
//        Optional<Court> court = courtsRepo.findById(courtId);
//        if(court.isPresent()){
//            Court realCourt = court.get();
//            return realCourt.getSlots();
//        }
//        return null;
//    }


    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); // This gives the username from JWT (sub)
    }

   public BookedSlots addSlot(Long courtId,BookedSlots slot){
       Court court = courtsService.getCourtById(courtId);
       String username = getCurrentUsername();
       Users user = usersRepo.findByUsername(username);
       if(court != null && user != null){
           slot.setCourt(court);
           slot.setUsers(user);
           return bookedSlotsRepo.save(slot);
       }
       return new BookedSlots();
   }

   public List<BookedSlots> getAllBookedSlots(Long courtId){
       Court court = courtsService.getCourtById(courtId);
       if(court != null){
           return bookedSlotsRepo.findByCourtId(courtId);
       }
       return new ArrayList<>();

   }

    public List<BookedSlots> getAllBookedSlotsByDay(Long courtId, String day) {
        Court court = courtsService.getCourtById(courtId);
        if(court != null){
            return bookedSlotsRepo.findByCourtIdAndDay(courtId,day);
        }
        return new ArrayList<>();


    }

//    public BookedSlots getSlotsForDayAndDate(String day, Date date){
//        return bookedSlotsRepo.findByDayAndDate(day,date);
//    }
}
