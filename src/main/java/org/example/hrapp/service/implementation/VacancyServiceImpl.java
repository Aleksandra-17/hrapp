package org.example.hrapp.service.implementation;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.hrapp.model.Vacancy;
import org.example.hrapp.repository.VacancyRepository;
import org.example.hrapp.service.VacancyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;

    @Override
    public Vacancy createVacancy(Vacancy vacancy) {
        return vacancyRepository.save(vacancy);
    }

    @Override
    @Transactional(readOnly = true)
    public Vacancy getVacancyById(Long id) {
        return vacancyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vacancy not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vacancy> getAllVacancies() {
        return vacancyRepository.findAll();
    }

    public Vacancy updateVacancy(Long id, Vacancy updatedVacancy) {
        Vacancy existingVacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Vacancy not found"));

        existingVacancy.setPosition(updatedVacancy.getPosition());
        existingVacancy.setDescription(updatedVacancy.getDescription());
        existingVacancy.setSalary(updatedVacancy.getSalary());
        existingVacancy.setRequirements(updatedVacancy.getRequirements());
        existingVacancy.setActive(updatedVacancy.getActive());
        existingVacancy.setEmployer(updatedVacancy.getEmployer());

        return vacancyRepository.save(existingVacancy);
    }


    @Override
    public void deleteVacancy(Long id) {
        vacancyRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vacancy> getVacanciesByEmployerId(Long employerId) {
        return vacancyRepository.findByEmployerId(employerId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Vacancy> filterVacancies(String status, LocalDate dateFrom, LocalDate dateTo) {
        Boolean isActive = null;
        if ("active".equalsIgnoreCase(status)) {
            isActive = true;
        } else if ("inactive".equalsIgnoreCase(status)) {
            isActive = false;
        }
        return vacancyRepository.findByFilters(isActive, dateFrom, dateTo);
    }
}