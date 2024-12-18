package org.example.hrapp.service;

import org.example.hrapp.model.Candidate;
import java.util.List;

public interface CandidateService {
    Candidate createCandidate(Candidate candidate);
    Candidate getCandidateById(Long id);
    List<Candidate> getAllCandidates();
    Candidate updateCandidate(Long id, Candidate candidate);
    void deleteCandidate(Long id);
    List<Candidate> searchCandidatesBySkills(String skills);
}
