package org.example.hrapp.service;

import org.example.hrapp.model.Vacancy;

import java.time.LocalDate;
import java.util.List;

public interface VacancyService {
    Vacancy createVacancy(Vacancy vacancy);
    Vacancy getVacancyById(Long id);
    List<Vacancy> getAllVacancies();
    Vacancy updateVacancy(Long id, Vacancy vacancy);
    void deleteVacancy(Long id);
    List<Vacancy> getVacanciesByEmployerId(Long employerId);

    List<Vacancy> filterVacancies(String status, LocalDate dateFrom, LocalDate dateTo);

}