package org.example.hrapp.repository;

import org.example.hrapp.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
    List<Vacancy> findByEmployerId(Long employerId);
    List<Vacancy> findAll();
    @Query("SELECT v FROM Vacancy v WHERE " +
            "(:isActive IS NULL OR v.active = :isActive) AND " +
            "(:dateFrom IS NULL OR v.createdAt >= :dateFrom) AND " +
            "(:dateTo IS NULL OR v.createdAt <= :dateTo)")
    List<Vacancy> findByFilters(
            @Param("isActive") Boolean isActive,
            @Param("dateFrom") LocalDate dateFrom,
            @Param("dateTo") LocalDate dateTo);

}
