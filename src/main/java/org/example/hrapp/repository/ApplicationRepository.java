package org.example.hrapp.repository;

import org.example.hrapp.model.Application;
import org.example.hrapp.model.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByCandidateId(Long candidateId);
    List<Application> findByVacancyId(Long vacancyId);
    List<Application> findByStatus(ApplicationStatus status);

    @Query("SELECT a FROM Application a " +
            "LEFT JOIN FETCH a.candidate c " +
            "LEFT JOIN FETCH a.vacancy v " +
            "LEFT JOIN FETCH v.employer " +
            "WHERE a.id = :id")
    Application findByIdWithDetails(@Param("id") Long id);
}