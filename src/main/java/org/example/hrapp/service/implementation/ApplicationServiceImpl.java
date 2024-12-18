package org.example.hrapp.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.hrapp.model.Application;
import org.example.hrapp.model.ApplicationStatus;
import org.example.hrapp.repository.ApplicationRepository;
import org.example.hrapp.service.ApplicationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Override
    public Application createApplication(Application application) {
        if (application.getCandidate() == null || application.getVacancy() == null) {
            throw new IllegalArgumentException("Кандидат и вакансия должны быть указаны");
        }

        log.info("Создание новой заявки");
        application.setStatus(ApplicationStatus.NEW);
        application.setApplicationDate(LocalDateTime.now());

        Application saved = applicationRepository.save(application);
        log.info("Создана заявка: {}", saved);
        return saved;
    }

    @Override
    public Application getApplicationById(Long id) {
        log.info("Начинаем поиск заявки с ID: {}", id);

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заявка не найдена с ID: " + id));

        log.info("Данные заявки:");
        log.info("ID: {}", application.getId());
        log.info("Статус: {}", application.getStatus());
        log.info("Кандидат ID: {}", application.getCandidate() != null ? application.getCandidate().getId() : "null");
        log.info("Вакансия ID: {}", application.getVacancy() != null ? application.getVacancy().getId() : "null");
        log.info("Комментарии: {}", application.getComments());

        return application;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    @Override
    public Application updateApplication(Long id, Application updatedApplication) {
        log.info("Обновление заявки: {}", id);
        Application existingApplication = getApplicationById(id);

        log.info("Текущий статус: {}", existingApplication.getStatus());
        log.info("Новый статус: {}", updatedApplication.getStatus());

        existingApplication.setCandidate(updatedApplication.getCandidate());
        existingApplication.setVacancy(updatedApplication.getVacancy());
        existingApplication.setStatus(updatedApplication.getStatus());
        existingApplication.setApplicationDate(updatedApplication.getApplicationDate());
        existingApplication.setComments(updatedApplication.getComments());

        Application saved = applicationRepository.save(existingApplication);
        log.info("Заявка обновлена: {}", saved);
        return saved;
    }



    @Override
    public void deleteApplication(Long id) {
        applicationRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Application> getApplicationsByCandidateId(Long candidateId) {
        return applicationRepository.findByCandidateId(candidateId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Application> getApplicationsByVacancyId(Long vacancyId) {
        return applicationRepository.findByVacancyId(vacancyId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Application> getApplicationsByStatus(ApplicationStatus status) {
        return applicationRepository.findByStatus(status);
    }

    @Override
    public Application updateApplicationStatus(Long id, ApplicationStatus status) {
        log.info("Обновление статуса заявки {} на {}", id, status);
        Application application = getApplicationById(id);
        application.setStatus(status);
        Application saved = applicationRepository.save(application);
        log.info("Статус обновлен: {}", saved);
        return saved;
    }

    @Override
    public Map<String, Long> getApplicationStatistics() {
        List<Application> applications = applicationRepository.findAll();

        Map<String, Long> statistics = Arrays.stream(ApplicationStatus.values())
                .collect(Collectors.toMap(
                        ApplicationStatus::name,
                        status -> 0L
                ));

        Map<String, Long> actualStats = applications.stream()
                .collect(Collectors.groupingBy(
                        application -> application.getStatus().name(),
                        Collectors.counting()
                ));

        statistics.putAll(actualStats);
        return statistics;
    }
}