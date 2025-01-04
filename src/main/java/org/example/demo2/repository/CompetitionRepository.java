package org.example.demo2.repository;

import org.example.demo2.model.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    @Modifying
    @Query("UPDATE Competition c SET c.totalKg = :totalKg WHERE c.id = :id")
    void updateTotalKg(@Param("id") Long id, @Param("totalKg") double totalKg);
    List<Competition> findAllByOrderByDateAsc();



}
