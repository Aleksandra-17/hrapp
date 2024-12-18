package org.example.hrapp.service.implementation;

import lombok.RequiredArgsConstructor;
import org.example.hrapp.model.Employer;
import org.example.hrapp.repository.EmployerRepository;
import org.example.hrapp.service.EmployerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployerServiceImpl implements EmployerService {

    private final EmployerRepository employerRepository;

    @Override
    public Employer createEmployer(Employer employer) {
        return employerRepository.save(employer);
    }

    @Override
    @Transactional(readOnly = true)
    public Employer getEmployerById(Long id) {
        return employerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employer not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employer> getAllEmployers() {
        return employerRepository.findAll();
    }

    @Override
    public Employer updateEmployer(Long id, Employer updatedEmployer) {
        Employer existingEmployer = getEmployerById(id);

        existingEmployer.setCompanyName(updatedEmployer.getCompanyName());
        existingEmployer.setContactPerson(updatedEmployer.getContactPerson());
        existingEmployer.setEmail(updatedEmployer.getEmail());
        existingEmployer.setPhone(updatedEmployer.getPhone());
        existingEmployer.setAddress(updatedEmployer.getAddress());

        return employerRepository.save(existingEmployer);
    }

    @Override
    public void deleteEmployer(Long id) {
        employerRepository.deleteById(id);
    }
}