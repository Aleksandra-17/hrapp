package org.example.hrapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
public class Employer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String companyName;
    private String contactPerson;
    private String email;
    private String phone;
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL)
    private List<Vacancy> vacancies;

    @Override
    public String toString() {
        return companyName;
    }
}