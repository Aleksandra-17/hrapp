package org.example.hrapp.service.implementation;

import lombok.RequiredArgsConstructor;
import org.example.hrapp.model.Candidate;
import org.example.hrapp.repository.CandidateRepository;
import org.example.hrapp.service.CandidateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;

    @Override
    public Candidate createCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    @Override
    @Transactional(readOnly = true)
    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Candidate not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    @Override
    public Candidate updateCandidate(Long id, Candidate updatedCandidate) {
        Candidate existingCandidate = getCandidateById(id);

        existingCandidate.setFirstName(updatedCandidate.getFirstName());
        existingCandidate.setLastName(updatedCandidate.getLastName());
        existingCandidate.setEmail(updatedCandidate.getEmail());
        existingCandidate.setPhone(updatedCandidate.getPhone());
        existingCandidate.setResume(updatedCandidate.getResume());
        existingCandidate.setSkills(updatedCandidate.getSkills());

        return candidateRepository.save(existingCandidate);
    }

    @Override
    public void deleteCandidate(Long id) {
        candidateRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Candidate> searchCandidatesBySkills(String skills) {
        return candidateRepository.findAll().stream()
                .filter(candidate -> candidate.getSkills() != null &&
                        candidate.getSkills().toLowerCase().contains(skills.toLowerCase()))
                .toList();
    }
}