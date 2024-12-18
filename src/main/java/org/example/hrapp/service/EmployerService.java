package org.example.hrapp.service;

import org.example.hrapp.model.Employer;
import java.util.List;

public interface EmployerService {
    Employer createEmployer(Employer employer);
    Employer getEmployerById(Long id);
    List<Employer> getAllEmployers();
    Employer updateEmployer(Long id, Employer employer);
    void deleteEmployer(Long id);
}