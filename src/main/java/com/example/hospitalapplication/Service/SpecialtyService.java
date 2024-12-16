package com.example.hospitalapplication.Service;

import com.example.hospitalapplication.Entity.Specialty;
import com.example.hospitalapplication.Exception.SpecialtyNotFoundException;
import com.example.hospitalapplication.Repository.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpecialtyService {
    private final SpecialtyRepository specialtyRepository;

    public List<Specialty> findAll(){
        return specialtyRepository.findAll();
    }

    public void updateSpecialty(Specialty specialty, Long specialty1) throws SpecialtyNotFoundException {
        Optional<Specialty> opt_specialty = specialtyRepository.findById(specialty1);
        if(opt_specialty.isPresent()){
            opt_specialty.get().setSalaryRate(specialty.getSalaryRate());
            opt_specialty.get().setTotalWages(specialty.getTotalWages());
            opt_specialty.get().setDoctorCount(specialty.getDoctorCount());
            specialtyRepository.save(opt_specialty.get());
        }
        else
            throw new SpecialtyNotFoundException("specialty with id '"+ specialty1+"' not found");


    }

    public Specialty getSpecialtyById(Long specialtyId) {

        Optional<Specialty> opt_specialty = specialtyRepository.findById(specialtyId);
        return opt_specialty.get();
    }

    public void addSpecialty(Specialty specialty) {
        specialtyRepository.save(specialty);
    }

    public void deleteSpecialty(Long specId) {
        Optional<Specialty> opt_specialty = specialtyRepository.findById(specId);
        specialtyRepository.delete(opt_specialty.get());

    }
}
