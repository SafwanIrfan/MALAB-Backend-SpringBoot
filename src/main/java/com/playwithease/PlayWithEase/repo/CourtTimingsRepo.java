package com.playwithease.PlayWithEase.repo;

import com.playwithease.PlayWithEase.model.Timings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourtTimingsRepo extends JpaRepository<Timings,Long> {
    List<Timings> findByCourtId(Long courtId);
    Timings findByCourtIdAndDay(Long courtId,String day);

}
