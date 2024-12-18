package org.example.hrapp.controller.api;

import lombok.RequiredArgsConstructor;
import org.example.hrapp.model.Vacancy;
import org.example.hrapp.service.VacancyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vacancies")
@RequiredArgsConstructor
public class VacancyController {

    private final VacancyService vacancyService;

    @PostMapping
    public ResponseEntity<Vacancy> createVacancy(@RequestBody Vacancy vacancy) {
        return ResponseEntity.ok(vacancyService.createVacancy(vacancy));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vacancy> getVacancy(@PathVariable Long id) {
        return ResponseEntity.ok(vacancyService.getVacancyById(id));
    }

    @GetMapping
    public ResponseEntity<List<Vacancy>> getAllVacancies() {
        return ResponseEntity.ok(vacancyService.getAllVacancies());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Vacancy> updateVacancy(
            @PathVariable Long id,
            @RequestBody Vacancy vacancy) {
        return ResponseEntity.ok(vacancyService.updateVacancy(id, vacancy));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable Long id) {
        vacancyService.deleteVacancy(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/employer/{employerId}")
    public ResponseEntity<List<Vacancy>> getVacanciesByEmployer(@PathVariable Long employerId) {
        return ResponseEntity.ok(vacancyService.getVacanciesByEmployerId(employerId));
    }
}