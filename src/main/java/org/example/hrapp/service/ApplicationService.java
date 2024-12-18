package org.example.hrapp.service;

import org.example.hrapp.model.Application;
import org.example.hrapp.model.ApplicationStatus;
import java.util.List;
import java.util.Map;

public interface ApplicationService {
    Application createApplication(Application application);
    Application getApplicationById(Long id);
    List<Application> getAllApplications();
    Application updateApplication(Long id, Application application);
    void deleteApplication(Long id);
    List<Application> getApplicationsByCandidateId(Long candidateId);
    List<Application> getApplicationsByVacancyId(Long vacancyId);
    List<Application> getApplicationsByStatus(ApplicationStatus status);
    Application updateApplicationStatus(Long id, ApplicationStatus status);

    Map<String, Long> getApplicationStatistics();

}