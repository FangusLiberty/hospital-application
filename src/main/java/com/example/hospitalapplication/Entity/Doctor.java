package com.example.hospitalapplication.Entity;

import javax.persistence.*;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.Period;

@Entity
@ToString
@Table(name = "doctor")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "specialty_id")
    private Specialty specialty;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_date")
    private LocalDate birthDate;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "employment_date")
    private LocalDate employmentDate;

    @Column(name = "work_age")
    private int workAge;

    @Column(name = "area_number")
    private Integer areaNumber;

    @Column(name = "base_salary")
    private double baseSalary;

    @Column(name = "experience")
    private int experience;

    @Column(name = "salary")
    private double salary;

    @Column(name = "pension")
    private double pension;

    @PrePersist
    public void calculateSalaryAndPension() {
        validateEmploymentDate();
        validateAreaNumber();

        double baseSalary = getSpecialty().getSalaryRate();
        int experience = getExperience();
        double bonus = calculateExperienceBonus(experience);
        setSalary(baseSalary + bonus);

        int age = calculateAge();
        double pension = calculatePension(age);
        setPension(pension);
    }
    private void validateEmploymentDate() {
        LocalDate currentDate = LocalDate.now();
        int ageAtEmployment = Period.between(getBirthDate(), getEmploymentDate()).getYears();
        if (ageAtEmployment < 20) {
            throw new IllegalArgumentException("Возраст врача при приеме на работу не может быть меньше 20 лет");
        }
    }

    private void validateAreaNumber() {
        if (!getSpecialty().isSpecialist() && getAreaNumber() == null) {
            throw new IllegalArgumentException("У врача, не являющегося узким специалистом, должен быть указан номер участка");
        }
    }

    private double calculateExperienceBonus(int experience) {
        if (experience >= 5 && experience < 10) {
            return 0.05 * getSpecialty().getSalaryRate();
        } else if (experience >= 10 && experience < 20) {
            return 0.1 * getSpecialty().getSalaryRate();
        } else if (experience >= 20 && experience < 35) {
            return 0.15 * getSpecialty().getSalaryRate();
        } else if (experience >= 35) {
            return 0.2 * getSpecialty().getSalaryRate();
        } else {
            return 0.0;
        }
    }

    private int calculateAge() {
        LocalDate currentDate = LocalDate.now();
        System.out.println(currentDate);
        return currentDate.getYear() - getBirthDate().getYear();
    }

    private double calculatePension(int age) {
        int retirementAge = (getSpecialty().isSpecialist()) ? 55 : 60;
        System.out.println(retirementAge);
        System.out.println(age);
        if (true) {
            System.out.println(getSpecialty().getSalaryRate());
            return 0.5 * getSpecialty().getSalaryRate();
        } else {
            return 0.0;
        }
    }

}
