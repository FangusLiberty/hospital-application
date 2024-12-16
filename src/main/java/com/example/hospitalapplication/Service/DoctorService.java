package com.example.hospitalapplication.Service;

import com.example.hospitalapplication.Entity.Doctor;
import com.example.hospitalapplication.Entity.Specialty;
import com.example.hospitalapplication.Exception.DoctorNotFoundException;
import com.example.hospitalapplication.Exception.SpecialtyNotFoundException;
import com.example.hospitalapplication.Repository.DoctorRepository;
import com.example.hospitalapplication.Repository.SpecialtyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecialtyRepository specialtyRepository;


    public List<Doctor> getAllBySpecialtuId(Long id ) throws DoctorNotFoundException {
        Optional<List<Doctor>> opt_doctors = doctorRepository.findAllBySpecialtyId(id);
        if(opt_doctors.isPresent()){
            return opt_doctors.get();
        }
        else
            throw new DoctorNotFoundException("doctors of specialty with id '"+ id+"' not found");



    }


    public Doctor getDoctorById(Long doctorId) {
        Optional<Doctor> opt_doctor = doctorRepository.findById(doctorId);
        return opt_doctor.get();
    }

    public void updateDoctor(Long doctorId, Doctor updatedDoctor) throws DoctorNotFoundException {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);

        if (optionalDoctor.isPresent()) {

            optionalDoctor.get().setSalary(updatedDoctor.getSalary());
            optionalDoctor.get().setAreaNumber(updatedDoctor.getAreaNumber());
            optionalDoctor.get().setFirstName(updatedDoctor.getFirstName());
            optionalDoctor.get().setLastName(updatedDoctor.getLastName());
            optionalDoctor.get().setMiddleName(updatedDoctor.getMiddleName());
            optionalDoctor.get().setBaseSalary(updatedDoctor.getBaseSalary());
            optionalDoctor.get().setWorkAge(updatedDoctor.getWorkAge());
            optionalDoctor.get().setWorkAge(updatedDoctor.getWorkAge());


            doctorRepository.save(optionalDoctor.get());
        } else {
            // Handle the case where the doctor with the given ID is not found
            throw new DoctorNotFoundException("Doctor with ID " + doctorId + " not found");
        }
    }

    public void createDoctor(Doctor doctor, Long specId) throws SpecialtyNotFoundException {

        Optional<Specialty> optionalSpecialty = specialtyRepository.findById(specId);
        if (optionalSpecialty.isPresent()) {
            doctor.setSpecialty(optionalSpecialty.get());
            doctorRepository.save(doctor);
        }
        else
            throw new SpecialtyNotFoundException("specialty not found");

    }

    public void deleteDoctor(Long doctorId) {
        Optional<Doctor> opt_doctor = doctorRepository.findById(doctorId);
        doctorRepository.delete(opt_doctor.get());

    }
}
