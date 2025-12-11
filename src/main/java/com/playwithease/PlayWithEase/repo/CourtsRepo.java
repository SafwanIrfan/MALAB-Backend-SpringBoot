package com.playwithease.PlayWithEase.repo;

import com.playwithease.PlayWithEase.model.Court;
import com.playwithease.PlayWithEase.model.Enums.CourtStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourtsRepo extends JpaRepository<Court, Long> {

    @Query("SELECT c from Court c WHERE "+
           "LOWER(c.courtName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.city) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(c.area) LIKE LOWER(CONCAT('%', :keyword, '%'))"
            )
    List<Court> searchCourts(String keyword);

    @Query("SELECT DISTINCT CASE " +
            "WHEN LOWER(c.courtName) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN c.courtName " +
            "WHEN LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN c.description " +
            "WHEN LOWER(c.city) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN c.city " +
            "WHEN LOWER(c.area) LIKE LOWER(CONCAT('%', :keyword, '%')) THEN c.area " +
            "END " +
            "FROM Court c " +
            "WHERE LOWER(c.courtName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(c.city) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
            "OR LOWER(c.area) LIKE LOWER(CONCAT('%', :keyword, '%'))"
    )
    List<String> searchCourtsByWords(String keyword);
    Optional<List<Court>> findByOwnerId(Long ownerId) throws RuntimeException;
    Optional<List<Court>> findByCourtStatus(CourtStatus status) throws RuntimeException;

    boolean existsByCourtName(String courtName);
}
