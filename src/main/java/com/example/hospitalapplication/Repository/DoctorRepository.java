package com.example.hospitalapplication.Repository;

import com.example.hospitalapplication.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.print.Doc;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    Optional<List<Doctor>> findAllBySpecialtyId(Long id);
}
