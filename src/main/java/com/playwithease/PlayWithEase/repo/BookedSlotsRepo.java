package com.playwithease.PlayWithEase.repo;

import com.playwithease.PlayWithEase.model.BookedSlots;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BookedSlotsRepo extends JpaRepository<BookedSlots, Long> {
    List<BookedSlots> findByCourtId(Long courtId);
    List<BookedSlots> findByUsersId(Long usersId);
    List<BookedSlots> findByCourtIdAndDay(Long courtId,String day);
    List<BookedSlots> findByCourtName(String courtName);
}
