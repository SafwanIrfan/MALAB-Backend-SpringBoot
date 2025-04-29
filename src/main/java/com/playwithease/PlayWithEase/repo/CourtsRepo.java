package com.playwithease.PlayWithEase.repo;

import com.playwithease.PlayWithEase.model.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourtsRepo extends JpaRepository<Court, Long> {

    @Query("SELECT c from Court c WHERE "+
           "LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.location) LIKE LOWER(CONCAT('%', :keyword, '%'))"
            )
    List<Court> searchCourts(String keyword);

    @Query("SELECT DISTINCT CASE " +
            "WHEN LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN c.name " +
            "WHEN LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN c.description " +
            "WHEN LOWER(c.location) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN c.location " +
            "END " +
            "FROM Court c " +
            "WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(c.location) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<String> searchCourtsByWords(String keyword);
    void deleteCourtImageById(Long Id);
}
