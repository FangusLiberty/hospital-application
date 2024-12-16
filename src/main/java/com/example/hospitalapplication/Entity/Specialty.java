package com.example.hospitalapplication.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "specialty")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "specialty")
    private List<Doctor> doctorList;

    @Column(name = "name")
    private String name;

    @Column(name = "is_specialist")
    private boolean isSpecialist;

    @Column(name = "doctor_count")
    private int doctorCount;

    @Column(name = "salary_rate")
    private double salaryRate;

    @Column(name = "total_wages")
    private double totalWages;
}